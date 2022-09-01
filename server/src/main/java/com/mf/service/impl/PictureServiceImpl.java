package com.mf.service.impl;

import com.common.base.Asset;
import com.common.base.ResponseEnum;
import com.common.exception.CompressException;
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
        try {
            PictureDo pictureDo = ObjectTransformer.transform(pictureDto, PictureDo.class);
            int result = pictureMapper.insertPicture(pictureDo);
            Asset.singleRowAffected(result);
            log.info("user {} success upload [{}] to server", pictureDo.getUserId(), pictureDo.getName());
        } catch (Exception e) {
            log.error("Failed to inset picture: {} with error {}", pictureDto.getName(), String.valueOf(e));
            throw new CompressException(ResponseEnum.FAILED_UPLOAD);
        }


    }
    @Override
    public PictureDto download(long id) {
        PictureDo pictureDo = pictureMapper.getPictureById(id);
        return ObjectTransformer.transform(pictureDo, PictureDto.class);
    }

    @Override
    public List<PictureDto> compress(CompressDto compressDto) {
        List<String> ids= compressDto.getUids();
        List<PictureDo> pictureDos = ids.stream()
                .map(pictureMapper::getPictureByUid)
                .peek(p -> {
                    byte[] data = CompressUtil.compress(new ByteArrayInputStream(p.getData()), compressDto);
                    p.setData(data);
                    p.setSize((long) data.length);
                }).collect(Collectors.toList());

        pictureMapper.updatePictures(pictureDos);
        return ObjectTransformer.transform(pictureDos,PictureDto.class);
    }

    @Override
    public void deleteByUid(String uid) {
        int result = pictureMapper.deleteByUid(uid);
        Asset.singleRowAffected(result);
    }

    @Override
    public void deleteByName(String name) {
        int result = pictureMapper.deleteByName(name);
        log.info("Delete {} from db successfully.", name);
        Asset.singleRowAffected(result);
    }

    @Override
    public PictureDto findByName(String name) {
        PictureDo pictureDo = pictureMapper.findByName(name);
        return pictureDo != null ? ObjectTransformer.transform(pictureMapper.findByName(name), PictureDto.class) : null;
    }

}
