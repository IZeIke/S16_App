package com.example.haritmoolphunt.facebookfeed.event;

/**
 * Created by Harit Moolphunt on 24/2/2561.
 */

public class BusEvent {

    public static class LoginEvent{}

    public static class HideEvent { /* Additional fields if needed */ }

    public static class ShowEvent { /* Additional fields if needed */ }

    public static class PhotoActivityEvent{

        private String[] urlList;

        public PhotoActivityEvent(String[] List){
            urlList = List;
        }

        public String[] getUrlList(){
            return urlList;
        }
    }

    public static class ProfileActivityEvent{

    }

}
