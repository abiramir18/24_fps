package com.example.demo.service;

import com.example.demo.model.MovieRecord;
import com.example.demo.repository.MovieRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.List;

@Service
public class MovieService{

    @Autowired
    public MovieRepository movieRepository;

    public String processAndSave(MultipartFile file) throws Exception{
//        Validation 1 - Checking the file type
        if(!file.getOriginalFilename().endsWith(".csv")){
            throw new IllegalArgumentException("Only CSV files are allowed");
        }

        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            CsvToBean<MovieRecord> csvToBean = new CsvToBeanBuilder<MovieRecord>(reader)
                    .withType(MovieRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<MovieRecord> movies = csvToBean.parse();

//            Validation 2 - Empty CSV
            if(movies.isEmpty()) throw new Exception("CSV file is empty");

            movieRepository.saveAll(movies);
            return "Successfully stored "+ movies.size() + " records.";
        }
    }

    public List<MovieRecord> fetchAllMovies(){

        return movieRepository.findAll();
    }

    public Page<MovieRecord> getPaginationRecords(
            int page, int size, String sortBy, String direction){
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return movieRepository.findAll(pageable);

    }

    public List<MovieRecord> searchByAnyAttribute(String query) {
        Specification<MovieRecord> spec = (root, queryBuilder, criteriaBuilder) -> {
            if (query == null || query.isEmpty()) return null;

            String pattern = '%' + query.toLowerCase() + '%';
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title").as(String.class)), pattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("tagline").as(String.class)), pattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("keywords").as(String.class)), pattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("genres").as(String.class)), pattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("originallanguage").as(String.class)), pattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("productioncompanies").as(String.class)), pattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("credits").as(String.class)), pattern));

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };

        return movieRepository.findAll(spec);
    }

    public void processAndSavePopularMovies(MultipartFile file) throws Exception{
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            CsvToBean<MovieRecord> csvToBean = new CsvToBeanBuilder<MovieRecord>(reader)
                    .withType(MovieRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<MovieRecord> topMovies = csvToBean.parse();

            for (MovieRecord newData : topMovies) {
                // This now returns a List of matches
                List<MovieRecord> matches = movieRepository.findByTitleIgnoreCase(newData.getTitle());

                for (MovieRecord existingMovie : matches) {
                    existingMovie.setGenres(newData.getGenres());
                    existingMovie.setPopularity(newData.getPopularity());
                    existingMovie.setOverview(newData.getOverview());
                    existingMovie.setPosterpath(newData.getPosterpath());
                    existingMovie.setPopular(true);

                    movieRepository.save(existingMovie);
                }
            }

//            for(MovieRecord newData : topMovies){
//                movieRepository.findByTitleIgnoreCase(newData.getTitle()).ifPresent(existingMovie -> {
//                    existingMovie.setGenres(newData.getGenres());
//                    existingMovie.setPopularity(newData.getPopularity());
//                    existingMovie.setOverview(newData.getOverview());
//                    existingMovie.setPosterpath(newData.getPosterpath());
//
//                    existingMovie.setPopular(true);
//
//                    movieRepository.save(existingMovie);
//                });
//            }
        }
    }
//    If a CSV row is missing a column or has a word where a number should be, the Java Bean mapping will catch the error before it hits your database.

    public List<MovieRecord> getTopPopularMoviesByGenre(){
        return movieRepository.findByIsPopularTrueOrderByGenresAsc();
    }

    public List<MovieRecord> getTopPopularMoviesBySpecificGenre(String genre){
        return movieRepository.findByIsPopularTrueAndGenresContainingIgnoreCase(genre);
    }

    public Map<String, Object> getMoviePaginated(int page, int size){
//        Create the pageable object - spring uses 0=based indexing for pages
        Pageable pageable = PageRequest.of(page, size);

//        Execute the paginated query
        Page<MovieRecord> moviePage = movieRepository.findAll(pageable);
        System.out.println(page);

//        Calculate how many records are left to be shown after this page
        long totalRecords = moviePage.getTotalElements();
        long shownSoFar = (long) (moviePage.getNumber()+1)* moviePage.getSize();
        long remaining = Math.max(0, totalRecords - shownSoFar);

//        Wrap everything in a object
        Map<String, Object> response = new HashMap<>();
        response.put("records", moviePage.getContent());
        response.put("totalPages", moviePage.getTotalPages());
        response.put("totalRecords", totalRecords);
        response.put("remaining", remaining);
        response.put("currentPage", moviePage.getNumber());

        return response;


    }


}