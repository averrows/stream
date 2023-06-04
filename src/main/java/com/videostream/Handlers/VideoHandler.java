package com.videostream.Handlers;

import com.videostream.DatabaseClient.IDatabaseClient;
import com.videostream.DatabaseClient.MongoDBClient;
import com.videostream.Models.Video;
import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.streams.Pump;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.nio.file.Path;
import java.nio.file.Paths;

public class VideoHandler implements IHandler {
    public static void handle(RoutingContext ctx, Vertx vertx) {
        String id = ctx.request().getParam("id");
        // get the video metadata
        Video video = getVideo(id);
        if (video == null) {
            JsonObject responseJson = new JsonObject();
            responseJson.put("id", id);
            responseJson.put("error", "Video not found");

            ctx.response().end(responseJson.encodePrettily());
            return;
        }

        Path videoPath = Paths.get("src/main/resources/" + video.path);
        System.out.println(videoPath.toAbsolutePath());
        long videoSize = videoPath.toFile().length();

        vertx.fileSystem().open(video.path, new OpenOptions(), asyncResult -> {
            if (asyncResult.succeeded()) {
                pumpVideo(ctx, video, videoSize, asyncResult);
            } else {
                ctx.response().setStatusCode(500).end();
            }
        });
    }

    private static void pumpVideo(RoutingContext ctx, Video video, long videoSize, AsyncResult<AsyncFile> asyncResult) {
        AsyncFile videoFile = asyncResult.result();

        ctx.response().putHeader("Content-Type", "video/mp4");
        ctx.response().putHeader("Content-Length", String.valueOf(videoSize));
        Pump pump = Pump.pump(videoFile, ctx.response());
        pump.start();

        videoFile.endHandler(event -> {
            videoFile.close();
            ctx.response().end();
        });

        ctx.response().exceptionHandler(event -> {
            videoFile.close();
            ctx.response().close();
        });
    }

    private static Video getVideo(String id) {
        IDatabaseClient databaseClient = MongoDBClient.ofCollection("video_metadata");
        System.out.println(id);
        Video video = databaseClient.getEntityById(id, Video.class);
        System.out.println(video);
        return video;
    }

}
