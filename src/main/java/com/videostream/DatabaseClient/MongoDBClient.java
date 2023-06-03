package com.videostream.DatabaseClient;


import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.internal.MongoClientImpl;
import com.videostream.Models.Video;
import org.bson.BsonDocument;
import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.Arrays;

public class MongoDBClient implements IDatabaseClient {
    private String collection;
    private MongoDBClient(String collection) {
        this.collection = collection;
    }

    public static MongoDBClient ofCollection(String collection) {
        return new MongoDBClient(collection);
    }
    @Override
    public <T> T getEntityById(String id, Class<T> resultClass) {
// Establish a connection to the MongoDB server
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress("127.0.0.1", 27017))))
                .codecRegistry(pojoCodecRegistry)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("videostream");
        Document document = new Document();
        document.put("_id", new ObjectId(id));
        return database.getCollection(collection).find(document, resultClass).first();
    }

    @Override
    public void saveEntity(Object entity) {

    }

    @Override
    public void updateEntity(Object entity) {

    }

    @Override
    public void deleteEntity(Object entity) {

    }
}
