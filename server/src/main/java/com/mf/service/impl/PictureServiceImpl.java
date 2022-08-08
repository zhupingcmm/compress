package com.mf.service.impl;

import com.mf.dto.PictureDto;
import com.mf.service.PictureService;
import com.mf.utils.CompressUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@Slf4j
public class PictureServiceImpl implements PictureService {

    @Override
    public void upload(PictureDto pictureDto) {
        CompressUtil.compress(pictureDto.getFilename(), new ByteArrayInputStream(pictureDto.getData()));
    }
}
