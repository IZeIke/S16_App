
package com.example.haritmoolphunt.facebookfeed.dao.ig_dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("biography")
    @Expose
    private String biography;
    @SerializedName("edge_followed_by")
    @Expose
    private EdgeFollowedBy edgeFollowedBy;
    @SerializedName("followed_by_viewer")
    @Expose
    private Boolean followedByViewer;
    @SerializedName("edge_follow")
    @Expose
    private EdgeFollow edgeFollow;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("profile_pic_url_hd")
    @Expose
    private String profilePicUrlHd;
    @SerializedName("username")
    @Expose
    private String username;

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public EdgeFollowedBy getEdgeFollowedBy() {
        return edgeFollowedBy;
    }

    public void setEdgeFollowedBy(EdgeFollowedBy edgeFollowedBy) {
        this.edgeFollowedBy = edgeFollowedBy;
    }

    public Boolean getFollowedByViewer() {
        return followedByViewer;
    }

    public void setFollowedByViewer(Boolean followedByViewer) {
        this.followedByViewer = followedByViewer;
    }

    public EdgeFollow getEdgeFollow() {
        return edgeFollow;
    }

    public void setEdgeFollow(EdgeFollow edgeFollow) {
        this.edgeFollow = edgeFollow;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfilePicUrlHd() {
        return profilePicUrlHd;
    }

    public void setProfilePicUrlHd(String profilePicUrlHd) {
        this.profilePicUrlHd = profilePicUrlHd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
