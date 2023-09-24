package com.project.moviegenie.review.controller;

import com.project.moviegenie.exception.ErrorCode;
import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.service.LoginService;
import com.project.moviegenie.response.ApiResponse;
import com.project.moviegenie.review.domain.Review;
import com.project.moviegenie.review.dto.MovieReviewRequest;
import com.project.moviegenie.review.dto.MovieReviewResponse;
import com.project.moviegenie.review.service.ReviewService;
import com.project.moviegenie.searchmovie.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{reviewId}")
    public ApiResponse<MovieReviewResponse> updateReview(@PathVariable Long reviewId, @RequestBody MovieReviewRequest request) {
        Review updateReview = reviewService.updateReview(reviewId, request);

        return ApiResponse.success(MovieReviewResponse.toDto(updateReview));
    }

    @GetMapping
    public ApiResponse<Page<MovieReviewResponse>> findAllReview(@PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC)
                                                                    Pageable pageable) {
        return ApiResponse.success(reviewService.findAllReview(pageable));
    }

    @GetMapping("/{reviewId}")
    public ApiResponse<MovieReviewResponse> findReviewById(@PathVariable Long reviewId) {
        return ApiResponse.success(MovieReviewResponse.toDto(reviewService.findReviewById(reviewId)));
    }

    @DeleteMapping("/{reviewId}")
    public ApiResponse<?> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);

        return ApiResponse.success();
    }

    @GetMapping("/validate/{reviewId}")
    public ApiResponse<?> validateMemberAndReview(@PathVariable Long reviewId) {
        if (reviewService.validateMemberAndReview(reviewId)) {
            return ApiResponse.success();
        }
        return ApiResponse.error(String.valueOf(ErrorCode.VALIDATION_ERROR), "수정, 삭제가 불가능합니다.");
    }
}
