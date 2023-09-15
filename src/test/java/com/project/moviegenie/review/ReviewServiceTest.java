package com.project.moviegenie.review;

import com.project.moviegenie.exception.MovieGenieAppException;
import com.project.moviegenie.review.domain.Review;
import com.project.moviegenie.review.domain.ReviewRepository;
import com.project.moviegenie.review.service.MovieReviewService;
import com.project.moviegenie.searchmovie.domain.Genre;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    MovieReviewService movieReviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("정상적으로 평론을 등록하면 평론 레포가 호출된다.")
    void createReview() {
        Review requestReview = Review.builder().title("title").context("context").genre(Genre.ACTION).build();

        when(reviewRepository.save(ArgumentMatchers.any(Review.class))).thenReturn(requestReview);
        Review savedReview = movieReviewService.createReview(requestReview);

        assertThat(requestReview.getWriter()).isEqualTo(savedReview.getWriter());

    }
    @Test
    @DisplayName("String으로 들어온 장르를 Enum 타입으로 변환한다")
    void getGenreFromString() {
        assertThat(Genre.ACTION).isEqualTo(movieReviewService.getGenreFromString("action"));
        assertThat(Genre.SF).isEqualTo(movieReviewService.getGenreFromString("SF"));
    }
    @Test
    @DisplayName("장르에 없는 값이 들어오면 예외가 발생한다")
    void getGenreFromStringFail() {
        assertThatThrownBy(() -> movieReviewService.getGenreFromString("InvalidGenre"))
                .isInstanceOf(MovieGenieAppException.class);
    }
}
