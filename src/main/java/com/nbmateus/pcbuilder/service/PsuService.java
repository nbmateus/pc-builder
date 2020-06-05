package com.nbmateus.pcbuilder.service;

import java.util.Optional;

import com.nbmateus.pcbuilder.dao.PsuRepository;
import com.nbmateus.pcbuilder.model.PSU;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PsuService {

    @Autowired
    PsuRepository psuRepository;

    public PsuService(PsuRepository psuRepository) {
        this.psuRepository = psuRepository;
    }

    public Iterable<PSU> findAll() {
        return psuRepository.findAll();
    }

    public PSU findById(long id) throws Exception {
        Optional<PSU> psuData = psuRepository.findById(id);
        if (!psuData.isPresent()) {
            throw new Exception("PSU does not exist.");
        }
        return psuData.get();
    }

    public PSU findByMakerAndName(String maker, String name) {
        return psuRepository.findByMakerAndName(maker, name);
    }

    public PSU addPsu(PSU psu) throws Exception {
        if (findByMakerAndName(psu.getMaker(), psu.getName()) != null) {
            throw new Exception("PSU duplicated.");
        }
        return psuRepository.save(psu);
    }

    public PSU updatePsu(long id, PSU updatedPsu) throws Exception {
        Optional<PSU> psuData = psuRepository.findById(id);
        PSU existingPsu = findByMakerAndName(updatedPsu.getMaker(), updatedPsu.getName());
        if(!psuData.isPresent()){
            throw new Exception("PSU does not exist.");
        }
        if (existingPsu != null && existingPsu.getId() != id){
            throw new Exception("PSU duplicated.");
        }

        psuData.get().setMaker(updatedPsu.getMaker());
        psuData.get().setMaxPrice(updatedPsu.getMaxPrice());
        psuData.get().setMinPrice(updatedPsu.getMinPrice());
        psuData.get().setName(updatedPsu.getName());
        psuData.get().setCertification(updatedPsu.getCertification());
        psuData.get().setWattage(updatedPsu.getWattage());

        return psuRepository.save(psuData.get());
    }

    public void delete(long id) throws Exception {
        Optional<PSU> psuData = psuRepository.findById(id);
        if (!psuData.isPresent()) {
            throw new Exception("PSU does not exist.");
        }
        psuRepository.delete(psuData.get());
    }
}