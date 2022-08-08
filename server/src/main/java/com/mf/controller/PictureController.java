package com.mf.controller;

import com.mf.dto.PictureDto;
import com.mf.service.PictureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @PostMapping("/picture/{id}")
    public void uploadResource(@PathVariable(name = "id") long userId, @RequestParam MultipartFile file) {
        try {
            byte [] data = file.getBytes();
            String filename = file.getOriginalFilename();
            String type = filename.substring(filename.lastIndexOf("."));
            PictureDto pictureDto = PictureDto.builder()
                    .userId(userId)
                    .filename(filename)
                    .type(type)
                    .data(data)
                    .build();
            pictureService.upload(pictureDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
