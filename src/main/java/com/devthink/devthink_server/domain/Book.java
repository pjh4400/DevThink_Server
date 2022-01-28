package com.devthink.devthink_server.domain;

import com.devthink.devthink_server.dto.BookResponseDto;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer isbn;

    private String name;

    private String writer;

    private String imgUrl;

    @Builder.Default
    private Integer reviewCnt = 0;

    @Builder.Default
    private BigDecimal scoreAvg = new BigDecimal("0");

    @Builder.Default
    @OneToMany(mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();

    public void setScoreAvg(BigDecimal scoreAvg){
        this.scoreAvg = scoreAvg;
    }

    public void addReview(Review review) {
        review.setBook(this);
        reviews.add(review);
        upReviewCnt();
    }

    public void upReviewCnt(){
        reviewCnt++;
    }

    public void downReviewCnt(){
        reviewCnt--;
    }

    public BookResponseDto toBookResponseDto(){
        return BookResponseDto.builder()
                .id(id)
                .isbn(isbn)
                .name(name)
                .writer(writer)
                .imgUrl(imgUrl)
                .reviewCnt(reviewCnt)
                .scoreAvg(scoreAvg)
                .build();
    }

}
