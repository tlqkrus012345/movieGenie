package com.project.moviegenie.review.service;

import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.review.domain.Review;
import com.project.moviegenie.review.dto.MovieReviewRequest;
import com.project.moviegenie.review.dto.MovieReviewResponse;
import com.project.moviegenie.searchmovie.domain.Genre;

import java.util.List;

public interface ReviewService {

    Review createReview(Review review);
    Review updateReview(Long reviewId, MovieReviewRequest request);
    Review findReviewById(Long id);
    List<MovieReviewResponse> findAllReview();
    void deleteReview(Long id);
    boolean validateMemberAndReview(Long reviewId);
    Genre getGenreFromString(String genre);
}
