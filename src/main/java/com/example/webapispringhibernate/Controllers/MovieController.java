package com.example.webapispringhibernate.Controllers;

import com.example.webapispringhibernate.Repository.CharacterRepository;
import com.example.webapispringhibernate.Repository.FranchiseRepository;
import com.example.webapispringhibernate.Repository.MovieRepository;
import com.example.webapispringhibernate.model.Character;
import com.example.webapispringhibernate.model.Franchise;
import com.example.webapispringhibernate.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class MovieController {

    //---Repository access
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private FranchiseRepository franchiseRepository;

    //---Create a movie
    @PostMapping("/movie/add")
    public void createMovie(@RequestParam String director,
                             @RequestParam String picture,
                             @RequestParam Integer release_year,
                             @RequestParam String title,
                             @RequestParam String trailer) {
        Movie movie = new Movie();
        movie.setDirector(director);
        movie.setPicture(picture);
        movie.setReleaseYear(release_year);
        movie.setTitle(title);
        movie.setTrailer(trailer);
        movie.setCharacters(null);
        movie.setFranchise(null);
        movieRepository.save(movie);
    }

    //---Read all movies
    @GetMapping("/movie/all")
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    //---Read a movie by id
    @GetMapping("/movie/{id}")
    public Movie getAMovie(@PathVariable Integer id) {
        Movie movie = null;
        if (movieRepository.existsById(id)) {
            movie = movieRepository.findById(id).get();
        }
        return movie;
    }

    //---update a movie by id
    @PatchMapping("/movie/update/{id}")
    public Movie updateMovie(@PathVariable Integer id,
                             @RequestParam(required = false) String director,
                             @RequestParam(required = false) String picture,
                             @RequestParam(required = false) Integer release_year,
                             @RequestParam(required = false) String title,
                             @RequestParam(required = false) String trailer
    ) {
        Movie movie = movieRepository.findById(id).get();
        if (director == null) {
            movie.setDirector(movie.getDirector());
        } else {
            movie.setDirector(director);
        }
        if (picture == null) {
            movie.setPicture(movie.getPicture());
        } else {
            movie.setPicture(picture);
        }
        if (release_year == null) {
            movie.setReleaseYear(movie.getReleaseYear());
        } else {
            movie.setReleaseYear(release_year);
        }
        if (title == null) {
            movie.setTitle(movie.getTitle());
        } else {
            movie.setTitle(title);
        }
        if (trailer == null) {
            movie.setTrailer(movie.getTrailer());
        } else {
            movie.setTrailer(trailer);
        }

        movieRepository.save(movie);
        return movie;
    }

    //---Delete a movie by id
    @DeleteMapping("/movie/delete/{id}")
    public void delAMovie(@PathVariable Integer id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            for (Character character : movie.get().getCharacters()) {
                character.getMovies().remove(movie.get());
                characterRepository.save(character);
            }
            Franchise franchise = movie.get().getFranchise();
            franchise.getMovies().remove(movie.get());
            franchiseRepository.save(franchise);

        }
        movieRepository.deleteById(id);
    }

    //---read all characters in a movie
    @GetMapping("/movie/{id}/characters")
    public List<String> getMovieInCharacter(@PathVariable Integer id) {
        Movie movie = movieRepository.findById(id).get();
        return movie.characterGetter();
    }

    //---update characters in a movie
    @PatchMapping("/movie/{id}/update-characters")
    public Movie updateCharactersInMovie(@PathVariable Integer id, @RequestParam List<Integer> idChar) {

        List<Character> characterList = new ArrayList<>();
        Optional<Movie> movie = movieRepository.findById(id);
        List<Character> intialList = movie.get().getCharacters();

        if (movie.isPresent()) {
            for (int i = 0; i < idChar.size(); i++) {
                Optional<Character> character = characterRepository.findById(idChar.get(i));
                characterList.add(character.get());
            }
            List<Character> newList = Stream.concat(intialList.stream(), characterList.stream())
                    .collect(Collectors.toList());

            movie.get().setCharacters(newList);
            movieRepository.save(movie.get());
        }
        return movie.get();
    }

    //---replace characters in a movie with new character list
    @PostMapping("/movie/{id}/replace-characters")
    public Movie replaceCharactersInMovie(@PathVariable Integer id, @RequestParam List<Integer> idChar) {

        List<Character> characterList = new ArrayList<>();
        Optional<Movie> movie = movieRepository.findById(id);

        if (movie.isPresent()) {
            for (int i = 0; i < idChar.size(); i++) {
                Optional<Character> character = characterRepository.findById(idChar.get(i));
                characterList.add(character.get());
            }
            movie.get().setCharacters(characterList);
            movieRepository.save(movie.get());
        }
        return movie.get();
    }
}
