package com.nbmateus.pcbuilder.service;

import java.util.Optional;

import com.nbmateus.pcbuilder.dao.PcCaseRepository;
import com.nbmateus.pcbuilder.model.PcCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PcCaseService {
    
    @Autowired
    PcCaseRepository pcCaseRepository;

    public PcCaseService(PcCaseRepository pcCaseRepository) {
        this.pcCaseRepository = pcCaseRepository;
    }

    public Iterable<PcCase> findAll() {
        return pcCaseRepository.findAll();
    }

    public PcCase findById(long id) throws Exception {
        Optional<PcCase> pcCaseData = pcCaseRepository.findById(id);
        if(!pcCaseData.isPresent()){
            throw new Exception("PcCase does not exist.");
        }
        return pcCaseData.get();
    }

    public PcCase findByMakerAndName(String maker, String name) {
        return pcCaseRepository.findByMakerAndName(maker, name);
    }

    public PcCase addPcCase(PcCase pcCase) throws Exception {
        if(findByMakerAndName(pcCase.getMaker(), pcCase.getName()) != null){
            throw new Exception("PcCase duplicated.");
        }
        return pcCaseRepository.save(pcCase);
    }

    public PcCase updatePcCase(long id, PcCase updatedPcCase) throws Exception {
        Optional<PcCase> pcCaseData = pcCaseRepository.findById(id);
        PcCase existingPcCase = findByMakerAndName(updatedPcCase.getMaker(), updatedPcCase.getName());
        if(!pcCaseData.isPresent()){
            throw new Exception("PcCase does not exist.");
        }
        if (existingPcCase != null && existingPcCase.getId() != id){
            throw new Exception("PcCase duplicated.");
        }

        pcCaseData.get().setMaker(updatedPcCase.getMaker());
        pcCaseData.get().setMaxPrice(updatedPcCase.getMaxPrice());
        pcCaseData.get().setMinPrice(updatedPcCase.getMinPrice());
        pcCaseData.get().setName(updatedPcCase.getName());
        pcCaseData.get().setDriveBays25(updatedPcCase.getDriveBays25());
        pcCaseData.get().setDriveBays35(updatedPcCase.getDriveBays35());
        pcCaseData.get().setMaxMoboSize(updatedPcCase.getMaxMoboSize());

        return pcCaseRepository.save(pcCaseData.get());
    }

    public void delete(long id) throws Exception {
        Optional<PcCase> pcCaseData = pcCaseRepository.findById(id);
        if(!pcCaseData.isPresent()){
            throw new Exception("PcCase does not exist.");
        }
        pcCaseRepository.delete(pcCaseData.get());
    }
}