package com.videostream.Models;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class Video {
    @BsonProperty
    public String title;
    @BsonProperty
    public String bytes;
    @BsonProperty
    public String durationSeconds;
    @BsonProperty
    public String path;
}
