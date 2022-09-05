package com.mf.mapper;

import com.mf.model.WeblogDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeblogMapper {
    int insertWeblog(WeblogDo weblogDo);
}
