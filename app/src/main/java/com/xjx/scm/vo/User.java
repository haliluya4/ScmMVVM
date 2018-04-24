package com.xjx.scm.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * 用户
 */
@Entity
public class User {
    @PrimaryKey
    private int id;
    @SerializedName("username")
    private String userName;
    private String name;
    private String agentName;
    private String token;

    @Ignore
    public User(int id, @NonNull String token) {
        this.id = id;
        this.token = token;
    }

    public User(int id, @NonNull String userName, @NonNull String name, @NonNull String agentName, String token) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.agentName = agentName;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
