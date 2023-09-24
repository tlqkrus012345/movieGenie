package com.project.moviegenie.review.service;

import com.project.moviegenie.review.domain.Review;
import com.project.moviegenie.review.dto.MovieReviewRequest;
import com.project.moviegenie.review.dto.MovieReviewResponse;
import com.project.moviegenie.searchmovie.domain.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    Review createReview(Review review);
    Review updateReview(Long reviewId, MovieReviewRequest request);
    Review findReviewById(Long id);
    Page<MovieReviewResponse> findAllReview(Pageable pageable);
    void deleteReview(Long id);
    boolean validateMemberAndReview(Long reviewId);
    Genre getGenreFromString(String genre);
}
