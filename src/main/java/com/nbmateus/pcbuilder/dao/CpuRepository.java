package com.nbmateus.pcbuilder.dao;

import com.nbmateus.pcbuilder.model.CPU;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CpuRepository extends CrudRepository<CPU, Long>{
        
    @Query(value = "SELECT * FROM cpu where cpu.maker = :maker and cpu.name = :name", nativeQuery = true)
    CPU findByMakerAndName(@Param("maker") String maker, @Param("name") String name);

    @Query(value = "SELECT * FROM cpu c where c.maker = :maker", nativeQuery = true)
    Iterable<CPU> findByMaker(@Param("maker") String maker);
}