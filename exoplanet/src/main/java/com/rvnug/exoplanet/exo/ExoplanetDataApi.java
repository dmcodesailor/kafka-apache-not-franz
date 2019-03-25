package com.rvnug.exoplanet.exo;

import com.rvnug.exoplanet.ApiVerticleBase;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.AsyncResult;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;

import java.util.ArrayList;

public class ExoplanetDataApi extends ApiVerticleBase {
    public ExoplanetDataApi(String eventBusChannelBase, Router parentRouter, String baseApiUrl, int apiPort) {
        super(eventBusChannelBase, parentRouter, baseApiUrl, apiPort);
        logger.trace("ExoplanetDataApi::ctor");
        this.appStackRoute = "exoplanet-data";
    }

    @Override
    public void start() {
        try {
            super.start();
            logger.trace("ExoplanetDataApi::start");
            this.registerGet(this.routeApi(""), this::getExoplanetList);
            this.registerGet(this.routeApi(":id"), this::getExoplanet);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    private void getExoplanetList(RoutingContext context) {
        logger.trace("ExoplanetDataApi::getExoplanetList");
        String channelName = this.routeEventBusChannel(this.LIST);
        this.vertx.eventBus().send(channelName, null, (AsyncResult<Message<String>> result) -> {
            if (result.succeeded()) {
//                ArrayList<ExoplanetData> responseMessage = result.result().body();
//                context.response().setStatusCode(200).end(Json.encode(responseMessage));
                context.response().setStatusCode(200).end(result.result().body());
            } else {
                context.response().setStatusCode(500).end(result.cause().getMessage());
            }
        });
    }

    private void getExoplanet(RoutingContext context) {
        String idString = context.request().getParam("id");
        logger.trace(String.format("ExoplanetDataApi::getExoplanet::%s", idString));
        try {
//            String idString = context.request().getParam("id");
            this.vertx.eventBus().send(this.routeEventBusChannel(this.RETRIEVE), idString, (AsyncResult<Message<ExoplanetData>> result) -> {
                if (result.succeeded()) {
                    ExoplanetData responseMessage = result.result().body();
                    context.response().setStatusCode(200).end(Json.encode(responseMessage));
                } else {
                    context.response().setStatusCode(500).end(result.cause().getMessage());
                }
            });
        } catch (Exception ex) {
            context.response().setStatusCode(500).end(ex.getMessage());
        }
    }

    @Override
    public void stop() {
        try {
            super.stop();
        } catch (Exception ex) {

        }
    }

}
