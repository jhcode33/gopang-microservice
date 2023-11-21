package com.gopang.itemserver.dataload;

import com.gopang.itemserver.dto.request.item.SellerInfo;
import com.gopang.itemserver.dto.request.item.save.*;
import com.gopang.itemserver.dto.response.ResItemSaveDto;
import com.gopang.itemserver.entity.Category;
import com.gopang.itemserver.repository.CategoryRepository;
import com.gopang.itemserver.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.validator.constraints.CodePointLength;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Order(2)
@Component
@RequiredArgsConstructor
public class ItemDataLoad implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ItemService itemService;

    private ItemSaveDto itemSaveDto;
    private ItemDetailSaveDto itemDetailSaveDto;
    private List<ItemOptionSaveDto> itemOptionSaveDtoList = new ArrayList<>();
    private BrandManufacturerSaveDto brandManufacturerSaveDto;

    @Override
    public void run(String... args) throws Exception {
        itemDataSet();
        saveItemTest();
    }


    public void saveItemTest() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(1L);
        sellerInfo.setSellerDeliveryId(2L);
        sellerInfo.setSellerREId(3L);
        Long categoryId = 4L;

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalArgumentException("category not found")
        );

        ItemSaveRequest itemSaveRequest = new ItemSaveRequest();
        itemSaveRequest.setItemSaveDto(itemSaveDto);
        itemSaveRequest.setItemOptionSaveDtoList(itemOptionSaveDtoList);
        itemSaveRequest.setItemDetailSaveDto(itemDetailSaveDto);
        itemSaveRequest.setBrandManufacturerSaveDto(brandManufacturerSaveDto);
        itemSaveRequest.setCategoryId(categoryId);

        itemService.save(sellerInfo, itemSaveRequest);
    }

    private void itemDataSet() {
        itemSaveDto = ItemSaveDto.builder()
                .sellerId(1L)
                .sellerDeliveryId(2L)
                .sellerREId(3L)
                .titleName("Sample Item")
                .itemLabel("Sample Seller")
                .sellState("판매중")
                .sellStartDate(LocalDate.now())
                .sellEndDate(LocalDate.now().plusDays(30))
                .build();

        itemDetailSaveDto = ItemDetailSaveDto.builder()
                .maxOrder("Sample Max Order")
                .parallelType("병행수입")
                .minorType("미성년자구입")
                .taxType("과세")
                .maker("Sample Maker")
                .manufacturerItemName("Sample Manufacturer Item Name")
                .warning("Sample Warning")
                .qualityAssurance("Sample Quality Assurance")
                .asInfo("Sample AS Info")
                .build();

        ItemOptionSaveDto itemOptionSaveDto1 = ItemOptionSaveDto.builder()
                .itemModelName("Sample Item Model Name")
                .sellerItemNumber("Sample Seller Item Number")
                .optionName("Sample Option Name")
                .sellAmount(10L)
                .normalCost(10000L)
                .discountCost(8000L)
                .sellCost(9000L)
                .inventoryAmount(50L)
                .build();

        ItemOptionSaveDto itemOptionSaveDto2 = ItemOptionSaveDto.builder()
                .itemModelName("Sample Item Model Name")
                .sellerItemNumber("Sample Seller Item Number")
                .optionName("Sample Option Name")
                .sellAmount(10L)
                .normalCost(10000L)
                .discountCost(8000L)
                .sellCost(9000L)
                .inventoryAmount(50L)
                .build();

        itemOptionSaveDtoList.add(itemOptionSaveDto1);
        itemOptionSaveDtoList.add(itemOptionSaveDto2);

        brandManufacturerSaveDto = BrandManufacturerSaveDto.builder()
                .brandName("Sample Brand")
                .manufacturerName("Sample Manufacturer")
                .build();
    }
}
