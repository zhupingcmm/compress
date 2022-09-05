package com.mf.service;

import com.mf.dto.WeblogDto;

public interface WeblogService<T> {
    void insertOneLog(T element);
}
