package com.mf.service.impl;

import com.mf.dto.WeblogDto;
import com.mf.mapper.WeblogMapper;
import com.mf.model.WeblogDo;
import com.mf.service.WeblogService;
import com.mf.utils.ObjectTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeblogServiceImpl implements WeblogService<WeblogDto> {

    private final WeblogMapper weblogMapper;


    @Override
    public void insertOneLog(WeblogDto weblogDto) {
        WeblogDo weblogDo = ObjectTransformer.transform(weblogDto, WeblogDo.class);
        weblogMapper.insertWeblog(weblogDo);
    }
}
