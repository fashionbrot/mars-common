package com.github.fashionbrot;

import java.util.UUID;

/**
 * @author fashi
 */
public class UuidUtil {

    private UuidUtil(){}

    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
