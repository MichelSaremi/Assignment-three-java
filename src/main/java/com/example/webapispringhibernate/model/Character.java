package com.example.webapispringhibernate.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//---defining our movie as entity
@Entity
@Table(name="Character")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Character {

    //---auto_incrementing id tag
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer id;

    //---table columns
    @Column(name="Name")
    public String Name;

    @Column(name="Alias")
    public String Alias;

    @Column(name="Gender")
    public String Gender;

    @Column(name="Picture")
    public String Picture;

    //---JSON getter for movies
    @JsonGetter("movies")
    public List<String> get_movies_list(){
        return movies.stream()
                .map(movie -> {
                    return "/movie/" +movie.id + " "+movie.getTitle();
                }).collect(Collectors.toList());
    }

    //---character association with movies
    @ManyToMany(mappedBy = "characters")
    public List<Movie> movies = new ArrayList<>();

    //---Character setters
    public void setCharName(String name) {
        this.Name = name;
    }
    public void setCharAlias(String alias) {
        this.Alias = alias;
    }
    public void setCharGender(String gender) {
        this.Gender = gender;
    }
    public void setCharPic(String pic) {
        this.Picture = pic;
    }
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    //---Character getters
    public String getCharName() {
        return this.Name;
    }
    public String getCharAlias() {
        return this.Alias;
    }
    public String getCharGender() {
        return this.Gender;
    }
    public String getCharPic() {
        return this.Picture;
    }
    public Integer getId() {return id; }
    public List<Movie> getMovies() {
        return movies;
    }


}
