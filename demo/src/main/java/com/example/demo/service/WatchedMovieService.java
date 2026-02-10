package com.example.demo.service;

import com.example.demo.model.WatchedMovieRecord;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WatchedMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class WatchedMovieService{

    @Autowired
    private WatchedMovieRepository watchedMovieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

//    @Transactional
//    public String toggleWatchStatus(Long userId, Long movieId){
//        if(watchedMovieRepository.existsByUserIdAndMovieId(userId, movieId)){
//            watchedMovieRepository.deleteByUserIdAndMovieId(userId, movieId);
//            return "Movie deleted";
//        }
//        else {
//            if (!movieRepository.existsById(movieId)) {
//                return "Movie not found!";
//            }
//
////        if(!movieRepository.existsById(movieId)){
////            return "Error: Movie doesn't exist";
////        }
////
////        if(watchedMovieRepository.existsByUserIdAndMovieId(userId, movieId)){
////            return "Error: You already watched this movie";
////        }
//
//            WatchedMovieRecord history = new WatchedMovieRecord();
//            history.setUserId(userId);
//            history.setMovieId(movieId);
//            history.setWatchedDate(LocalDateTime.now());
//
//            watchedMovieRepository.save(history);
//
//            return "Movie added to your personal watch history";
//        }
//
//    }

    public String addToWatchedHistory(String username, Long movieId){
              WatchedMovieRecord history = new WatchedMovieRecord();
              history.setUsers(userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("Username not found")));
              history.setMovie(movieRepository.findById(movieId).orElseThrow(()->new RuntimeException("Movie not found")));
              history.setWatchedDate(LocalDateTime.now());

              watchedMovieRepository.save(history);

              return "Movie added to your personal watch history.";
    }

    public String deleteFromWatchedHistory(String username, Long movieId){
        if(watchedMovieRepository.existsByUsersUsernameAndMovieInternalId(username, movieId)){
            watchedMovieRepository.deleteByUsersUsernameAndMovieInternalId(username, movieId);
            return "Successfully removed movie from watched history";
        }
        return "Error: This movie was not found in the watched history";

    }

    public List<WatchedMovieRecord> getUserRecords(String username){
        return watchedMovieRepository.findByUsersUsername(username);
    }

}