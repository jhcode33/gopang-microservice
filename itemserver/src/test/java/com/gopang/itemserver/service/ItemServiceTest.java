package com.gopang.itemserver.service;

import com.gopang.itemserver.dto.request.item.*;
import com.gopang.itemserver.dto.request.item.save.*;
import com.gopang.itemserver.dto.response.ResItemSaveDto;
import com.gopang.itemserver.entity.Category;
import com.gopang.itemserver.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Mock
    private CategoryRepository categoryRepository;

    ItemSaveDto itemSaveDto;
    ItemDetailSaveDto itemDetailSaveDto;
    List<ItemOptionSaveDto> itemOptionSaveDtoList = new ArrayList<>();
    BrandManufacturerSaveDto brandManufacturerSaveDto;

    @BeforeEach
    public void setUp() {
        // 스프링 컨테이너에서 받은 CategoryRepository가 아닌, Mock 객체를 직접 주입해서 사용
        ReflectionTestUtils.setField(itemService, "categoryRepository", categoryRepository);
        itemDataSet();
    }

    @Test
    public void saveItemTest() {
        // when
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(1L);
        sellerInfo.setSellerDeliveryId(2L);
        sellerInfo.setSellerREId(3L);
        Long categoryId = 4L;

        // Mocking: categoryRepository.findById()가 호출될 때 반환할 값을 지정
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(new Category()));
        ItemSaveRequest itemSaveRequest = new ItemSaveRequest();
        itemSaveRequest.setItemSaveDto(itemSaveDto);
        itemSaveRequest.setItemOptionSaveDtoList(itemOptionSaveDtoList);
        itemSaveRequest.setItemDetailSaveDto(itemDetailSaveDto);
        itemSaveRequest.setBrandManufacturerSaveDto(brandManufacturerSaveDto);
        itemSaveRequest.setCategoryId(categoryId);

        // given
        ResItemSaveDto resItemSaveDto = itemService.save(sellerInfo, itemSaveRequest);

        // then
        assertEquals(itemSaveDto.getTitleName(), resItemSaveDto.getTitleName()); // 예시로 아이템 타이틀 검증

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
