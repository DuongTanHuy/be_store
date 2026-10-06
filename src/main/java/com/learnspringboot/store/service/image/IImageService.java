package com.learnspringboot.store.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.learnspringboot.store.dto.ImageDto;
import com.learnspringboot.store.model.Image;

public interface IImageService {
    Image getImageById(Long id);

    void deleteImage(Long id);

    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);

    void updateImage(MultipartFile file, Long imageId);
}
