package com.example.demo.model;
import jakarta.persistence.*;

@Entity
@Table(name = "watchlist")
public class WatchlistRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_internal_name", referencedColumnName = "username")
    private UserRecord users;

//    Many watchlist entries can point to the same movie
    @ManyToOne
    @JoinColumn(name = "movie_internal_id", referencedColumnName = "internalId")
    private MovieRecord movie;

    public WatchlistRecord() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MovieRecord getMovie() {
        return movie;
    }

    public void setMovie(MovieRecord movie) {
        this.movie = movie;
    }

    public UserRecord getUsers() {
        return users;
    }

    public void setUsers(UserRecord users) {
        this.users = users;
    }
}