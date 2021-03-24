package com.example.mymusic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

@SerializedName("idUser")
@Expose
private String idUser;
@SerializedName("username")
@Expose
private String username;
@SerializedName("password")
@Expose
private String password;
@SerializedName("name")
@Expose
private String name;
@SerializedName("active")
@Expose
private String active;

public String getIdUser() {
return idUser;
}

public void setIdUser(String idUser) {
this.idUser = idUser;
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

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getActive() {
return active;
}

public void setActive(String active) {
this.active = active;
}

}