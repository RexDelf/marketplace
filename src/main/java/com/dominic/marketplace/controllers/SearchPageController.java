package com.dominic.marketplace.controllers;

import com.dominic.marketplace.dto.AdvertCardDTO;
import com.dominic.marketplace.models.Advert;
import com.dominic.marketplace.models.AdvertImage;
import com.dominic.marketplace.services.AdvertService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class SearchPageController {

    @Autowired
    private ModelMapper modelMapper;

    private final AdvertService advertService;

    @GetMapping
    public List<AdvertCardDTO> getAdverts(){
        modelMapper.addConverter(converter);
        return advertService.findAll().stream().map(advert -> modelMapper.map(advert, AdvertCardDTO.class)).collect(Collectors.toList());
    }

    @PostMapping(value = {"/create"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> saveAdvert(@RequestPart Advert advert, @RequestPart MultipartFile[] images) throws Exception{
        try{
            Set<AdvertImage> advertImages = new LinkedHashSet<>();

            for(MultipartFile image: images){
                AdvertImage advertImage = new AdvertImage(
                        image.getOriginalFilename(),
                        image.getContentType(),
                        image.getBytes()
                );
                advertImages.add(advertImage);
            }
            advert.setAdvertImages(advertImages);
            advertService.save(advert);
            return ResponseEntity.ok().build();
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    Converter<Advert, AdvertCardDTO> converter = new AbstractConverter<Advert, AdvertCardDTO>() {
        @Override
        protected AdvertCardDTO convert(Advert source) {
            AdvertCardDTO destination = new AdvertCardDTO();
            Set<AdvertImage> sourceSet = source.getAdvertImages();

            destination.setId(source.getId());
            destination.setLocation(source.getLocation());
            destination.setPrice(source.getPrice());
            destination.setTitle(source.getTitle());
            destination.setCreatedAt(source.getCreatedAt());

            if(sourceSet.size() != 0){
                destination.setAdvertImage(sourceSet.stream().findFirst().get());
            }

            return destination;
        }
    };
}
