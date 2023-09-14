package com.project.moviegenie.searchmovie.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> actors = new ArrayList<>();

    private String filmDirector;

    @Enumerated(EnumType.STRING)
    private Background background;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> keywords = new ArrayList<>();

    @Builder
    public Movie(Genre genre, String actor, String filmDirector, Background background, String keyword) {
        this.genre = genre;
        actors.add(actor);
        this.filmDirector = filmDirector;
        this.background = background;
        keywords.add(keyword);
    }

}
