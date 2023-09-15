package com.project.moviegenie.review.service;

import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.review.domain.Review;
import com.project.moviegenie.searchmovie.domain.Genre;

public interface ReviewService {

    Review createReview(Review review);
    Genre getGenreFromString(String genre);
}
