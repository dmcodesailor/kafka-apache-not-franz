package com.rvnug.exoplanet;

import com.rvnug.exoplanet.util.Logger;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;

public abstract class VerticleBase extends AbstractVerticle {
    protected final static Logger logger = Logger.logger();

    //    protected final static String DATA = "db";
    protected final static String CREATE = "mk";
    protected final static String RETRIEVE = "rd";
    protected final static String UPDATE = "vi";
    protected final static String DELETE = "rm";
    protected final static String LIST = "ls";
    protected final static String SEARCH = "qy";
    protected final static String MATCH = "rx";

    protected String appStackRoute = "";
    protected String baseEventBusChannel;

    protected VerticleBase(String eventBusBaseChannel) {
        super();
        this.baseEventBusChannel = eventBusBaseChannel;
    }

    @Override
    public void start() {
        try {
            super.start();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    protected String routeEventBusChannel(String endpointName) {
        String result = this.ensureTrailingSlash(this.baseEventBusChannel)
                + this.ensureTrailingSlash(this.appStackRoute)
                + endpointName;
        logger.trace("VerticleBase::routeEventBusChannel::" + result);
        return result;
    }

    protected String ensureTrailingSlash (String input) {
        if (input != null && !input.trim().endsWith("/")) {
            input = input.trim() + "/";
        }
        return input;
    }

    public void messageHandler(Message message) {
        String target = message.body().toString().trim();
        message.reply("");
    }

    @Override
    public void stop() {
        try {
            super.stop();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

}
