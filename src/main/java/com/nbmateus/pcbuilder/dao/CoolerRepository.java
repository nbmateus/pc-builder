package com.nbmateus.pcbuilder.dao;

import com.nbmateus.pcbuilder.model.Cooler;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CoolerRepository extends CrudRepository<Cooler, Long>{
    
    @Query(value = "SELECT * FROM cooler where cooler.maker = :maker and cooler.name = :name", nativeQuery = true)
    Cooler findByMakerAndName(@Param("maker") String maker, @Param("name") String name);
}