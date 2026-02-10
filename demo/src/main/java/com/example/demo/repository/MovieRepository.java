package com.example.demo.repository;

import com.example.demo.model.MovieRecord;
import com.example.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.*;

@Repository
public interface MovieRepository extends JpaRepository<MovieRecord, Long>, JpaSpecificationExecutor<MovieRecord> {
//    Prevent from saving the same data (data saved using csv 1) again using csv 2,
//    csv 1 - The Northman, csv 2 - the northman, if you don't use this line, the computer thinks these two are different movies.
//    Optional<MovieRecord> findByTitleIgnoreCase(String title);
    List<MovieRecord> findByTitleIgnoreCase(String title);

//    Fetches only the 30 records from csv 2
    List<MovieRecord> findByIsPopularTrueOrderByGenresAsc();

//    Fetches the 30 records filtered by the specific genre string
    List<MovieRecord> findByIsPopularTrueAndGenresContainingIgnoreCase(String genre);
/*
     @Query("Select m from MovieRecord m where m.isPopular = True and Lower(m.genres) like lower(concat('%', :genre, '%'))")
     List<MovieRecord> fetchMoviesByGenre(@Param("genre") String genre);
 */

}

/*
 the method name must follow a specific naming convention - findByIsTopPopularTrueAndGenresContainingIgnoreCase

 findBy - select * from movies where ...
 IsTopPopularTrue - is_top_popular = true
 And - AND
 Genres - genres
 Containing - like %genre%
 IgnoreCase - LOWER(genre) = LOWER(?), here ? is the placeholder for the variable you pass into the method

 */

/*
    We can use custom names you have to write query for that
 */