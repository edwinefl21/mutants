package com.meli.mutant.repository;

import com.meli.mutant.model.DNA;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDNARepository extends MongoRepository<DNA,String> {
}
