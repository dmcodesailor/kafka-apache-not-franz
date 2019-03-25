package com.rvnug.exoplanet;

import com.rvnug.exoplanet.util.DbHelper;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.asyncsql.PostgreSQLClient;

public class DbVerticleBase<T extends EntityBase> extends VerticleBase {

    protected AsyncSQLClient sqlClient = null;
    // TODO - set the username/password for your instance
    protected String username = "";
    protected String password = "";
    protected DbHelper<T> dbHelper = new DbHelper<>(logger);

    public DbVerticleBase(String eventBusChannelBase) {
        super(eventBusChannelBase);
    }

    @Override
    public void start() {
        try {
            super.start();
            logger.trace("DbVerticleBase::start");
            this.useSharedPool();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void stop() {
        try {
            super.stop();
            logger.trace("DbVerticleBase::stop");
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

    private void useSharedPool() {
        logger.trace("DbVerticleBase::useSharedPool");
        JsonObject postgreSqlClientConfig = new JsonObject();
        postgreSqlClientConfig.put("host", "localhost");
        postgreSqlClientConfig.put("port", 5432);
        postgreSqlClientConfig.put("username", this.username);
        postgreSqlClientConfig.put("password", this.password);
        postgreSqlClientConfig.put("database", "exoplanets");
        logger.debug("DbVerticleBase::useSharedPool::creating shared connection pool...");
        try {
            this.sqlClient = PostgreSQLClient.createShared(this.vertx, postgreSqlClientConfig);
            logger.info("DbVerticleBase::useSharedPool::connected");
        } catch (Exception ex) {
            logger.error("DbVerticleBase::useSharedPool::" + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
