package com.project.moviegenie.review.domain;

import com.project.moviegenie.config.BaseTimeEntity;
import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.review.dto.MovieReviewRequest;
import com.project.moviegenie.searchmovie.domain.Genre;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @Lob
    private String context;

    @Builder
    public Review(String title, Genre genre, Member writer, String context) {
        this.title = title;
        this.genre = genre;
        this.writer = writer;
        this.context = context;
    }

    public void updateReview(MovieReviewRequest request, Genre genre) {
        this.title = request.getTitle();
        this.context = request.getContext();
        this.genre = genre;
    }
}
