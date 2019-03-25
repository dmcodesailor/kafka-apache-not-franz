/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rvnug.exoplanet;

import com.hazelcast.config.Config;
import com.rvnug.exoplanet.exo.*;
import com.rvnug.exoplanet.util.Logger;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import java.util.ArrayList;

/**
 *
 * @author brianlanham
 */
public class BootstrapVerticle extends AbstractVerticle {

    private final static Logger logger = Logger.logger();

    private String moduleName = "exoplanet-service";
    private io.vertx.core.Vertx vertx = io.vertx.core.Vertx.vertx();

    ArrayList<Verticle> getChildStandardVerticles(Router router) {
        logger.trace("BootstrapVerticle::getChildStandardVerticles");
        ArrayList<Verticle> result = new ArrayList<Verticle>();

        String baseEvtBusChannel = this.config().getString("EVENTBUS_BASE_CHANNEL");

        result.add(new ExoplanetDataApi(baseEvtBusChannel, router, this.config().getString("REST_API_BASE_URL"), this.config().getInteger("REST_API_PORT")));
        return result;
    }

    ArrayList<Verticle> getChildWorkerVerticles() {
        logger.trace("BootstrapVerticle::getChildWorkerVerticles");
        ArrayList<Verticle> result = new ArrayList<Verticle>();

        String baseEvtBusChannel = this.config().getString("EVENTBUS_BASE_CHANNEL");

        // Exoplanet Data subsystem
        result.add(new ExoplanetReaderWorkerVerticle(baseEvtBusChannel));

        return result;
    }

    public static void main(String[] args) {
        logger.trace("BootsrapVerticle::main");
        String verticleName = BootstrapVerticle.class.getClass().getCanonicalName();
        BootstrapVerticle verticleInstance = new BootstrapVerticle();
        verticleInstance.deployModuleVerticle(verticleName, verticleInstance, getDeploymentOptions());
    }

    private static DeploymentOptions getDeploymentOptions() {
        JsonObject vertxConfig = new io.vertx.core.json.JsonObject();
        vertxConfig.put("REST_API_PORT", 8080);
        vertxConfig.put("REST_API_BASE_URL", "/exoplanet-service/api/");
        vertxConfig.put("EVENTBUS_BASE_CHANNEL", "/exoplanet-service/");
        vertxConfig.put("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.Log4j2LogDelegateFactory");
        DeploymentOptions deployOptions = new io.vertx.core.DeploymentOptions();
        deployOptions.setConfig(vertxConfig);
        return deployOptions;
    }

    BootstrapVerticle(){
        super();
        logger.trace("BootstrapVerticle::ctor");
    }

    @Override
    public void start() {
        logger.trace("BootsrapVerticle::start");
        try {
            super.start();
//            this.deployChildVerticles(this.getDeploymentOptions());
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void stop() {
        logger.trace("BootsrapVerticle::stop");
        try {
            super.stop();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    protected void deployModuleVerticle (String moduleName, BootstrapVerticle verticleInstance, DeploymentOptions options) {
        logger.trace(String.format("BootstrapVerticle::deployModuleVerticle(%s, %s, %s)", moduleName, verticleInstance.getClass().getName(), options.toJson()));
        vertx.deployVerticle (verticleInstance, options,
                (AsyncResult<String> deployResult) -> {
                    if (deployResult.succeeded ()) {
                        logger.info(moduleName + " module verticle deployment successful. Deployment id is: " + deployResult.result ());
                        verticleInstance.deployChildVerticles(this.getDeploymentOptions());
                    } else {
                        deployResult.cause ().printStackTrace ();
                    }
                }
        );
    }

    protected void deployChildVerticles(DeploymentOptions options) {
        logger.trace("BootstrapVerticle::deployChildVerticles");
//        DeploymentOptions options = new DeploymentOptions ();
//        JsonObject config = this.config ();
//        options.setConfig (config);

        Config hazelcastConfig = new Config();
        ClusterManager myClusterManager = new HazelcastClusterManager (hazelcastConfig);

        Vertx.clusteredVertx (new VertxOptions ().setClusterManager(myClusterManager),result->{
            if (result.succeeded ())
            {
                HttpServer httpServer = vertx.createHttpServer ();
                int port = config ().getInteger ("REST_API_PORT");

                //deploy worker verticles first
                ArrayList<Verticle> verticlesToDeploy = getChildWorkerVerticles ();
                for (Verticle child : verticlesToDeploy){
//                    DeploymentOptions childOptions = new DeploymentOptions ();
                    options.setWorker (true);
                    String name = child.getClass ().getName ();
                    logger.trace("BootstrapVerticle::deployChildVerticles::" + name + "::" + options.getConfig().getString("REST_API_BASE_URL"));
                    this.vertx.deployVerticle (child, options, (deployResult) -> {
                                if (deployResult.succeeded ()) {
                                    logger.info (moduleName+" module worker verticle deployment successful(" + name +
                                            "). Deployment id is: " + deployResult.result ());
                                }
                                else {
                                    //noinspection ThrowableResultOfMethodCallIgnored
                                    deployResult.cause ().printStackTrace ();
                                }
                            }
                    );
                }

                final Router router = this.getRouter();
                //now deploy standard verticles
                verticlesToDeploy = getChildStandardVerticles (router);

                for (Verticle child : verticlesToDeploy){
                    String name = child.getClass ().getName ();
                    this.vertx.deployVerticle (child, (deployResult) -> {
                                if (deployResult.succeeded ()) {
                                    logger.info (moduleName+" module standard verticle ("+name+")deployment successful. Deployment id is: " + deployResult.result ());
                                }
                                else {
                                    //noinspection ThrowableResultOfMethodCallIgnored
                                    deployResult.cause ().printStackTrace ();
                                }
                            }
                    );
                }

                httpServer.requestHandler (router::accept).listen (port, handler -> {
                    if (handler.succeeded()) {
                        logger.info ("Starting HTTP server succeeded....");
                    }

                    if (handler.failed ()) {
                        logger.info (handler.cause ().getMessage());
                        try {
                            this.stop ();
                        }
                        catch (Exception e) {
                            e.printStackTrace ();
                        }
                    }
                });
            }
            else
            {
                logger.fatal ("Could not deploy "+ moduleName+" module verticle, reason:");
                logger.fatal (result.cause ().getMessage ());
            }
        });
    }
    private CorsHandler getCorsHandler() {
        CorsHandler corsHandler = CorsHandler.create ("*")
                .allowedMethod (HttpMethod.GET)
                .allowedMethod (HttpMethod.POST)
                .allowedMethod (HttpMethod.OPTIONS)
                .allowedHeader("content-type");
        return corsHandler;
    }

    private Router getRouter() {
        Router router = Router.router(vertx);
        router.route().handler(getCorsHandler());
        router.route().handler(BodyHandler.create());
        return router;
    }


}
