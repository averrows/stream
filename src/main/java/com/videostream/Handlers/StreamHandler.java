package com.videostream.Handlers;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class StreamHandler implements IHandler {
    public static void handle(RoutingContext ctx, Vertx vertx) {
        HttpServerRequest request = ctx.request();
        HttpServerResponse response = ctx.response();

        response.putHeader("Content-Type", "video/mp4");
        response.setChunked(true);

        request.handler(chunk -> {
            //TODO: save to the local file
        });
        request.endHandler(event -> {
            response.end();
        });
    }
}
