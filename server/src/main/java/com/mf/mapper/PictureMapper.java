package com.mf.mapper;

import com.mf.model.PictureDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PictureMapper {
    int insertPicture(PictureDo pictureDo);

    PictureDo getPictureById(Long id);

    PictureDo getPictureByUid(String id);


    void updatePictures (List<PictureDo> pictureDos);

    int deleteByUid(String uid);

//    List<PictureDo> getPictureByIds(List<Long> ids);
}
