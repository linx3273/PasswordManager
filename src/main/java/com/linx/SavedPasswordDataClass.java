package com.linx;

import org.bson.types.ObjectId;

import java.util.Objects;

public class SavedPasswordDataClass {
    private ObjectId id;
    private String description;
    private String username;
    private String password;

    public SavedPasswordDataClass(ObjectId id, String description, String username, String password) {
        this.id = id;
        this.description = description;
        this.username = username;
        this.password = password;
    }

    public SavedPasswordDataClass() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavedPasswordDataClass that = (SavedPasswordDataClass) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
