package com.mf.controller;

import com.common.base.BaseResponse;
import com.mf.dto.PictureDto;
import com.mf.service.PictureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/picture")
public class PictureController {

    private final PictureService pictureService;

    @PostMapping("/{id}")
    public BaseResponse uploadResource(@PathVariable(name = "id") long userId, @RequestParam MultipartFile file) {
        try {
            byte [] data = file.getBytes();
            String filename = file.getOriginalFilename();
            String type = filename.substring(filename.lastIndexOf("."));
            PictureDto pictureDto = PictureDto.builder()
                    .userId(userId)
                    .compressSettingId(1l)
                    .filename(filename)
                    .type(type)
                    .data(data)
                    .build();
            pictureService.upload(pictureDto);
            return BaseResponse.success();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public BaseResponse<PictureDto> downloads(HttpServletResponse response, @PathVariable long id) {

        try {
            PictureDto pictureDto = pictureService.download(id);
            ServletOutputStream writer = response.getOutputStream();
            response.setHeader("content-disposition", "attachment; fileName=" + pictureDto.getFilename());
            writer.write(pictureDto.getData());
            return BaseResponse.success(pictureDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}