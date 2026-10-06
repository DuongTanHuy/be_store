package com.learnspringboot.store.service.image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.learnspringboot.store.dto.ImageDto;
import com.learnspringboot.store.exceptions.ResourceNotFoundException;
import com.learnspringboot.store.model.Image;
import com.learnspringboot.store.model.Product;
import com.learnspringboot.store.repository.ImageRepository;
import com.learnspringboot.store.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    @Value("${api.prefix}")
    private String apiPrefix;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    private ImageDto mapToImageDto(Image image) {
        // ImageDto imageDto = new ImageDto();
        // imageDto.setImageId(image.getId());
        // imageDto.setImageName(image.getFileName());
        // imageDto.setDownloadUrl(image.getDownloadUrl());
        // return imageDto;
        ImageDto imageDto = modelMapper.map(image, ImageDto.class);
        return imageDto;
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found!"));
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
                () -> new ResourceNotFoundException("Image not found!"));
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productRepository.getReferenceById(productId);
        List<ImageDto> savedImageDtos = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(file.getBytes());
                image.setProduct(product);

                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(apiPrefix + "/image/" + savedImage.getId() + "/download");
                imageRepository.save(savedImage);

                savedImageDtos.add(mapToImageDto(savedImage));
            } catch (IOException e) {
                throw new RuntimeException("Error saving image!", e);
            }
        }

        return savedImageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(file.getBytes());
            imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("Error updating image!", e);
        }
    }

}
