package com.example.mymusic.services;

import com.example.mymusic.models.Advertise;
import com.example.mymusic.models.Album;
import com.example.mymusic.models.Comment;
import com.example.mymusic.models.Genre;
import com.example.mymusic.models.Playlist;
import com.example.mymusic.models.Song;
import com.example.mymusic.models.Topic;
import com.example.mymusic.models.TopicAndGenre;
import com.example.mymusic.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET("songbanner.php")
    Call<List<Advertise>> getDataBanner();

    @GET("playlistforcurrentday.php")
    Call<List<Playlist>> getPlaylistCurrentDay();

    @GET("topicandgenrecurrentday.php")
    Call<TopicAndGenre> getDataTopicAndGenre();

    @GET("albumhot.php")
    Call<List<Album>> getDataAlbum();

    @GET("hotsong.php")
    Call<List<Song>> getDataHotSong();

    @FormUrlEncoded
    @POST("listsongs.php")
    Call<List<Song>> getListAdvertiseSongs(@Field("idAdvertise") String idAdvertise);

    @FormUrlEncoded
    @POST("listsongs.php")
    Call<List<Song>> getListPlaylistSongs(@Field("idPlaylist") String idPlaylist);

    @GET("allplaylist.php")
    Call<List<Playlist>> getAllPlaylists();

    @FormUrlEncoded
    @POST("listsongs.php")
    Call<List<Song>> getListGenreSongs(@Field("idGenre") String idGenre);

    @GET("alltopic.php")
    Call<List<Topic>> getAllTopics();

    @FormUrlEncoded
    @POST("genrefromtopic.php")
    Call<List<Genre>> getGenreFromTopic(@Field("idTopic") String idTopic);

    @GET("allalbum.php")
    Call<List<Album>> getAllAlbums();

    @FormUrlEncoded
    @POST("listsongs.php")
    Call<List<Song>> getListAlbumSongs(@Field("idAlbum") String idAlbum);

    @FormUrlEncoded
    @POST("updateliked.php")
    Call<String> updateLiked(@Field("liked") String liked, @Field("idSong") String idSong);

    @FormUrlEncoded
    @POST("search.php")
    Call<List<Song>> getSearch(@Field("keySearch") String keySearch);

    @FormUrlEncoded
    @POST("insertuser.php")
    Call<String> createUser(@Field("username") String username, @Field("password") String password, @Field("name") String name);

    @FormUrlEncoded
    @POST("login.php")
    Call<User> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("favorite.php")
    Call<String> favorite(@Field("idSong") String idSong, @Field("idUser") String idUser);

    @FormUrlEncoded
    @POST("listsongs.php")
    Call<List<Song>> getFavoriteSongs(@Field("idUser") String idUser);

    @FormUrlEncoded
    @POST("returnuserfromgg.php")
    Call<User> returnUserGG(@Field("username") String username, @Field("password") String password, @Field("name") String name);

    @FormUrlEncoded
    @POST("getcomment.php")
    Call<List<Comment>> getAllComment(@Field("idSong") String idSong);

    @FormUrlEncoded
    @POST("sendcomment.php")
    Call<String> sendComment(@Field("idUser") String idUser,
                             @Field("idSong") String idSong,
                             @Field("nameuser") String nameuser,
                             @Field("content") String content,
                             @Field("time") String time);
}
