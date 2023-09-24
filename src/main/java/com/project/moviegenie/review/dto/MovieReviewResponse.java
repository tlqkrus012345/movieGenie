package com.project.moviegenie.review.dto;

import com.project.moviegenie.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieReviewResponse {

    private String title;
    private String context;
    private String genre;
    private String writer;
    private String registerDate;

    public static MovieReviewResponse toDto(Review review) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDate = review.getCreatedDate().format(dateTimeFormatter);

        return new MovieReviewResponse(
                review.getTitle(),
                review.getContext(),
                review.getGenre().name(),
                review.getWriter().getNickName(),
                formattedDate);
    }
}
