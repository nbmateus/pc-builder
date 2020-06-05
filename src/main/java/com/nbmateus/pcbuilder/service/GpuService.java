package com.nbmateus.pcbuilder.service;

import java.util.Optional;

import com.nbmateus.pcbuilder.dao.GpuRepository;
import com.nbmateus.pcbuilder.model.GPU;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GpuService {
    
    @Autowired
    GpuRepository gpuRepository;

    public GpuService(GpuRepository gpuRepository) {
        this.gpuRepository = gpuRepository;
    }

    public Iterable<GPU> findAll() {
        return gpuRepository.findAll();
    }

    public GPU findById(long id) throws Exception {
        Optional<GPU> gpuData = gpuRepository.findById(id);
        if(!gpuData.isPresent()) {
            throw new Exception("GPU does not exist.");
        }
        return  gpuData.get();
    }

    public GPU findByMakerAndName(String maker, String name) {
        return gpuRepository.findByMakerAndName(maker, name);
    }

    public GPU addGpu(GPU gpu) throws Exception {
        if(findByMakerAndName(gpu.getMaker(), gpu.getName()) != null){
            throw new Exception("GPU duplicated.");
        }
        return gpuRepository.save(gpu);
    }

    public GPU updateGpu(long id, GPU updatedGpu) throws Exception {
        Optional<GPU> gpuData = gpuRepository.findById(id);
        GPU existingGpu = findByMakerAndName(updatedGpu.getMaker(), updatedGpu.getName());
        if(!gpuData.isPresent()) {
            throw new Exception("GPU does not exist.");
        }
        if (existingGpu != null && existingGpu.getId() != id){
            throw new Exception("GPU duplicated.");
        }

        gpuData.get().setBaseClock(updatedGpu.getBaseClock());
        gpuData.get().setBoostClock(updatedGpu.getBoostClock());
        gpuData.get().setMaker(updatedGpu.getMaker());
        gpuData.get().setMaxPrice(updatedGpu.getMaxPrice());
        gpuData.get().setMinPrice(updatedGpu.getMinPrice());
        gpuData.get().setName(updatedGpu.getName());
        gpuData.get().setTdp(updatedGpu.getTdp());
        gpuData.get().setDisplayPort(updatedGpu.isDisplayPort());
        gpuData.get().setDvi(updatedGpu.isDvi());
        gpuData.get().setHdmi(updatedGpu.isHdmi());
        gpuData.get().setMemory(updatedGpu.getMemory());
        gpuData.get().setVga(updatedGpu.isVga());

        return gpuRepository.save(gpuData.get());
    }

    public void delete(long id) throws Exception {
        Optional<GPU> gpuData = gpuRepository.findById(id);
        if(!gpuData.isPresent()) {
            throw new Exception("GPU does not exist.");
        }
        gpuRepository.delete(gpuData.get());
    }
}