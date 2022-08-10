package com.mf.service;

import com.mf.dto.CompressDto;
import com.mf.dto.PictureDto;

public interface PictureService {

    void upload(PictureDto pictureDto);

    PictureDto download(long id);

    void compress(CompressDto compressDto);
}
