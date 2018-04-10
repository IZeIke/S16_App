package com.example.haritmoolphunt.facebookfeed.manager.helper;

/**
 * Created by Harit Moolphunt on 1/4/2561.
 */

public class NameListCollector {

    private String fbId;
    private String igId;

    public NameListCollector(String fb,String ig){
        fbId = fb;
        igId = ig;
    }

    public void setFbId(String fb){
        this.fbId = fb;
    }

    public void setIgId(String ig){
        this.igId = ig;
    }

    public String getFbId(){
        return fbId;
    }

    public String getIgId(){
        return igId;
    }

    public static String findIgFromFbID(String fbId){
        for(int i=0;i<nameIGList.length;i++)
        {
            if(nameId[i].compareTo(fbId) == 0){
                return nameIGList[i]+","+idIGList[i];
            }
        }
        return null;
    }

    public static String findNameFromFbID(String fbId){
        for(int i=0;i<nameIGList.length;i++)
        {
            if(nameId[i].compareTo(fbId) == 0){
                return nameList[i];
            }
        }
        return null;
    }

    public static final String[] nameList = {"Mahnmook","Music","Mint","Ant","Ae","Fame","Pada","Petch","Proud","Pim","Sonja","Anny","Nink","Orn"};
    public static final String[] nameId = {"1705672169741865","108567926503749","138188866783330","445003689233126","709046175950703","1913673758896179","260045927831231","108001133246382","120406398606162","799444370230832","111090532897541","809228849236630","333542507094850","737460709751015"};
    public static final String[] nameIGList = {"mahnmoo_k","musizicc","minnie_minto","anant.wrnd","ae_khunwassana","fame.babycat","padavenus","pansadiamondp","me_so_proud","pimkhajon","sonja_donnelly","annyanni_","hermionink"};
    public static final String[] idIGList = {"14531662","365940386","183683575","207448830","1258755888","342005484","193904360","32491004","3229691469","29720794","4965671393","578578647","1180519026"};
}
