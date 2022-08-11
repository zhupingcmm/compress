package com.mf.service;

import com.mf.dto.CompressDto;
import com.mf.dto.PictureDto;

import java.util.List;

public interface PictureService {

    void upload(PictureDto pictureDto);

    PictureDto download(long id);

    List<PictureDto> compress(CompressDto compressDto);

    void deleteByUid(String uid);
}
