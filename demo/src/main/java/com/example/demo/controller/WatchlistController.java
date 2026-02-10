package com.example.demo.controller;

import com.example.demo.model.MovieRecord;
import com.example.demo.model.WatchedDTO;
import com.example.demo.model.WatchlistRecord;
import com.example.demo.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/watchlist")

public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @PostMapping("/addToWatchlist")
    public ResponseEntity<String> addToWatchlist(@RequestBody WatchedDTO watchlist){
        String response = watchlistService.addToWatchlist(watchlist.getUsername(), watchlist.getMovieId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getFullWatchlist")
    public ResponseEntity<List<MovieRecord>> getFullWatchlist(){
        return ResponseEntity.ok(watchlistService.getFullWatchlist());
    }

    @DeleteMapping("/deleteFromWatchlist")
    public ResponseEntity<String> deleteFromWatchlist(@RequestBody WatchedDTO watchlist){
        String response = watchlistService.removeFromWatchlist(watchlist.getUsername(), watchlist.getMovieId());
        if(response.contains("Error")) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getWatchlist")
    public ResponseEntity<List<WatchlistRecord>> getWatchlist(@RequestBody WatchedDTO watchlist){
        return ResponseEntity.ok(watchlistService.getWatchlist(watchlist.getUsername()));
    }
    }
