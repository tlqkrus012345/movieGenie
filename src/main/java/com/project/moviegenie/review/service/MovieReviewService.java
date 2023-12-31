package com.project.moviegenie.review.service;

import com.project.moviegenie.exception.ErrorCode;
import com.project.moviegenie.exception.MovieGenieAppException;
import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.service.LoginService;
import com.project.moviegenie.review.domain.Review;
import com.project.moviegenie.review.domain.ReviewRepository;
import com.project.moviegenie.review.dto.MovieReviewRequest;
import com.project.moviegenie.review.dto.MovieReviewResponse;
import com.project.moviegenie.searchmovie.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

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
    @Transactional(readOnly = true)
    public Page<MovieReviewResponse> findAllReview(Pageable pageable) {
        return reviewRepository.findAll(pageable)
                .map(review -> {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    String formattedDate = review.getCreatedDate().format(dateTimeFormatter);

                    MovieReviewResponse response = new MovieReviewResponse();
                    response.setTitle(review.getTitle());
                    response.setContext(review.getContext());
                    response.setWriter(review.getWriter().getNickName());
                    response.setGenre(review.getGenre().name());
                    response.setRegisterDate(formattedDate);
                    return response;
                });
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

    @Override
    public boolean validateMemberAndReview(Long reviewId) {
        Long writerId = findReviewById(reviewId).getWriter().getId();
        Long loginMember = loginService.getLoginMember().getId();

        if (writerId == loginMember) {
            return true;
        }
        return false;
    }

    public boolean isMatchedWriter(String writerEmail) {
        Member loginMember = loginService.getLoginMember();

        if (writerEmail.equals(loginMember.getEmail())) {
            return true;
        }
        return false;
    }

}
