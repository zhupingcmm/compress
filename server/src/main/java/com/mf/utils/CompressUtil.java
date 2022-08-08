package com.mf.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.*;

public class CompressUtil {

    public static FileOutputStream compress(String filename, ByteArrayInputStream fileInputStream) {
        String fileName = CompressUtil.class.getClassLoader().getResource(filename).getPath();
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));){
            Thumbnails.of(fileInputStream)
                    .size(120, 120)
                    .rotate(90)
                    .toOutputStream(fileOutputStream);
            return fileOutputStream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
