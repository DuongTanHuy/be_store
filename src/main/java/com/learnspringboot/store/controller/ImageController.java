package com.learnspringboot.store.controller;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.learnspringboot.store.dto.ImageDto;
import com.learnspringboot.store.model.Image;
import com.learnspringboot.store.response.ApiResponse;
import com.learnspringboot.store.service.image.IImageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files,
            @RequestParam Long productId) {
        List<ImageDto> imageDtos = imageService.saveImages(files, productId);
        return ResponseEntity.ok().body(new ApiResponse("Upload images successfully!", imageDtos));

    }

    @GetMapping("/{imageId}/download")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) {
        Image image = imageService.getImageById(imageId);

        try {
            ByteArrayResource resource = new ByteArrayResource(image.getImage());
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read image data", e);
        }

    }

    @PutMapping("/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestParam MultipartFile files) {
        imageService.updateImage(files, imageId);
        return ResponseEntity.ok(new ApiResponse("Update image successfully!", null));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }

}
