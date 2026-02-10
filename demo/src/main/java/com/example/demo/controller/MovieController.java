package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.controller.*;
import com.example.demo.repository.*;
import com.example.demo.service.*;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")

public class MovieController{

    @Autowired
    private MovieService movieService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file){
        try{
            return ResponseEntity.ok(movieService.processAndSave(file));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }

    @GetMapping("/getAllrecords")
    public ResponseEntity<List<MovieRecord>> getRecords(){
               List<MovieRecord> movies = movieService.fetchAllMovies();
               return ResponseEntity.ok(movies);
    }

//    To get first 10 popular movies

    @GetMapping("/getPopularRecords")
    public ResponseEntity<Page<MovieRecord>> getRecords(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
//            Default to popularity for sorting
            @RequestParam(defaultValue = "popularity") String sortBy,
//            Default to highest first
            @RequestParam(defaultValue = "desc") String direction
    ){
        return ResponseEntity.ok(movieService.getPaginationRecords(page, size, sortBy, direction));
    }


//    To get all matching records
    @GetMapping("/search")
    public ResponseEntity<List<MovieRecord>> searchMovies(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int size,
            @RequestParam(defaultValue = "popularity") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ){
        return ResponseEntity.ok(movieService.searchByAnyAttribute(query));
    }

    @PostMapping("/uploadTop")
    public ResponseEntity<String> uploadTopMovies(@RequestParam("file") MultipartFile file){
        try{
            movieService.processAndSavePopularMovies(file);
            return ResponseEntity.ok("Top 30 movies updated successfully!");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }
    }

    @GetMapping("/top_popular_bygenre")
    public ResponseEntity<List<MovieRecord>> getTopPopular(){
        return ResponseEntity.ok(movieService.getTopPopularMoviesByGenre());
    }

//    To get the movie list by genre name
    @GetMapping("/genre/{genreName}")
    public ResponseEntity<List<MovieRecord>> getTopGenre(@PathVariable String genreName){
        return ResponseEntity.ok(movieService.getTopPopularMoviesBySpecificGenre(genreName));
    }

    @GetMapping("/view")
    public ResponseEntity<Map<String, Object>> getMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Map <String, Object> response = movieService.getMoviePaginated(page, size);
        return ResponseEntity.ok(response);
    }






}

