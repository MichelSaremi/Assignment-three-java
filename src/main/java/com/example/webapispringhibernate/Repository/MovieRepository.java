package com.example.webapispringhibernate.Repository;

import com.example.webapispringhibernate.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
