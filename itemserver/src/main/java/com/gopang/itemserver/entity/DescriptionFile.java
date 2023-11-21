package com.gopang.itemserver.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "description_file")
public class DescriptionFile {

    @Id
    @Column(name = "description_file_id")
    private Long descriptionFileId;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "save_name")
    private String saveName;

    @Column(name = "file_size")
    private String fileSize;

    @Column(name = "file_key")
    private String fileKey;

    @Column(name = "file_path")
    private String filePath;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Item item;
}
