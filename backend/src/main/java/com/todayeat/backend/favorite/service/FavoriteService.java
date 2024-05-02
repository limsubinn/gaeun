package com.todayeat.backend.favorite.service;

import com.todayeat.backend._common.response.error.exception.BusinessException;
import com.todayeat.backend._common.util.SecurityUtil;
import com.todayeat.backend.consumer.entity.Consumer;
import com.todayeat.backend.favorite.dto.request.CreateFavoriteRequest;
import com.todayeat.backend.favorite.mapper.FavoriteMapper;
import com.todayeat.backend.favorite.repository.FavoriteRepository;
import com.todayeat.backend.store.entity.Store;
import com.todayeat.backend.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.todayeat.backend._common.response.error.ErrorType.FAVORITE_CONFLICT;
import static com.todayeat.backend._common.response.error.ErrorType.STORE_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final StoreRepository storeRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    public void create(CreateFavoriteRequest request) {

        // 소비자
        Consumer consumer = securityUtil.getConsumer();

        // 가게
        Store store = storeRepository.findByIdAndDeletedAtIsNull(request.getStoreId())
                .orElseThrow(() -> new BusinessException(STORE_NOT_FOUND));

        // 찜이 이미 존재
        if (favoriteRepository.existsByConsumerAndStoreAndDeletedAtIsNull(consumer, store)) {
            throw new BusinessException(FAVORITE_CONFLICT);
        }

        // 저장
        favoriteRepository.save(FavoriteMapper.INSTANCE.toFavorite(consumer, store));

        // 가게 찜 수 증가
        store.updateFavoriteCnt(1);
    }
}
