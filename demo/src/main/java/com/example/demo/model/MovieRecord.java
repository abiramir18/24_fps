package com.example.demo.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByNames;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="movies")

public class MovieRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long internalId;

    @CsvBindByName(column = "id")
    private Long id;

    @CsvBindByName(column = "title")
    private String title;

    @CsvBindByName(column = "genres")
    private String genres;

    @CsvBindByName(column = "original_language")
    private String originallanguage;

    @CsvBindByName(column = "overview")
    @Column(length=10000)
    private String overview;

    @CsvBindByName(column = "popularity")
    private double popularity;

    @CsvBindByName(column = "production_companies")
    @Column(columnDefinition = "TEXT")
    private String productioncompanies;

    @CsvBindByName(column = "release_date")
    private String releasedate;

    @CsvBindByName(column = "budget")
    private Long budget;

    @CsvBindByName(column = "revenue")
    private Long revenue;

    @CsvBindByName(column = "runtime")
    private Integer runtime;

    @CsvBindByName(column = "status")
    private String status;

    @CsvBindByName(column = "tagline")
    private String tagline;

    @CsvBindByName(column = "vote_average")
    private double voteaverage;

    @CsvBindByName(column = "vote_count")
    private Integer votecount;

    @CsvBindByName(column = "credits")
    @Lob
    @Column(columnDefinition = "TEXT")
    private String credits;

    @CsvBindByName(column = "keywords")
    @Column(columnDefinition = "TEXT")

    private String keywords;

    @CsvBindByName(column = "poster_path")

    private String posterpath;

    @CsvBindByName(column = "backdrop_path")
    private String backdroppath;

    private Boolean isPopular = false;

//    @CsvBindByName(column = "recommendations")
//    private String recommendations;


    public Long getInternalId() {
        return internalId;
    }

    public void setInternalId(Long internalId) {
        this.internalId = internalId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getOriginallanguage() {
        return originallanguage;
    }

    public void setOriginallanguage(String originallanguage) {
        this.originallanguage = originallanguage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getProductioncompanies() {
        return productioncompanies;
    }

    public void setProductioncompanies(String productioncompanies) {
        this.productioncompanies = productioncompanies;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public double getVoteaverage() {
        return voteaverage;
    }

    public void setVoteaverage(double voteaverage) {
        this.voteaverage = voteaverage;
    }

    public Integer getVotecount() {
        return votecount;
    }

    public void setVotecount(Integer votecount) {
        this.votecount = votecount;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }

    public String getBackdroppath() {
        return backdroppath;
    }

    public void setBackdroppath(String backdroppath) {
        this.backdroppath = backdroppath;
    }

    public Boolean getPopular() {
        return isPopular;
    }

    public void setPopular(Boolean popular) {
        isPopular = popular;
    }
}
