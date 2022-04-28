package com.example.webapispringhibernate.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

//---defining our movie as entity
@Entity
@Table(name="Movie")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Movie {

    //---auto_incrementing id tag
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer id;

    //---table columns
    @Column(name = "Title")
    public String Title;

    @Column(name = "Release_Year")
    public int ReleaseYear;

    @Column(name = "Director")
    public String Director;

    @Column(name = "Picture")
    public String Picture;

    @Column(name = "Trailer")
    public String Trailer;

    //---JSON getter for characters
    @JsonGetter("characters")
    public List<String> characterGetter() {
        return characters.stream()
                .map(characterItem -> {
                    return "/character/" + characterItem.getId() + " " + characterItem.getCharName();
                }).collect(Collectors.toList());
    }

    //---movie association with character
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "characters_movies",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "character_id")}
    )
    public List<Character> characters;

    //---JSON getter for franchise
    @JsonGetter("franchise")
    public String getAFranchise()
    {
        if(franchise != null)
        {
            return "/franchise/" + franchise.id +" "+franchise.getName();
        }
        else
        {
            return null;
        }
    }

    //---movie association with franchise
    @ManyToOne()
    @JoinTable(name = "franchise_movies",
            joinColumns = {@JoinColumn(name = "movies_id")},
            inverseJoinColumns = {@JoinColumn(name = "franchise_id")}
    )
    private Franchise franchise;


    //---Movie Getters
    public String getTitle() {
        return Title;
    }

    public int getReleaseYear() {
        return ReleaseYear;
    }

    public String getDirector() {
        return Director;
    }

    public String getPicture() {
        return Picture;
    }

    public String getTrailer() {
        return Trailer;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public Franchise getFranchise() {
        return franchise;
    }

    public Integer getId() {
        return id;
    }

    //---Movie Setters
    public void setTitle(String title) {
        Title = title;
    }

    public void setReleaseYear(int releaseYear) {
        ReleaseYear = releaseYear;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public void setTrailer(String trailer) {
        Trailer = trailer;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public void delFranchise() {
        this.franchise = null;
    }
}
