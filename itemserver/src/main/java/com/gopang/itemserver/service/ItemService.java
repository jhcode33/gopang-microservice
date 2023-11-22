package com.gopang.itemserver.service;

import com.gopang.itemserver.dto.request.item.SellerInfo;
import com.gopang.itemserver.dto.response.ResItemOrder;
import com.gopang.itemserver.dto.request.item.save.*;
import com.gopang.itemserver.dto.request.item.update.*;
import com.gopang.itemserver.dto.response.ResItem;
import com.gopang.itemserver.dto.response.ResItemDetailDto;
import com.gopang.itemserver.dto.response.ResItemSaveDto;
import com.gopang.itemserver.dto.response.ResItemUpdateDto;
import com.gopang.itemserver.entity.*;
import com.gopang.itemserver.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemDetailRepository itemDetailRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final BrandManufacturerRepository brandManufacturerRepository;
    private final CategoryRepository categoryRepository;

    public List<ResItem> findMainItemList() {
        List<Item> itemList = itemRepository.findAll();
        List<ResItem> resItemList = new ArrayList<>();

        for (Item item : itemList) {
            for (ItemOption option : item.getOptions()) {
                ResItem resItem = ResItem.builder()
                        .itemId(item.getItemId())
                        .itemTitleName(item.getTitleName())
                        .itemOptionName(option.getOptionName())
                        .sellAmount(option.getSellAmount())
                        .sellCost(option.getSellCost())
                        .build();
                resItemList.add(resItem);
            }
        }
        return resItemList;
    }

    public ResItemDetailDto findItemDetail(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalArgumentException("Iten not found")
        );

        return ResItemDetailDto.fromEntity(item);
    }

    // 판매자 id, 판매자 배송 정보 id, 판매자 환불 정보 id, 카테고리 id
    // 판매자 배송, 환불 정보x
    public ResItemSaveDto save(SellerInfo sellerInfo, ItemSaveRequest itemSaveRequest) {
        Long categoryId = itemSaveRequest.getCategoryId();
        ItemSaveDto itemDto = itemSaveRequest.getItemSaveDto();
        ItemDetailSaveDto itemDetailDto = itemSaveRequest.getItemDetailSaveDto();
        List<ItemOptionSaveDto> itemOptionDtoList = itemSaveRequest.getItemOptionSaveDtoList();
        BrandManufacturerSaveDto brandManuDto = itemSaveRequest.getBrandManufacturerSaveDto();

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("Not Found")
        );

        itemDto.setSellerInfo(sellerInfo.getSellerId(), sellerInfo.getSellerDeliveryId(), sellerInfo.getSellerREId());
        Item item = ItemSaveDto.ofEntity(itemDto);
        item.setCategory(category);
        itemRepository.save(item);

        ItemDetail itemDetail = ItemDetailSaveDto.ofEntity(itemDetailDto);
        itemDetail.setItem(item);
        itemDetailRepository.save(itemDetail);

        List<Long> itemOptionIds = new ArrayList<>();
        for (ItemOptionSaveDto itemOptionSaveDto : itemOptionDtoList) {
            ItemOption itemOption = ItemOptionSaveDto.ofEntity(itemOptionSaveDto);
            itemOption.setItem(item);
            itemOptionRepository.save(itemOption);
            itemOptionIds.add(itemOption.getItemOptionId());
        }

        BrandManufacturer brandManufacturer = BrandManufacturerSaveDto.ofEntity(brandManuDto);
        brandManufacturer.setItem(item);
        brandManufacturerRepository.save(brandManufacturer);

        return ResItemSaveDto.fromEntity(item, itemDetail.getItemDetailId(), itemOptionIds, brandManufacturer.getBrandManufacturerId());
    }

    public ResItemUpdateDto update(SellerInfo sellerInfo, ItemUpdateRequest itemRequest) {
        Long categoryId = itemRequest.getCategoryId();
        ItemUpdateDto itemUpdateDto = itemRequest.getItemUpdateDto();
        ItemDetailUpdateDto itemDetailUpdateDto = itemRequest.getItemDetailUpdateDto();
        List<ItemOptionUpdateDto> itemOptionUpdateDtoList = itemRequest.getItemOptionUpdateDtoList();
        BrandManufacturerUpdateDto brandManuUpdateDto = itemRequest.getBrandManufacturerUpdateDto();

        itemUpdateDto.setSellerInfo(sellerInfo.getSellerId(), sellerInfo.getSellerDeliveryId(), sellerInfo.getSellerREId());

        Category newCategory = categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("Not Found")
        );

        Item item = itemRepository.findById(itemRequest.getItemUpdateDto().getItemId()).orElseThrow(
                () -> new IllegalArgumentException("Item not found")
        );


        List<ItemOption> itemOptionList = item.getOptions();
        for (ItemOptionUpdateDto itemOptionUpdateDto : itemOptionUpdateDtoList) {
            updateOptionList(itemOptionList, itemOptionUpdateDto);
        }

        List<Long> itemOptionIds = itemOptionList.stream()
                .map(ItemOption::getItemOptionId)
                .toList();

        ItemDetail updatedItemDetail = updateItemDetail(itemDetailUpdateDto);
        BrandManufacturer updatedBrandManu = updateBrandManu(brandManuUpdateDto);
        item.update(newCategory, itemUpdateDto, updatedItemDetail, itemOptionList, updatedBrandManu);

        return ResItemUpdateDto.fromEntity(item,
                updatedItemDetail.getItemDetailId(),
                itemOptionIds,
                updatedBrandManu.getBrandManufacturerId());

    }

    /**
     * 주문에서 상품 정보 조회
     * @author 김민규
     * @param itemIds
     * @return
     */
    public List<ResItemOrder> getItemOrders(List<Long> itemIds) {
        List<Item> itemList = itemRepository.findAllById(itemIds);

        List<ResItemOrder> resItemOrders = new ArrayList<>();
        for (Item item : itemList) {
            Long itemPrice = item.getOptions().get(1).getSellCost();
            ResItemOrder resItemOrder = ResItemOrder.builder()
                    .itemId(item.getItemId())
                    .itemName(item.getTitleName())
                    .itemPrice(itemPrice)
                    .build();

            resItemOrders.add(resItemOrder);
        }

        return resItemOrders;
    }

    //== private method ==//
    private ItemDetail updateItemDetail(ItemDetailUpdateDto itemDetailUpdateDto) {
        ItemDetail itemDetail = itemDetailRepository.findById(itemDetailUpdateDto.getItemDetailId()).orElseThrow(
                () -> new IllegalArgumentException(("ItemDetail not found"))
        );
        itemDetail.update(itemDetailUpdateDto);
        return itemDetail;
    }

    private void updateOptionList(List<ItemOption> optionList, ItemOptionUpdateDto dto) {
        for (ItemOption option : optionList) {
            Long updateOptionId = dto.getItemOptionId();
            if (option.getItemOptionId() == updateOptionId) {
                option.update(dto);
            }
        }
    }

    private BrandManufacturer updateBrandManu(BrandManufacturerUpdateDto dto) {
        BrandManufacturer brandManu = brandManufacturerRepository.findById(dto.getBrandManufacturerId()).orElseThrow(
                () -> new IllegalArgumentException(("ItemDetail not found"))
        );
        brandManu.update(dto);
        return brandManu;
    }

}
