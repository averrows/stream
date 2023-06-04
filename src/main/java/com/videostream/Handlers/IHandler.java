package com.videostream.Handlers;

import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

public interface IHandler {
    static void handle(RoutingContext ctx, Vertx vertx) {};
}
