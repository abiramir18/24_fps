package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="user_watch_history")
public class WatchedMovieRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="username_interal", referencedColumnName = "username")
    private UserRecord users;

    //    Many watchlist entries can point to the same movie
    @ManyToOne
    @JoinColumn(name = "movie_internal_id", referencedColumnName = "internalId")
    private MovieRecord movie;

    private LocalDateTime watchedDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserRecord getUsers() {
        return users;
    }

    public void setUsers(UserRecord users) {
        this.users = users;
    }

    public MovieRecord getMovie() {
        return movie;
    }

    public void setMovie(MovieRecord movie) {
        this.movie = movie;
    }

    public LocalDateTime getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(LocalDateTime watchedDate) {
        this.watchedDate = watchedDate;
    }
}