package com.example.demo.service;

import com.example.demo.model.MovieRecord;
import com.example.demo.model.UserRecord;
import com.example.demo.model.WatchlistRecord;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WatchlistRepository;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchlistService{

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private WatchlistRepository watchlistRepository;
    @Autowired
    private UserRepository userRepository;

    public String addToWatchlist(String username, Long internalId){
        if(watchlistRepository.existsByUsersUsernameAndMovieInternalId(username, internalId)){
            return "Movie is already in your watchlist.";
        }

        MovieRecord movie = movieRepository.findById(internalId)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: "+internalId));

        Optional<UserRecord> user = userRepository.findByUsername(username);

        WatchlistRecord watchlistEntry = new WatchlistRecord();
        watchlistEntry.setUsers(user.orElseThrow(()->new RuntimeException("Username not found")));
        watchlistEntry.setMovie(movie);
        watchlistRepository.save(watchlistEntry);

        return "Successfully added "+ movie.getTitle()+" to your watchlist.";
    }

    public List<MovieRecord> getFullWatchlist(){
        return watchlistRepository.findAll()
                .stream()
                .map(WatchlistRecord::getMovie)
                .collect(Collectors.toList());
    }

    @Transactional
    public String removeFromWatchlist(String username, Long movieId){
        if(watchlistRepository.existsByUsersUsernameAndMovieInternalId(username,movieId)) {
            watchlistRepository.deleteByUsersUsernameAndMovieInternalId(username, movieId);
            return "Successfully movie deleted from your watchlist";
        }
        if(!watchlistRepository.existsByMovieInternalId(movieId)) {
            return "Error : Movie not found";
        }
        if(!watchlistRepository.existsByUsersUsername(username)){
            return "Username not found!";
        }
        return "Unexpected error";

    }

    @GetMapping
    public List<WatchlistRecord> getWatchlist(String username){
         return watchlistRepository.findByUsersUsername(username);
    }
}