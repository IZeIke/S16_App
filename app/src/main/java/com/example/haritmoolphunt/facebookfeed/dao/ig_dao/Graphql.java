
package com.example.haritmoolphunt.facebookfeed.dao.ig_dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Graphql {

    @SerializedName("user")
    @Expose
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
