package com.nbmateus.pcbuilder.dao;

import com.nbmateus.pcbuilder.model.Motherboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MotherboardRepository extends CrudRepository<Motherboard, Long>{

    @Query(value = "SELECT * FROM motherboard m where m.maker = :maker and m.name = :name", nativeQuery = true)
    Motherboard findByMakerAndName(@Param("maker") String maker, @Param("name") String name);
    
}