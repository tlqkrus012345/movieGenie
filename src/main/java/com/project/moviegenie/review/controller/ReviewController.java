package com.project.moviegenie.review.controller;

import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.service.LoginService;
import com.project.moviegenie.response.ApiResponse;
import com.project.moviegenie.review.domain.Review;
import com.project.moviegenie.review.dto.MovieReviewRequest;
import com.project.moviegenie.review.dto.MovieReviewResponse;
import com.project.moviegenie.review.service.ReviewService;
import com.project.moviegenie.searchmovie.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final LoginService loginService;

    @PostMapping
    public ApiResponse<MovieReviewResponse> createReview(@RequestBody MovieReviewRequest request) {
        Member loginMember = loginService.getLoginMember();
        Genre genre = reviewService.getGenreFromString(request.getGenre());
        Review newReview = MovieReviewRequest.toEntity(request, loginMember, genre);

        return ApiResponse.success(MovieReviewResponse.toDto(reviewService.createReview(newReview)));
    }
}