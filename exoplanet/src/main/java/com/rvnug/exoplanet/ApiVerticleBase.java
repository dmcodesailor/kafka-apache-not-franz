package com.rvnug.exoplanet;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public abstract class ApiVerticleBase extends VerticleBase {
    protected Router router;
    private String baseApiUrl;
    private int apiPort;

    protected ApiVerticleBase(String eventBusChannelBase, Router parentRouter, String baseApiUrl, int apiPort) {
        super(eventBusChannelBase);
        this.router = parentRouter;
        this.baseApiUrl = baseApiUrl;
        this.apiPort = apiPort;
    }

    @Override
    public void start() {
        try {
            super.start();

            // Invoke an HTTP server to handle requests.
            HttpServer httpServer = vertx.createHttpServer();
            httpServer.requestHandler(router::accept).listen(this.apiPort);
            logger.trace("REST Server Started at " +this.apiPort);

        } catch (Exception ex) {
            logger.error(ex.getMessage() + " while starting RestApiVerticle");
        }
    }

    protected String routeApi(String endpointName) {
        logger.trace("ApiVerticleBase::routeApi::" + endpointName);
        return this.ensureTrailingSlash(this.baseApiUrl)
                + this.ensureTrailingSlash(this.appStackRoute)
                + endpointName;
    }

    protected void registerGet(String route, Handler<RoutingContext> handler) {
        logger.trace(String.format("ApiVerticleBase::registerGet::%s", route));
        this.router.get(route).handler(handler);
    }

    protected void registerPost(String route, Handler<RoutingContext> handler) {
        logger.trace(String.format("ApiVerticleBase::registerPost::%s", route));
        this.router.post(route).handler(handler);
    }

    protected void registerPut(String route, Handler<RoutingContext> handler) {
        logger.trace(String.format("ApiVerticleBase::registerPut::%s", route));
        this.router.put(route).handler(handler);
    }

    protected void registerDelete(String route, Handler<RoutingContext> handler) {
        logger.trace(String.format("ApiVerticleBase::registerDelete::%s", route));
        this.router.delete(route).handler(handler);
    }

    protected String exoplanetServiceExceptionToLightweightJson(String exceptionString) {
        logger.trace("ApiVerticleBase::exoplanetServiceExceptionToLightweightJson::" + exceptionString);
        JsonObject exceptionJson = new JsonObject(exceptionString);
        JsonObject result = new JsonObject();
        result.put("number", exceptionJson.getInteger("number"));
        result.put("message", exceptionJson.getString("message"));
        return result.toString();
    }

    @Override
    public void stop() {
        try {
            super.stop();
        } catch (Exception ex) {
            logger.error(ex.getMessage() + " while stopping RestApiVerticle");
        }
    }
}
