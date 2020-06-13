package com.nbmateus.pcbuilder.service;

import java.util.Optional;

import com.nbmateus.pcbuilder.dao.RamRepository;
import com.nbmateus.pcbuilder.dto.MotherboardDto;
import com.nbmateus.pcbuilder.model.RAM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RamService {
    
    @Autowired
    RamRepository ramRepository;

    public RamService(RamRepository ramRepository) {
        this.ramRepository = ramRepository;
    }

    public Iterable<RAM> findAll() {
        return ramRepository.findAll();
    }

    public RAM findById(long id) throws Exception {
        Optional<RAM> ramData = ramRepository.findById(id);
        if(!ramData.isPresent()){
            throw new Exception("RAM does not exist.");
        }
        return ramData.get();
    }

    public RAM findByMakerAndName(String maker, String name) {
        return ramRepository.findByMakerAndName(maker, name);
    }

    public RAM addRam(RAM ram) throws Exception {
        if(findByMakerAndName(ram.getMaker(), ram.getName()) != null){
            throw new Exception("RAM duplicated.");
        }
        return ramRepository.save(ram);
    }

    public RAM updateRam(long id, RAM updatedRam) throws Exception {
        Optional<RAM> ramData = ramRepository.findById(id);
        RAM existingRam = findByMakerAndName(updatedRam.getMaker(), updatedRam.getName());
        if(!ramData.isPresent()){
            throw new Exception("RAM does not exist.");
        }
        if (existingRam != null && existingRam.getId() != id){
            throw new Exception("RAM duplicated.");
        }

        ramData.get().setMaker(updatedRam.getMaker());
        ramData.get().setMaxPrice(updatedRam.getMaxPrice());
        ramData.get().setMinPrice(updatedRam.getMinPrice());
        ramData.get().setName(updatedRam.getName());
        ramData.get().setCapacity(updatedRam.getCapacity());
        ramData.get().setLatency(updatedRam.getLatency());
        ramData.get().setSpeed(updatedRam.getSpeed());
        ramData.get().setType(updatedRam.getType());

        return ramRepository.save(ramData.get());
    }

    public void delete(long id) throws Exception {
        Optional<RAM> ramData = ramRepository.findById(id);
        if(!ramData.isPresent()){
            throw new Exception("RAM does not exist.");
        }
        ramRepository.delete(ramData.get());
    }

    public Iterable<RAM> findByMotherboardSpecs(MotherboardDto motherboardDto){
        return ramRepository.findByMotherboardSpecs(motherboardDto.getRamType().name(), motherboardDto.getMaxRamSpeed());
    }
}