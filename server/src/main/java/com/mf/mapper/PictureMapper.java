package com.mf.mapper;

import com.mf.model.PictureDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PictureMapper {
    int insertPicture(PictureDo pictureDo);

    PictureDo getPictureById(Long id);
}
