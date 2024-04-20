package com.todayeat.backend.store.entity;

import com.todayeat.backend._common.entity.BaseTime;
import com.todayeat.backend.location.entity.Address;
import com.todayeat.backend.seller.entity.Seller;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE store SET deleted_at = CONVERT_TZ(NOW(), '+00:00', '+09:00') WHERE store_id = ?")
public class Store extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @NotEmpty
    @Column(nullable = false, length = 100)
    private String registeredName;

    @NotEmpty
    @Column(nullable = false, length = 10)
    @Pattern(regexp = "[0-9]+", message = "숫자만 입력해주세요.")
    private String registeredNo;

    @NotEmpty
    @Column(nullable = false, length = 10)
    private String bossName;

    @Embedded
    private Address address;

    @NotEmpty
    @Column(nullable = false, length = 20)
    @Pattern(regexp = "[0-9]+", message = "숫자만 입력해주세요.")
    private String tel;

    @NotEmpty
    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = true)
    private String image;

    @Column(nullable = true)
    private String operatingTime;

    @Column(nullable = true)
    private String holiday;

    @Column(nullable = true)
    private String originCountry;

    @Column(nullable = true)
    private String introduction;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isOpened;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int reviewCnt;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int favoriteCnt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;
}
