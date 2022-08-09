package com.mf.service.impl;

import com.mf.dto.PictureDto;
import com.mf.mapper.PictureMapper;
import com.mf.model.PictureDo;
import com.mf.service.PictureService;
import com.mf.utils.CompressUtil;
import com.mf.utils.ObjectTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

    private final PictureMapper pictureMapper;
    @Override
    public void upload(PictureDto pictureDto) {
        PictureDo pictureDo = ObjectTransformer.transform(pictureDto, PictureDo.class);
        pictureMapper.insertPicture(pictureDo);
    }

    @Override
    public PictureDto download(long id) {
        PictureDo pictureDo = pictureMapper.getPictureById(id);
        return ObjectTransformer.transform(pictureDo, PictureDto.class);
    }


}
