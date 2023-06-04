package com.videostream;

import com.videostream.DatabaseClient.IDatabaseClient;
import com.videostream.DatabaseClient.MongoDBClient;
import com.videostream.Handlers.VideoHandler;
import com.videostream.Models.Video;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.streams.Pump;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StreamService extends AbstractVerticle {
    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.get("/video/:id").handler(ctx -> VideoHandler.handle(ctx, vertx));
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

}
