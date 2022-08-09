package com.mf.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.*;

public class CompressUtil {

    public static byte[] compress(ByteArrayInputStream fileInputStream) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
            Thumbnails.of(fileInputStream)
                    .size(120, 120)
                    .rotate(90)
                    .toOutputStream(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
