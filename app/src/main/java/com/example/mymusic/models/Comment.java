package com.example.mymusic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("idUser")
    @Expose
    private String idUser;
    @SerializedName("idSong")
    @Expose
    private String idSong;
    @SerializedName("nameuser")
    @Expose
    private String nameuser;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("time")
    @Expose
    private String time;

    public Comment(String idUser, String idSong, String nameuser, String content, String time) {
        this.idUser = idUser;
        this.idSong = idSong;
        this.nameuser = nameuser;
        this.content = content;
        this.time = time;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdSong() {
        return idSong;
    }

    public void setIdSong(String idSong) {
        this.idSong = idSong;
    }

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}