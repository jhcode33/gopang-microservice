package com.gopang.itemserver.entity;

import com.gopang.itemserver.common.BaseTimeEntity;
import com.gopang.itemserver.dto.request.item.update.ItemDetailUpdateDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "Item_Detail")
@NoArgsConstructor
public class ItemDetail extends BaseTimeEntity {

    public enum ParallelType { 병행수입, 병행수입아님 }
    public enum MinorType { 미성년자구입, 미성년자불가 }
    public enum TaxType { 과세, 면세 }

    @Id @GeneratedValue
    @Column(name = "item_detail_id")
    private Long ItemDetailId;

    @Column(name = "max_order")
    private String maxOrder;

    @Enumerated(EnumType.STRING)
    private ParallelType parallelType;

    @Enumerated(EnumType.STRING)
    private MinorType minorType;

    @Enumerated(EnumType.STRING)
    private TaxType taxType;

    //제조자
    private String maker;

    //제품명
    @Column(name = "manufacturer_item_name")
    private String manufacturerItemName;

    //취급시 주의사항
    private String warning;

    //품질보증기준
    @Column(name = "quality_assurance")
    private String qualityAssurance;

    @Column(name = "as_info")
    private String asInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public ItemDetail(Long itemDetailId, String maxOrder, ParallelType parallelType, MinorType minorType, TaxType taxType, String maker, String manufacturerItemName, String warning, String qualityAssurance, String asInfo, Item item) {
        this.ItemDetailId = itemDetailId;
        this.maxOrder = maxOrder;
        this.parallelType = parallelType;
        this.minorType = minorType;
        this.taxType = taxType;
        this.maker = maker;
        this.manufacturerItemName = manufacturerItemName;
        this.warning = warning;
        this.qualityAssurance = qualityAssurance;
        this.asInfo = asInfo;
        this.item = item;
    }

    //== 양방향 연관관계 편의 메소드 ==//
    public void setItem(Item item) {
        this.item = item;
    }

    public void update(ItemDetailUpdateDto dto) {
        this.maxOrder = dto.getMaxOrder();
        this.parallelType = ParallelType.valueOf(dto.getParallelType());
        this.minorType = MinorType.valueOf(dto.getMinorType());
        this.taxType = TaxType.valueOf(dto.getTaxType());
        this.maker = dto.getMaker();
        this.manufacturerItemName = dto.getManufacturerItemName();
        this.warning = dto.getWarning();
        this.qualityAssurance = dto.getQualityAssurance();
        this.asInfo = dto.getAsInfo();
    }
}
