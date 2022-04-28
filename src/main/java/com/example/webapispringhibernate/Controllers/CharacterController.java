package com.example.webapispringhibernate.Controllers;

import com.example.webapispringhibernate.Repository.CharacterRepository;
import com.example.webapispringhibernate.Repository.MovieRepository;
import com.example.webapispringhibernate.model.Character;
import com.example.webapispringhibernate.model.Movie;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@SecurityRequirement(name = "keycloak_implicit")
public class CharacterController {

    //---Repository access
    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private MovieRepository movieRepository;

    //---Create a Character
    @PostMapping("/character/add")
    public Character createCharacter(@RequestParam String alias,
                                     @RequestParam String gender,
                                     @RequestParam String name,
                                     @RequestParam String picture
    ) {
        Character character = new Character();
        character.setCharAlias(alias);
        character.setCharGender(gender);
        character.setCharName(name);
        character.setCharPic(picture);
        characterRepository.save(character);
        return character;
    }

    //---Read all the Characters in the database
    @GetMapping("/character/all")
    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
    }

    //---Read a character by id
    @GetMapping("/character/{id}")
    public Character getACharacter(@PathVariable Integer id) {
        Character character = null;
        if(characterRepository.existsById(id)) {
            character = characterRepository.findById(id).get();
        }
        return character;
    }

    //---update a character by id
    @PatchMapping("/character/update/{id}")
    public Character updateCharacter(@PathVariable Integer id, @RequestParam(required = false) String alias, @RequestParam(required = false) String gender, @RequestParam(required = false) String name, @RequestParam(required = false) String picture) {
        Character character = characterRepository.findById(id).get();
        if (alias == null) {
            character.setCharAlias(character.getCharAlias());
        } else {
            character.setCharAlias(alias);
        }
        if (gender == null) {
            character.setCharGender(character.getCharGender());
        } else {
            character.setCharGender(gender);
        }
        if (name == null) {
            character.setCharName(character.getCharName());
        } else {
            character.setCharName(name);
        }
        if (picture == null) {
            character.setCharPic(character.getCharPic());
        } else {
            character.setCharPic(picture);
        }
        characterRepository.save(character);
        return character;
    }

    //---Delete a character by id
    @DeleteMapping("/character/delete/{id}")
    public void delACharacter(@PathVariable Integer id){
        Optional<Character> character = characterRepository.findById(id);
        if(character.isPresent()){
            for (Movie movie: character.get().getMovies()){
                movie.getCharacters().remove(character.get());
                movieRepository.save(movie);
            }
        }
        characterRepository.deleteById(id);
    }

}
