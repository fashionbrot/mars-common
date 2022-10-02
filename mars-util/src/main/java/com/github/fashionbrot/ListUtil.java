package com.github.fashionbrot;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fashi
 */
@Slf4j
public class ListUtil {

    /**
     * 深度copy
     * @param src src
     * @param <T> T
     * @return List
     */
    public static <T> List<T> deepCopy(List<T> src)  {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<T> dest = (List<T>) in.readObject();
            return dest;
        }catch (Exception e){
            log.error("deepCopy error",e);
        }
        return  null;
    }

    /**
     * 第一位和最后一位替换
     * @param list list
     */
    public static void swapList(List list){
        if (ObjectUtil.isNotEmpty(list)){
            for (int i = 0; i < list.size()-1; i++) {
                Collections.swap(list,i,i+1);
            }
        }
    }

}
