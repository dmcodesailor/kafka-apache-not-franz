package com.rvnug.exoplanet.exo;

import com.rvnug.exoplanet.DbVerticleBase;
import com.rvnug.exoplanet.VerticleBase;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.SQLConnection;

import java.util.ArrayList;
import java.util.Arrays;

public class ExoplanetReaderWorkerVerticle extends DbVerticleBase {

    public ExoplanetReaderWorkerVerticle(String eventBusChannelBase) {
        super(eventBusChannelBase);
        this.appStackRoute = "exoplanet-data";
        logger.trace("ExoplanetReaderWorkerVerticle::ctor");
    }

    @Override
    public void start() {
        try {
            super.start();
            logger.trace("ExoplanetReaderWorkerVerticle::start");
            this.vertx.eventBus().consumer(this.routeEventBusChannel(VerticleBase.LIST), this::retrieveExoplanetDataList);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
    }

    public void retrieveExoplanetDataList(Message<String> message) {
        String logMessageBase = String.format("%s::%s::", this.getClass().getSimpleName(), "retrieveExoplanetDataList");
        logger.trace(logMessageBase + message.body());
        try {
            this.sqlClient.getConnection(res -> {
                if (res.succeeded()) {
                    SQLConnection connection = res.result();
//                    String query = String.format("SELECT * FROM %s", "exoplanet_eu_catalog");
                    String query = String.format("SELECT to_json(array_agg(%s)) FROM %s", "exoplanet_eu_catalog", "exoplanet_eu_catalog");
                    connection.query(query, (queryResult -> {
                        if (queryResult.succeeded()) {
                            logger.trace("retrieveExoplanetDataList::query succeeded");
                            JsonArray resultRecord = queryResult.result().getResults().get(0);
                            ArrayList<ExoplanetData> resultEntity = new ArrayList<>();
//                            message.reply(Json.encode(resultRecord.getString(0)));
                            message.reply(resultRecord.getString(0));
                        } else {
                            logger.trace(String.format("retrieveExoplanetDataList:: query FAILED::%s", queryResult.cause().getMessage()));
                            message.fail(500, queryResult.cause().getMessage());
                        }
                    }));
                } else {
                    logger.trace(String.format("retrieveExoplanetDataList::connection FAILED::%s", res.cause().getMessage()));
                    message.fail(500, res.cause().getMessage());
                }
            });
        } catch (Exception ex) {
            logger.trace(String.format("retrieveExoplanetDataList::%s", ex.getMessage()));
            message.fail(500, ex.getMessage());
        }
    }

    @Override
    public void stop() {
        try {
            super.stop();
            logger.trace("ExoplanetReaderWorkerVerticle::stop");
            this.sqlClient.close((result) -> {
                if(result.failed()) {
                    logger.error(result.cause().getMessage());
                }
            });
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

}
