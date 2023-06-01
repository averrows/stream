package com.streambackend;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class StreamService extends AbstractVerticle {
    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.get("/").handler(routingContext -> {
            routingContext.response().end("Server Running!");
        });
        router.get("/test-param/:name").handler(routingContext -> {
            String name = routingContext.request().getParam("name");
            routingContext.response().end("Hello " + name + "!");
        });
        vertx.createHttpServer()
                .requestHandler(req -> req.response().end("hello"))
                .listen(8080);
    }
}
