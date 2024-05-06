package com.todayeat.backend.menu.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetMenusResponse {

    private Long storeId;

    private List<GetMenuResponse> menus;

    private Integer size;

    @Builder
    private GetMenusResponse(Long storeId, List<GetMenuResponse> menus, Integer size) {
        this.storeId = storeId;
        this.menus = menus;
        this.size = size;
    }

    public static GetMenusResponse of(Long storeId, List<GetMenuResponse> menus, Integer size) {
        return builder()
                .storeId(storeId)
                .menus(menus)
                .size(size)
                .build();
    }
}
