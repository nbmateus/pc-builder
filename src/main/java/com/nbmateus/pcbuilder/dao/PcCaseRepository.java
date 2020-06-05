package com.nbmateus.pcbuilder.dao;

import com.nbmateus.pcbuilder.model.PcCase;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PcCaseRepository extends CrudRepository<PcCase, Long>{
    
    @Query(value = "SELECT * FROM pc_case p where p.maker = :maker and p.name = :name", nativeQuery = true)
    PcCase findByMakerAndName(@Param("maker") String maker, @Param("name") String name);
}