package com.example.demo.controller;

import com.example.demo.model.WatchedDTO;
import com.example.demo.model.WatchedMovieRecord;
import com.example.demo.service.WatchedMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchedHistory")
public class WatchedMovieController{
    @Autowired
    public WatchedMovieService watchedMovieService;

//    @PostMapping("/addToWatched")
//    public ResponseEntity<String> watchMovie(
//            @RequestBody Long userId,
//            @RequestBody Long movieId){
//
//        String result = watchedMovieService.toggleWatchStatus(userId, movieId);
//
//        if (result.contains("Error")){
//            return ResponseEntity.badRequest().body(result);
//        }
//        return ResponseEntity.ok(result);
//    }

    @PostMapping("/addToWatched")
    public ResponseEntity<String> watchMovie(
            @RequestBody WatchedDTO watched){

        String result = watchedMovieService.addToWatchedHistory(watched.getUsername(), watched.getMovieId());

        if (result.contains("Error")){
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deleteFromWatched")
    public ResponseEntity<String> removeFromWatched(@RequestBody WatchedDTO watched){
        String result = watchedMovieService.deleteFromWatchedHistory(watched.getUsername(), watched.getMovieId());
        if(result.contains("Error")){
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);

    }

//    @PostMapping("/user/getWatched")
//    public ResponseEntity<Object> getUserHistory(@RequestBody Long userId, @RequestBody Long movieId){
//        List<WatchedMovieRecord> watchedHistory = watchedMovieService.getUserRecords(userId);
//        if(watchedHistory.isEmpty()){
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(watchedHistory);
//    }

    @GetMapping("/user/getWatched")
    public ResponseEntity<Object> getUserHistory(@RequestBody WatchedDTO watched){
        List<WatchedMovieRecord> watchedHistory = watchedMovieService.getUserRecords(watched.getUsername());
        if(watchedHistory.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(watchedHistory);
    }

//    public ResponseEntity<List<WatchedMovieRecord>> getUserHistory(@PathVariable Long userId){
//        List<WatchedMovieRecord> watchedHistory = watchedMovieService.getUserRecords(userId);
//        if(watchedHistory.isEmpty()){
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(watchedHistory);
//    }
}