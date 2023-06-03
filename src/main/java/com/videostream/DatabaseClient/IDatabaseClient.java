package com.videostream.DatabaseClient;

import org.bson.BsonDocument;
import org.bson.Document;

public interface IDatabaseClient {
    <T> T getEntityById(String id, Class<T> resultClass);
    <T> void saveEntity(T entity);
    <T> void updateEntity(T entity);
    <T> void deleteEntity(T entity);
}
