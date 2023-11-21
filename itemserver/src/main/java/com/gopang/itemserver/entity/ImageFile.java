package com.gopang.itemserver.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "image_file")
public class ImageFile {

    public enum ImageType { MAIN, SUB }

    @Id @GeneratedValue
    @Column(name = "image_file_id")
    private Long imageFileId;

    @Enumerated(EnumType.STRING)
    @Column(name = "image_type")
    private ImageType imageType;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "save_name")
    private String saveName;

    @Column(name = "image_size")
    private String imageSize;

    @Column(name = "image_key")
    private String imageKey;

    @Column(name = "image_path")
    private String imagePath;

}
