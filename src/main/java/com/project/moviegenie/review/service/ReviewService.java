package com.project.moviegenie.review.service;

import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.review.domain.Review;
import com.project.moviegenie.review.dto.MovieReviewRequest;
import com.project.moviegenie.searchmovie.domain.Genre;

public interface ReviewService {

    Review createReview(Review review);
    Review updateReview(Long reviewId, MovieReviewRequest request);
    Review findReviewById(Long id);
    Genre getGenreFromString(String genre);
}
