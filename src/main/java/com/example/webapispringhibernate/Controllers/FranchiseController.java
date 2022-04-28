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
public class FranchiseController {

    //---Repository access
    @Autowired
    private FranchiseRepository franchiseRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CharacterRepository characterRepository;

    //---Create a franchise
    @PostMapping("/franchise/add")
    public void createFranchise(@RequestParam String description,
                                     @RequestParam String name) {
        Franchise franchise = new Franchise();
        franchise.setDescription(description);
        franchise.setName(name);
        franchiseRepository.save(franchise);
    }

    //---Read all franchises
    @GetMapping("/franchise/all")
    public List<Franchise> getAllFranchises() {
        return franchiseRepository.findAll();
    }

    //---Read a franchise by id
    @GetMapping("/franchise/{id}")
    public Franchise getAFranchise(@PathVariable Integer id) {
        Franchise franchise = null;
        if (franchiseRepository.existsById(id)) {
            franchise = franchiseRepository.findById(id).get();
        }
        return franchise;
    }

    //---update a franchise by id
    @PatchMapping("/franchise/update/{id}")
    public Franchise updateFranchise(@PathVariable Integer id,
                                     @RequestParam(required = false) String description,
                                     @RequestParam(required = false) String name) {
        Franchise franchise = franchiseRepository.findById(id).get();
        if (name == null) {
            franchise.setName(franchise.getName());
        } else {
            franchise.setName(name);
        }
        if (description == null) {
            franchise.setDescription(franchise.getDescription());
        } else {
            franchise.setDescription(description);
        }
        franchiseRepository.save(franchise);
        return franchise;
    }

    //---Delete a franchise by id
    @DeleteMapping("/franchise/delete/{id}")
    public void delAFranchise(@PathVariable Integer id) {
        Optional<Franchise> franchise = franchiseRepository.findById(id);
        if (franchise.isPresent()) {
            for (Movie movie : franchise.get().getMovies()) {

                movie.setFranchise(null);
                movieRepository.save(movie);
            }
        }
        franchiseRepository.deleteById(id);
    }

    //---read all movies from a franchise
    @GetMapping("/franchise/{id}/movies")
    public List<String> getMovieInFranchise(@PathVariable Integer id) {
        Franchise franchise = franchiseRepository.findById(id).get();
        return franchise.get_movies_list();
    }

    //---read all characters from a franchise
    @GetMapping("/franchise/{id}/characters")
    public List<Character> getCharactersInFranchise(@PathVariable Integer id) {
        List<Character> characterList = new ArrayList<>();
        Optional<Franchise> franchise = franchiseRepository.findById(id);
        if (franchise.isPresent()) {
            for (Movie movie : franchise.get().getMovies()) {
                for (Character character : movie.getCharacters()) {
                    characterList.add(character);
                }
            }
        }
        return characterList;
    }

    //---update (add) movies in a franchise
    @PatchMapping("/franchise/{id}/update-movies")
    public Franchise updateMoviesInFranchise(@PathVariable Integer id, @RequestParam List<Integer> idMov) {

        List<Movie> movieList = new ArrayList<>();
        Franchise franchise = franchiseRepository.findById(id).get();
        List<Movie> intialList = franchise.getMovies();

        for (int i = 0; i < idMov.size(); i++) {
            Optional<Movie> movie = movieRepository.findById(idMov.get(i));
            movieList.add(movie.get());
        }
        List<Movie> newList = Stream.concat(intialList.stream(), movieList.stream())
                .collect(Collectors.toList());

        for (int i = 0; i < newList.size(); i++) {

            newList.get(i).setFranchise(franchise);
            movieRepository.save(newList.get(i));
        }

        franchise.setMovies(newList);
        return franchise;
    }

    //---replace movies in a franchise, with a new list
    @PostMapping("/franchise/{id}/delete-movies")
    public Franchise replaceMovieInFranchise(@PathVariable Integer id) {

        Franchise franchise = franchiseRepository.findById(id).get();
        List<Movie> initialList = franchise.getMovies();

        for (int i = 0; i < initialList.size(); i++) {
            initialList.get(i).delFranchise();
            movieRepository.save(initialList.get(i));
        }

        return franchise;
    }
}
