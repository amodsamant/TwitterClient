package com.twitterclient.utils;

/**
 * Created by Amod on 3/26/17.
 */

public class GenericUtils {

    public static String modifyProfileImageUrl(String defaultUrl) {

        return defaultUrl.replace("_normal.", "_bigger.");

    }

}
