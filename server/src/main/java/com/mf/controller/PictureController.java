package com.mf.controller;

import com.common.base.BaseResponse;
import com.common.base.ResponseEnum;
import com.common.exception.CompressException;
import com.mf.dto.CompressDto;
import com.mf.dto.PictureDto;
import com.mf.service.PictureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/picture")
public class PictureController {

    private final PictureService pictureService;

    @PostMapping("/{userid}")
    public BaseResponse uploadResource(@PathVariable(name = "userid") long userId,  @RequestParam MultipartFile file) {
        try {
            byte [] data = file.getBytes();
            long size = file.getSize();
            String name = file.getOriginalFilename();
            String type = file.getContentType();

            if (pictureService.findByName(name) != null) {
                throw new CompressException(ResponseEnum.DUPLICATE_FILE);
            }

            PictureDto pictureDto = PictureDto.builder()
                    .userId(userId)
                    .size(size)
                    .compressSettingId(1l)
                    .name(name)
                    .type(type)
                    .data(data)
                    .build();
            pictureService.upload(pictureDto);
            return BaseResponse.success();
        } catch (IOException e) {
            throw new CompressException(ResponseEnum.FAILED_UPLOAD);
        }
    }

    @PostMapping("/compress")
    public BaseResponse<List<PictureDto>> compress(@RequestBody CompressDto compressDto){
        return BaseResponse.success(pictureService.compress(compressDto));
    }

    @GetMapping("/{id}")
    public BaseResponse<PictureDto> downloads(HttpServletResponse response, @PathVariable long id) {
        try (ServletOutputStream writer = response.getOutputStream()){
            PictureDto pictureDto = pictureService.download(id);
            response.setHeader("content-disposition", "attachment; fileName=" + pictureDto.getName());
            writer.write(pictureDto.getData());
            return BaseResponse.success(pictureDto);
        } catch (IOException e) {
            throw new CompressException(ResponseEnum.FAILED_DOWNLOAD);
        }
    }

    @DeleteMapping("/{name}")
    public BaseResponse remove(@PathVariable String name){
        pictureService.deleteByName(name);
        return BaseResponse.success();
    }


}
