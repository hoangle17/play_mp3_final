package com.example.mymusic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Album implements Serializable {

    @SerializedName("idAlbum")
    @Expose
    private String idAlbum;
    @SerializedName("nameAlbum")
    @Expose
    private String nameAlbum;
    @SerializedName("singerAlbum")
    @Expose
    private String singerAlbum;
    @SerializedName("imageAlbum")
    @Expose
    private String imageAlbum;

    public String getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(String idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getNameAlbum() {
        return nameAlbum;
    }

    public void setNameAlbum(String nameAlbum) {
        this.nameAlbum = nameAlbum;
    }

    public String getSingerAlbum() {
        return singerAlbum;
    }

    public void setSingerAlbum(String singerAlbum) {
        this.singerAlbum = singerAlbum;
    }

    public String getImageAlbum() {
        return imageAlbum;
    }

    public void setImageAlbum(String imageAlbum) {
        this.imageAlbum = imageAlbum;
    }

}