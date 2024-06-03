package com.goodforallcode.mp3Tagger.util;


import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNameUtil {
    public static Integer getTrackNumberFromName(String name){
        Pattern digitRegex = Pattern.compile("\\d+");
        Matcher digits = digitRegex.matcher(name);
        Integer result=null;
        if (digits.find()) {
            result=Integer.parseInt(digits.group());
        }
        return result;
    }
    public static String getCleanFileName(Path file){
        return getCleanFileName(file,false);
    }

    public static String getCleanFileName(Path file, boolean ignoreDashes){
        String name=file.getFileName().toString();
        return getCleanFileName(name);
    }

    public static String getCleanFileName(String name) {
        return getCleanFileName(name,false);
    }
    public static String getCleanFileName(String name, boolean ignoreDashes) {
        name = name.replaceAll("\\.mp3","");
        if(name.startsWith("0")){
            name = name.substring(1);
        }
        Integer trackNumber= FileNameUtil.getTrackNumberFromName(name);
        if(trackNumber!=null) {
            name = name.replaceFirst(trackNumber.toString(), "");
            if (name.startsWith(" - ")) {
                name = name.substring(3);
            }else if (name.startsWith(" ")) {
                name = name.substring(1);
            }
        }
        name=name.replaceAll("\\[","")
                .replaceAll("\\]","").replaceAll("\\(demo\\)","");
        if(!ignoreDashes){
            name=name.replaceAll("-","");
        }
        return name;
    }
}
