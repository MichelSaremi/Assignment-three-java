package com.example.webapispringhibernate.Repository;

import com.example.webapispringhibernate.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.webapispringhibernate.model.Character;

public interface CharacterRepository extends JpaRepository<Character, Integer> {
}
