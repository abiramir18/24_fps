package com.example.demo.repository;

import com.example.demo.model.WatchedMovieRecord;
import com.example.demo.model.WatchlistRecord;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchedMovieRepository extends JpaRepository<WatchedMovieRecord, Long>{
//    List<WatchedMovieRecord> findByUserId(Long userId);
//    boolean existsByUserIdAndMovieId(Long userId, Long movieId);
    @Modifying
        // This tells the spring that this is an UPDATE / DELETE not a select statement
    @Transactional
        // Give the permission to delete the data
        //  Reason why we are not add @Transactional in save - When we try to add some data, and it fails, it doesn't lead to any data loss, you just don't have a few row.
//    void deleteByUserIdAndMovieId(Long userId, Long movieId);
    void deleteByUsersUsernameAndMovieInternalId(String username, Long movieId);
    boolean existsByUsersUsernameAndMovieInternalId(String username, Long movieId);
    List<WatchedMovieRecord> findByUsersUsername(String username);
}