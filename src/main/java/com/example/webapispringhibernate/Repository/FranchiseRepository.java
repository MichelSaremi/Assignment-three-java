package com.example.webapispringhibernate.Repository;

import com.example.webapispringhibernate.model.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise, Integer> {
}
