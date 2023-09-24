package com.project.moviegenie.review;

import com.project.moviegenie.exception.MovieGenieAppException;
import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.domain.MemberRole;
import com.project.moviegenie.review.domain.Review;
import com.project.moviegenie.review.domain.ReviewRepository;
import com.project.moviegenie.review.dto.MovieReviewRequest;
import com.project.moviegenie.review.dto.MovieReviewResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("정상적으로 요청을 하면 Pagination이 적용하여 10개씩 보여주며 총 페이지는 2이다.")
    void findAllReview() {
        Member member = Member.builder().email("test@email.com").password("a12345678").nickName("test").memberRole(MemberRole.MEMBER).build();
        List<Review> reviewList = new ArrayList<>();
        for (int i=0; i<11; i++) {
            Review review = Review.builder().title("title" + i).context("context").writer(member).genre(Genre.ACTION).build();
            reviewList.add(review);
        }

        Pageable pageable = PageRequest.of(0, 10);

        when(reviewRepository.findAll(pageable)).thenReturn(new PageImpl<>(reviewList, pageable, reviewList.size()));

        Page<MovieReviewResponse> resultPage = movieReviewService.findAllReview(pageable);

        assertEquals(11, resultPage.getTotalElements());
        assertEquals(2, resultPage.getTotalPages());
        assertEquals(10, resultPage.getSize());
        assertFalse(resultPage.hasPrevious());
        assertFalse(resultPage.getContent().isEmpty());
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
