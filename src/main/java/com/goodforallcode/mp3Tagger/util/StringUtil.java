package com.goodforallcode.mp3Tagger.util;

import java.util.List;

public class StringUtil {

    public static String cleanupAlbum(String album){
        album=album.toLowerCase().replaceAll("disc 1","").replaceAll("disc one","").
                replaceAll("disc 2","").replaceAll("disc two","");
        List<String> replacements=List.of("seasons","sountrack","soundtrack");
        for(String replace:replacements){
            album=album.replaceFirst(replace,"");
        }
        return performCommonReplacements(album);
    }
    private static String replaceSpecialCharacters(String value){
        return value.replaceAll("é","e").replaceAll("è","e").replaceAll("â","a").
                replaceAll("î","i").replaceAll("ô","o").replaceAll("ñ","n")
                .replaceAll("ü","u").replaceAll("ï","i").replaceAll("ç", "c");
    }

    private static String performCommonReplacements(String value){
        value= value.replaceAll("\\.","").replaceAll("-","");
        return replaceSpecialCharacters(value);
    }
    public static String cleanupArtist(String value) {
        String result= value.toLowerCase().replaceAll("the "," ").replaceAll("tha "," ");
        result=result.replaceAll("7\" version","").replaceAll("12\" version","");
        result=result.replaceAll("é","e").replaceFirst("&","and")
                .replaceAll("[^a-zA-Z0-9\\[\\]() ]", "");
        return performCommonReplacements(result);
    }
    public static String cleanupTrack(String value) {
        List<String> replacements=List.of("old school","reunion","remix","extended","the ","original",
                //to match the numbers we removed
                "one","two","three","four","five","six","seven","eight","nine","ten","eleven","twelve","thirteen");
        String result=removeTrailingText(value).toLowerCase().replaceAll(" and ","").replaceAll(" the ","").replaceAll("7\" version","").replaceAll("12\" version","");
        if(result.endsWith(" th")){
            int start=result.lastIndexOf(" th");
            result=result.substring(0,start);
        }
        if(result.endsWith(" the")){
            int start=result.lastIndexOf(" the");
            result=result.substring(0,start);
        }
        for(String replace:replacements){
            result=result.replaceFirst(replace,"");
        }
        result= result.replaceAll("[^a-zA-Z\\[\\]()]", "");
        result=result.replaceAll("é","e").replaceAll("&","").replaceFirst("mix","");

        return performCommonReplacements(result).trim();
    }

    public static String removeTrailingText(String trackName){
        boolean hasTrailingText=false;
        if(trackName!=null) {
            trackName=trackName.toLowerCase();
            if (trackName.contains("[")) {
                trackName = trackName.substring(0, trackName.indexOf("[")).trim();
            }
            if (trackName.contains("(")) {
                trackName = trackName.substring(0, trackName.indexOf("(")).trim();
            }
        }
        return trackName;
    }
    public static boolean hasTrailingText(String trackName){
        boolean hasTrailingText=false;
        if (trackName.contains("[")) {
            hasTrailingText=true;
        } else if (trackName.contains("(")) {
            hasTrailingText=true;
        }
        return hasTrailingText;
    }
}
