package com.project.moviegenie.review.service;

import com.project.moviegenie.exception.ErrorCode;
import com.project.moviegenie.exception.MovieGenieAppException;
import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.service.LoginService;
import com.project.moviegenie.review.domain.Review;
import com.project.moviegenie.review.domain.ReviewRepository;
import com.project.moviegenie.review.dto.MovieReviewRequest;
import com.project.moviegenie.searchmovie.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovieReviewService implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final LoginService loginService;
    @Override
    @Transactional
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public Review updateReview(Long reviewId, MovieReviewRequest request) {
        Review review = findReviewById(reviewId);
        review.updateReview(request, getGenreFromString(request.getGenre()));
        return review;
    }

    @Override
    @Transactional(readOnly = true)
    public Review findReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new MovieGenieAppException(ErrorCode.NOT_FOUND_REVIEW));
    }

    @Override
    public void deleteReview(Long id) {
        String writerEmail = findReviewById(id).getWriter().getEmail();
        if (isMatchedWriter(writerEmail)) {
            reviewRepository.deleteById(id);
        }
    }

    @Override
    public Genre getGenreFromString(String stringGenre) {
        for (Genre genre : Genre.values()) {
            if (genre.name().equalsIgnoreCase(stringGenre)) {
                return genre;
            }
        }
        throw new MovieGenieAppException(ErrorCode.NOT_FOUND_GENRE);
    }

    public boolean isMatchedWriter(String writerEmail) {
        Member loginMember = loginService.getLoginMember();

        if (writerEmail.equals(loginMember.getEmail())) {
            return true;
        }
        return false;
    }

}
