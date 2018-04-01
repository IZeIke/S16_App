
package com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("edge_owner_to_timeline_media")
    @Expose
    private EdgeOwnerToTimelineMedia edgeOwnerToTimelineMedia;

    public EdgeOwnerToTimelineMedia getEdgeOwnerToTimelineMedia() {
        return edgeOwnerToTimelineMedia;
    }

    public void setEdgeOwnerToTimelineMedia(EdgeOwnerToTimelineMedia edgeOwnerToTimelineMedia) {
        this.edgeOwnerToTimelineMedia = edgeOwnerToTimelineMedia;
    }

}
