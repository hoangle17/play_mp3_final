package com.example.mymusic.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Song implements Parcelable {

    @SerializedName("idSong")
    @Expose
    private String idSong;
    @SerializedName("nameSong")
    @Expose
    private String nameSong;
    @SerializedName("imageSong")
    @Expose
    private String imageSong;
    @SerializedName("singer")
    @Expose
    private String singer;
    @SerializedName("linkSong")
    @Expose
    private String linkSong;
    @SerializedName("liked")
    @Expose
    private String liked;

    protected Song(Parcel in) {
        idSong = in.readString();
        nameSong = in.readString();
        imageSong = in.readString();
        singer = in.readString();
        linkSong = in.readString();
        liked = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getIdSong() {
        return idSong;
    }

    public void setIdSong(String idSong) {
        this.idSong = idSong;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getImageSong() {
        return imageSong;
    }

    public void setImageSong(String imageSong) {
        this.imageSong = imageSong;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getLinkSong() {
        return linkSong;
    }

    public void setLinkSong(String linkSong) {
        this.linkSong = linkSong;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idSong);
        dest.writeString(nameSong);
        dest.writeString(imageSong);
        dest.writeString(singer);
        dest.writeString(linkSong);
        dest.writeString(liked);
    }
}