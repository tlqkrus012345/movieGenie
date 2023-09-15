package com.project.moviegenie.review.service;

import com.project.moviegenie.exception.ErrorCode;
import com.project.moviegenie.exception.MovieGenieAppException;
import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.review.domain.Review;
import com.project.moviegenie.review.domain.ReviewRepository;
import com.project.moviegenie.searchmovie.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovieReviewService implements ReviewService{

    private final ReviewRepository reviewRepository;
    @Override
    @Transactional
    public Review createReview(Review review) {
        return reviewRepository.save(review);
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
}
