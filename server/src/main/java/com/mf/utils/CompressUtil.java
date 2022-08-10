package com.mf.utils;

import com.mf.dto.CompressDto;
import net.coobird.thumbnailator.Thumbnails;

import java.io.*;

public class CompressUtil {

    public static byte[] compress(ByteArrayInputStream fileInputStream, CompressDto compressDto) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
            Thumbnails.of(fileInputStream)
                    .size(compressDto.getCompressProfile().getHeight(), compressDto.getCompressProfile().getWidth())
                    .rotate(compressDto.getCompressProfile().getAngle())
                    .toOutputStream(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
