/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twiceagain.selescript.interpreter.runtime;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.twiceagain.selescript.SSConfig;
import java.util.Map;
import org.bson.Document;

/**
 * Access layer to the mongo db. Uses lazy initialisation.
 *
 * @author xavier
 */
public class SSMongo {

    private static MongoClient CLIENT = null;
    private final SSConfig config;

    SSMongo(SSConfig config) {

        this.config = config;
    }

    /**
     * Lazy insert. Will create a MongoClient if none is yet available.
     *
     * @param data - a Map with the document key/value.
     */
    public void insert(Map<String, String> data) {

        if (CLIENT == null) {
            CLIENT = MongoClients.create(config.getMongoConnexionString());
        }

        Document doc = new Document();
        data.keySet().forEach((k) -> {
            doc.append(k, data.get(k));
        });
        Document dd = new Document("data", doc).append("Selescript", config.getSelescriptVersion());
        
        
        // add debugging log in debug mode ?
        
        CLIENT
                .getDatabase(config.getMongoDatabase())
                .getCollection(config.getMongoCollection())
                .insertOne(dd);

    }
}
