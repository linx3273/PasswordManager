package com.linx;

import com.linx.PasswordStorer.PasswordDataClass;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class DatabaseHandler {
     private static final MongoClient mongoClient = new MongoClient("localhost", 27017);
     private static final MongoDatabase mongoDatabase = mongoClient.getDatabase("password_manager");
     private static final MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("saved");
     private static final MongoCollection<Document> masterMongoCollection = mongoDatabase.getCollection("master");


    public void addEntry(PasswordDataClass passwordDataClass){
        Document document = new Document();
        document.append("Description", passwordDataClass.getDescription());
        document.append("Username", passwordDataClass.getUsername());
        document.append("Password", passwordDataClass.getPassword());
        mongoCollection.insertOne(document);
    }

    public void deleteEntry(PasswordDataClass passwordDataClass){
        mongoCollection.deleteOne(
                Filters.eq("_id", passwordDataClass.getId())
        );
    }

    public void updateEntry(PasswordDataClass passwordDataClass){
        mongoCollection.updateOne(
                Filters.eq("_id", passwordDataClass.getId()),
                Updates.combine(
                        Updates.set("Username", passwordDataClass.getUsername()),
                        Updates.set("Password", passwordDataClass.getPassword())
                )
        );
    }

    public ArrayList<PasswordDataClass> fetchAll(){
        ArrayList<PasswordDataClass> savedPasswords = new ArrayList<PasswordDataClass>();
        FindIterable<Document> iterable = mongoCollection.find();

        for (Document entry: iterable){
            PasswordDataClass passwordEntry = new PasswordDataClass(
                    (ObjectId) entry.get("_id"),
                    (String) entry.get("Description"),
                    (String) entry.get("Username"),
                    (String) entry.get("Password")
            );
            savedPasswords.add(passwordEntry);
        }
        return savedPasswords;
    }

    public void createMasterPassword(String password) throws NoSuchAlgorithmException {
        String pass_enc = new Encryption().encrypt(password);

        Document document = new Document("password", pass_enc);
        masterMongoCollection.insertOne(document);
    }

    public boolean checkMasterPassword(String password) throws NoSuchAlgorithmException {
        String pass_enc = new Encryption().encrypt(password);

        FindIterable<Document> iterable = masterMongoCollection.find();
        String key = "";

        for (Document entry: iterable){
            key = (String) entry.get("password");
        }

        if (pass_enc.equals(key)){
            return true;
        }
        return false;
    }
}
