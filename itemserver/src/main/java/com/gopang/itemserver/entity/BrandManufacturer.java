package com.gopang.itemserver.entity;

import com.gopang.itemserver.dto.request.item.update.BrandManufacturerUpdateDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "Brand_Manufacturer")
@NoArgsConstructor
public class BrandManufacturer {

    @Id @GeneratedValue
    @Column(name = "brand_manufacturer_id")
    private Long brandManufacturerId;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "manufacturer_name")
    private String manufacturerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public BrandManufacturer(Long brandManufacturerId, String brandName, String manufacturerName, Item item) {
        this.brandManufacturerId = brandManufacturerId;
        this.brandName = brandName;
        this.manufacturerName = manufacturerName;
        this.item = item;
    }

    //== 양방향 연관관계 편의 메소드 ==//
    public void setItem(Item item) {
        this.item = item;
    }

    public void update(BrandManufacturerUpdateDto dto) {
        this.brandName = dto.getBrandName();
        this.manufacturerName = dto.getManufacturerName();
    }
}
