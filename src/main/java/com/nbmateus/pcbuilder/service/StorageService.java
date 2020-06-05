package com.nbmateus.pcbuilder.service;

import java.util.Optional;

import com.nbmateus.pcbuilder.dao.StorageRepository;
import com.nbmateus.pcbuilder.model.Storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageService {
    
    @Autowired
    StorageRepository storageRepository;

    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    public Iterable<Storage> findAll() {
        return storageRepository.findAll();
    }

    public Storage findById(long id) throws Exception {
        Optional<Storage> storageData = storageRepository.findById(id);
        if(!storageData.isPresent()){
            throw new Exception("Storage does not exist.");
        }
        return storageData.get();
    }

    public Storage findByMakerAndName(String maker, String name) {
        return storageRepository.findByMakerAndName(maker, name);
    }

    public Storage addStorage(Storage storage) throws Exception {
        if(findByMakerAndName(storage.getMaker(), storage.getName()) != null) {
            throw new Exception("Storage duplicated.");
        }
        return storageRepository.save(storage);
    }

    public Storage updateStorage(long id, Storage updatedStorage) throws Exception {
        Optional<Storage> storageData = storageRepository.findById(id);
        Storage existingStorage = findByMakerAndName(updatedStorage.getMaker(), updatedStorage.getName());
        if(!storageData.isPresent()){
            throw new Exception("Storage does not exist.");
        }
        if (existingStorage != null && existingStorage.getId() != id){
            throw new Exception("Storage duplicated.");
        }

        storageData.get().setMaker(updatedStorage.getMaker());
        storageData.get().setMaxPrice(updatedStorage.getMaxPrice());
        storageData.get().setMinPrice(updatedStorage.getMinPrice());
        storageData.get().setName(updatedStorage.getName());
        storageData.get().setCapacity(updatedStorage.getCapacity());
        storageData.get().setReadSpeed(updatedStorage.getReadSpeed());
        storageData.get().setStorageType(updatedStorage.getStorageType());
        storageData.get().setWriteSpeed(updatedStorage.getWriteSpeed());

        return storageRepository.save(storageData.get());
    }

    public void delete(long id) throws Exception {
        Optional<Storage> storageData = storageRepository.findById(id);
        if(!storageData.isPresent()){
            throw new Exception("Storage does not exist.");
        }
        storageRepository.delete(storageData.get());
    }
}