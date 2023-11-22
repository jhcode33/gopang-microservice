package com.gopang.itemserver.controller;

import com.gopang.itemserver.dto.request.item.SellerInfo;
import com.gopang.itemserver.dto.response.ResItemOrder;
import com.gopang.itemserver.dto.request.item.save.ItemSaveRequest;
import com.gopang.itemserver.dto.request.item.update.ItemUpdateRequest;
import com.gopang.itemserver.dto.response.ResItem;
import com.gopang.itemserver.dto.response.ResItemSaveDto;
import com.gopang.itemserver.dto.response.ResItemUpdateDto;
import com.gopang.itemserver.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    //private final RestTemplate restTemplate;

    @GetMapping("/main/items")
    public ResponseEntity<List<ResItem>> mainItemList() {
        List<ResItem> itemList = itemService.findMainItemList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(itemList);
    }

    @PostMapping("/seller/item")
    public ResponseEntity<ResItemSaveDto> save(
            @RequestBody ItemSaveRequest itemRequest) {
        // 판매자 id, 판매자 배송지 id, 판매자 환불지 id
        // 원래는 RestTemplate or Kafka으로 요청해야 함
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(1L);
        sellerInfo.setSellerDeliveryId(2L);
        sellerInfo.setSellerREId(3L);

        ResItemSaveDto itemSave = itemService.save(sellerInfo, itemRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(itemSave);
    }

    //등록한 판매자일 경우에만 수정할 수 있도록 해야하지 않겠니?
    @PutMapping("/seller/item/{itemId}")
    public ResponseEntity<ResItemUpdateDto> update(
            @PathVariable Long itemId,
            @RequestBody ItemUpdateRequest itemRequest) {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(1L);
        sellerInfo.setSellerDeliveryId(2L);
        sellerInfo.setSellerREId(3L);

        itemRequest.getItemUpdateDto().setItemId(itemId);
        ResItemUpdateDto updateDto = itemService.update(sellerInfo, itemRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updateDto);

    }

    /**
     * 주문에서 상품 정보 조회
     * @author 김민규
     * @param itemIds
     * @return
     */
    @PostMapping("/order/item")
    public ResponseEntity<List<ResItemOrder>> getItemOrder(
            @RequestBody List<Long> itemIds) {

        List<ResItemOrder> resItemOrders = itemService.getItemOrders(itemIds);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resItemOrders);
    }

}
