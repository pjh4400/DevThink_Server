package com.devthink.devthink_server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer isbn;

    @Builder.Default
    private Integer reviewCnt = 0;

    @Builder.Default
    private BigDecimal scoreAvg = new BigDecimal("0");

    @Builder.Default
    @OneToMany(mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();




}
