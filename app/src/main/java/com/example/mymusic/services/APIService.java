package com.example.mymusic.services;

public class APIService {
    private static String url = "https://hoanglv2410.000webhostapp.com/Server/";

    public static DataService getService(){
        return APIRetrofitClient.getClient(url).create(DataService.class);
    }
}
