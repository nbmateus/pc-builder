package com.nbmateus.pcbuilder.dao;

import com.nbmateus.pcbuilder.model.RAM;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RamRepository extends CrudRepository<RAM, Long>{
    
    @Query(value = "SELECT * FROM ram where ram.maker = :maker and ram.name = :name", nativeQuery = true)
    RAM findByMakerAndName(@Param("maker") String maker, @Param("name") String name);
}