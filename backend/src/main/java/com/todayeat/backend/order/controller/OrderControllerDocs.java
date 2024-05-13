package com.todayeat.backend.order.controller;

import com.todayeat.backend._common.response.error.ErrorResponse;
import com.todayeat.backend._common.response.success.SuccessResponse;
import com.todayeat.backend.order.dto.request.CreateOrderRequest;
import com.todayeat.backend.order.dto.response.CreateOrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "orders", description = "주문")
@RequestMapping("/api/orders")
public interface OrderControllerDocs {

    @Operation(summary = "주문 등록",
            description = """
                          `ROLE_CONSUMER` \n
                          request body 넣어주세요. \n
                          orderInfoId를 반환합니다. 이 값은 결제 검증에 쓰입니다.
                          """)
    @ApiResponse(responseCode = "200",
            description = "성공",
            content = @Content(schema = @Schema(implementation = CreateOrderResponse.class)))
    @ApiResponse(responseCode = "400",
            description = "가게 영업 중이 아닐 경우 / 판매 중이 아닐 경우 / 실제 재고보다 장바구니 수량이 클 경우",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "403",
            description = "장바구니에 접근 권한이 없는 경우",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404",
            description = "가게가 없는 경우 / 장바구니가 없는 경우",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "409",
            description = "다른 가게의 음식이 장바구니에 존재",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping
    SuccessResponse<CreateOrderResponse> create(@RequestBody @Valid CreateOrderRequest request);
}
