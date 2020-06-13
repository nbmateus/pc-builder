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

    @Query(value = "SELECT * FROM ram r WHERE r.type = :ramType AND r.speed <= :maxSpeed", nativeQuery = true)
    Iterable<RAM> findByMotherboardSpecs(@Param("ramType") String ramType, @Param("maxSpeed") int maxSpeed);

}