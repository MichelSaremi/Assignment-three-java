package com.example.webapispringhibernate.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

//---defining our movie as entity
@Entity
@Table(name="Franchise")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Franchise {

    //---auto_incrementing id tag
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer id;

    //---table columns
    @Column(name = "Name")
    public String Name;

    @Column(name ="Description")
    public String Description;

    //---JSON getter for movies
    @JsonGetter("movies")
    public List<String> get_movies_list(){
        return movies.stream()
                .map(movie -> {
                    return "/movie/" +movie.id + " "+movie.getTitle();
                }).collect(Collectors.toList());
    }

    //---franchise association with movies
    @OneToMany(mappedBy = "franchise", fetch = FetchType.LAZY)
    List<Movie> movies;

    //---Franchise Setters
    public void setName(String name) {
        Name = name;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    //---Franchise Getters
    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}

