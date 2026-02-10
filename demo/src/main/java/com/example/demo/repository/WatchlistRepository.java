package com.example.demo.repository;

import com.example.demo.model.WatchlistRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface WatchlistRepository extends JpaRepository<WatchlistRecord, Long>{
//    Helps to check if a movie is already in the watchlist
    boolean existsByMovieInternalId(Long internalId);
//    Helps with deletion
    void deleteByMovieInternalId(Long internalId);
//   Helps to check if username and movie is already in the list
    boolean existsByUsersUsernameAndMovieInternalId(String username, Long internalId);
    void deleteByUsersUsernameAndMovieInternalId(String username, Long internalId);
    boolean existsByUsersUsername(String username);
    List<WatchlistRecord> findByUsersUsername(String username);
}