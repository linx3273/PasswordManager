package com.linx;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class DatabaseHandler {
    final private static MongoClient mongoClient = new MongoClient("localhost", 27017);
    final private static MongoDatabase mongoDatabase = mongoClient.getDatabase("password_manager");
    final private static MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("password_manager");

    public void addEntry(SavedPasswordDataClass savedPasswordDataClass){

        Document document = new Document();
        document.append("Description", savedPasswordDataClass.getDescription());
        document.append("Username", savedPasswordDataClass.getUsername());
        document.append("Password", savedPasswordDataClass.getPassword());
        mongoCollection.insertOne(document);
    }

    public void deleteEntry(SavedPasswordDataClass savedPasswordDataClass){
        mongoCollection.deleteOne(
                Filters.eq("_id", savedPasswordDataClass.getId())
        );
    }

    public void updateEntry(SavedPasswordDataClass savedPasswordDataClass){
        mongoCollection.updateOne(
                Filters.eq("_id", savedPasswordDataClass.getId()),
                Updates.combine(
                        Updates.set("Username", savedPasswordDataClass.getUsername()),
                        Updates.set("Password", savedPasswordDataClass.getPassword())
                )
        );
    }

    public ArrayList<SavedPasswordDataClass> findAll(){
        ArrayList<SavedPasswordDataClass> savedPasswords = new ArrayList<SavedPasswordDataClass>();
        FindIterable<Document> iterable = mongoCollection.find();

        for (Document entry: iterable){
            SavedPasswordDataClass passwordEntry = new SavedPasswordDataClass(
                    (ObjectId) entry.get("_id"),
                    (String) entry.get("Description"),
                    (String) entry.get("Username"),
                    (String) entry.get("Password")
            );
            savedPasswords.add(passwordEntry);
        }
        return savedPasswords;
    }
}
