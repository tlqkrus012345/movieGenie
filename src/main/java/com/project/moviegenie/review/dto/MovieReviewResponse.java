package com.project.moviegenie.review.dto;

import com.project.moviegenie.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieReviewResponse {

    private String title;
    private String context;
    private String genre;
    private String writer;

    public static MovieReviewResponse toDto(Review review) {
        return new MovieReviewResponse(review.getTitle(), review.getContext(), review.getGenre().name(), review.getWriter().getNickName());
    }
}
