package com.dominic.marketplace.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "advertImage")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "advert_image_seq")
    private Long id;

    private String name;

    private String type;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    public AdvertImage(String name, String type, byte[] imageData) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
    }
}
