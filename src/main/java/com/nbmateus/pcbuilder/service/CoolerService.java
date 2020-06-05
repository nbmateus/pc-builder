package com.nbmateus.pcbuilder.service;

import java.util.Optional;

import com.nbmateus.pcbuilder.dao.CoolerRepository;
import com.nbmateus.pcbuilder.model.Cooler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoolerService {

    @Autowired
    CoolerRepository coolerRepository;

    public CoolerService(CoolerRepository coolerRepository) {
        this.coolerRepository = coolerRepository;
    }

    public Iterable<Cooler> findAll() {
        return coolerRepository.findAll();
    }

    public Cooler findById(long id) throws Exception {
        Optional<Cooler> coolerData = coolerRepository.findById(id);
        if (!coolerData.isPresent()) {
            throw new Exception("Cooler does not exist.");
        }
        return coolerData.get();
    }

    public Cooler findByMakerAndName(String maker, String name) {
        return coolerRepository.findByMakerAndName(maker, name);
    }

    public Cooler addCooler(Cooler cooler) throws Exception {
        if (findByMakerAndName(cooler.getMaker(), cooler.getName()) != null) {
            throw new Exception("Cooler duplicated.");
        }
        return coolerRepository.save(cooler);
    }

    public Cooler updateCooler(long id, Cooler updatedCooler) throws Exception {
        Optional<Cooler> coolerData = coolerRepository.findById(id);
        Cooler existingCpu = findByMakerAndName(updatedCooler.getMaker(), updatedCooler.getName());
        if (!coolerData.isPresent()) {
            throw new Exception("Cooler does not exist.");
        }
        if (existingCpu != null && existingCpu.getId() != id) {
            throw new Exception("Cooler duplicated.");
        }

        coolerData.get().setMaker(updatedCooler.getMaker());
        coolerData.get().setMaxCpuTDP(updatedCooler.getMaxCpuTDP());
        coolerData.get().setMaxPrice(updatedCooler.getMaxPrice());
        coolerData.get().setMaxRPM(updatedCooler.getMaxRPM());
        coolerData.get().setMinPrice(updatedCooler.getMinPrice());
        coolerData.get().setName(updatedCooler.getName());
        coolerData.get().setNoiseLevel(updatedCooler.getNoiseLevel());

        return coolerRepository.save(coolerData.get());
    }

    public void delete(long id) throws Exception {
        Optional<Cooler> coolerData = coolerRepository.findById(id);
        if (!coolerData.isPresent()) {
            throw new Exception("Cooler does not exist.");
        }
        coolerRepository.delete(coolerData.get());
    }

}