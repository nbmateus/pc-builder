package com.nbmateus.pcbuilder.dao;

import com.nbmateus.pcbuilder.model.PSU;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PsuRepository extends CrudRepository<PSU, Long> {
    
    @Query(value = "SELECT * FROM psu where psu.maker = :maker and psu.name = :name", nativeQuery = true)
    PSU findByMakerAndName(@Param("maker") String maker, @Param("name") String name);

    @Query(value = "SELECT * FROM psu p WHERE p.wattage >= :power", nativeQuery = true)
    Iterable<PSU> findByPowerRequired(@Param("power") int power);
}