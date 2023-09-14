package com.project.moviegenie.movie;

import com.project.moviegenie.searchmovie.domain.Background;
import com.project.moviegenie.searchmovie.domain.Genre;
import com.project.moviegenie.searchmovie.domain.Movie;
import com.project.moviegenie.searchmovie.domain.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void save() {
        Movie movie = Movie.builder()
                .genre(Genre.ACTION)
                .actor("actor")
                .filmDirector("director")
                .background(Background.MODERN)
                .keyword("keyword")
                .build();

        Movie savedMovie = movieRepository.save(movie);

        assertThat(movie.getActors()).isEqualTo(savedMovie.getActors());
        assertThat(movie).isEqualTo(savedMovie);
    }
}
