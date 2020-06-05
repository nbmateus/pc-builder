package com.nbmateus.pcbuilder.dao;

import com.nbmateus.pcbuilder.model.Storage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends CrudRepository<Storage, Long> {

    @Query(value = "SELECT * FROM Storage s where s.maker = :maker and s.name = :name", nativeQuery = true)
    Storage findByMakerAndName(@Param("maker") String maker, @Param("name") String name);

}