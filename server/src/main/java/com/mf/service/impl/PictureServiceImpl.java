package com.mf.service.impl;

import com.mf.dto.CompressDto;
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
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<PictureDto> compress(CompressDto compressDto) {
        List<Long> ids= compressDto.getPictureIds();
        List<PictureDo> pictureDos = ids.stream()
                .map(id -> pictureMapper.getPictureById(id))
                .map(p -> {
                    byte[] data = CompressUtil.compress(new ByteArrayInputStream(p.getData()), compressDto);
                    p.setData(data);
                    return p;
                }).collect(Collectors.toList());

        pictureMapper.updatePictures(pictureDos);
        return ObjectTransformer.transform(pictureDos,PictureDto.class);
    }


}
