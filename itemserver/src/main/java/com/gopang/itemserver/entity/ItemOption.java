package com.gopang.itemserver.entity;

import com.gopang.itemserver.dto.request.item.update.ItemOptionUpdateDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "item_option")
@NoArgsConstructor
public class ItemOption {

    @Id @GeneratedValue
    @Column(name = "item_option_id")
    private Long itemOptionId;

    @Column(name = "item_model_name")
    private String itemModelName;

    @Column(name = "seller_item_number")
    private String sellerItemNumber;

    @Column(name = "option_name")
    private String optionName;

    // 상품 옵션별 판매 개수
    @Column(name = "sell_amount")
    private Long sellAmount;

    // 정상가
    @Column(name = "normal_cost")
    private Long normalCost;

    // 할인가
    @Column(name = "discount_cost")
    private Long discountCost;

    // 판매가
    @Column(name = "sell_cost")
    private Long sellCost;

    // 재고 수량
    @Column(name = "inventory_amount")
    private Long inventoryAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public ItemOption(Long itemOptionId, String itemModelName, String sellerItemNumber, String optionName, Long sellAmount, Long normalCost, Long discountCost, Long sellCost, Long inventoryAmount) {
        this.itemOptionId = itemOptionId;
        this.itemModelName = itemModelName;
        this.sellerItemNumber = sellerItemNumber;
        this.optionName = optionName;
        this.sellAmount = sellAmount;
        this.normalCost = normalCost;
        this.discountCost = discountCost;
        this.sellCost = sellCost;
        this.inventoryAmount = inventoryAmount;
    }

    //== 양방향 연관관계 편의 메소드 ==//
    public void setItem(Item item) {
        this.item = item;
        item.getOptions().add(this); // 양방향 연관관계 설정
    }

    public void update(ItemOptionUpdateDto dto) {
        this.itemModelName = dto.getItemModelName();
        this.sellerItemNumber = dto.getSellerItemNumber();
        this.optionName = dto.getOptionName();
        this.sellAmount = dto.getSellAmount();
        this.normalCost = dto.getNormalCost();
        this.discountCost = dto.getDiscountCost();
        this.sellCost = dto.getSellCost();
        this.inventoryAmount = dto.getInventoryAmount();
    }
}
