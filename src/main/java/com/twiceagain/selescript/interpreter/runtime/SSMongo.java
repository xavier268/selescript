/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.interpreter.runtime;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.twiceagain.selescript.exceptions.SSDatabaseException;
import java.util.Map;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;

/**
 * Access layer to the mongo db. Uses lazy initialisation.
 *
 * @author xavier
 */
public class SSMongo {

    private static MongoClient CLIENT = null;
    private static MongoDatabase DATABASE = null;
    private static MongoCollection<Document> COLLECTION = null;
    private final SSRuntimeContext rtc;

    SSMongo(SSRuntimeContext rtc) {
        this.rtc = rtc;
    }

    /**
     * In debug mode, will return a string representation of the inserted
     * document.
     *
     * @param data
     * @return
     */
    public String insert(Map<String, Object> data) {
        if (CLIENT == null) {
            CLIENT = MongoClients.create(rtc.getConfig().getMongoConnexionString());
            DATABASE = CLIENT.getDatabase(rtc.getConfig().getMongoDatabase());
            COLLECTION = DATABASE.getCollection(rtc.getConfig().getMongoCollection());
        }

        Document doc = new Document("selescript", rtc.getConfig().getSelescriptVersion())
                .append("data", new Document(data));
        COLLECTION.insertOne(doc, (Void result, Throwable ex) -> {
            if (ex != null) {
                throw new SSDatabaseException(ex);
            }
        });
        if (rtc.getConfig().isDebug()) {
            return doc.toJson(JsonWriterSettings.builder().indent(true).build());
        } else {
            return null;
        }
    }
}


