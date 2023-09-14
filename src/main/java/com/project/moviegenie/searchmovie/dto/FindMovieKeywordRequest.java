package com.project.moviegenie.searchmovie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindMovieKeywordRequest {
    private String genre;
    private String actor;
    private String filmDirector;
    private String background;
    private String keyword;
}
