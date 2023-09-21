package com.project.moviegenie.review.dto;

import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.review.domain.Review;
import com.project.moviegenie.searchmovie.domain.Genre;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieReviewRequest {

    private String title;
    private String context;
    private String genre;

    public static Review toEntity(MovieReviewRequest request, Member writer, Genre genre) {
        return Review.builder()
                .title(request.getTitle())
                .genre(genre)
                .context(request.getContext())
                .writer(writer)
                .build();
    }
}
