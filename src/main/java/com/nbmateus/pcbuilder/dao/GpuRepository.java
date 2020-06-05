package com.nbmateus.pcbuilder.dao;

import com.nbmateus.pcbuilder.model.GPU;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GpuRepository extends CrudRepository<GPU, Long>{

    @Query(value = "SELECT * FROM gpu WHERE gpu.maker = :maker AND gpu.name = :name", nativeQuery = true)
    GPU findByMakerAndName(@Param("maker") String maker, @Param("name") String name); 
    
}