package com.nbmateus.pcbuilder.service;

import java.util.Optional;

import com.nbmateus.pcbuilder.dao.CpuRepository;
import com.nbmateus.pcbuilder.model.CPU;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CpuService {

    @Autowired
    CpuRepository cpuRepository;

    public CpuService(CpuRepository cpuRepository) {
        this.cpuRepository = cpuRepository;
    }

    public Iterable<CPU> findAll() {
        return cpuRepository.findAll();
    }

    public CPU findById(long id) throws Exception {
        Optional<CPU> cpuData = cpuRepository.findById(id);
        if (!cpuData.isPresent()) {
            throw new Exception("CPU does not exist.");
        }
        return cpuData.get();
    }

    public CPU findByMakerAndName(String maker, String name) {
        return cpuRepository.findByMakerAndName(maker, name);
    }

    public CPU addCpu(CPU cpu) throws Exception {
        if (findByMakerAndName(cpu.getMaker(), cpu.getName()) != null) {
            throw new Exception("CPU duplicated.");
        }
        return cpuRepository.save(cpu);
    }

    public CPU updateCpu(long id, CPU updatedCpu) throws Exception {
        Optional<CPU> cpuData = cpuRepository.findById(id);
        CPU existingCpu = findByMakerAndName(updatedCpu.getMaker(), updatedCpu.getName());
        if (!cpuData.isPresent()) {
            throw new Exception("CPU does not exist.");
        }
        if (existingCpu != null && existingCpu.getId() != id) {
            throw new Exception("CPU duplicated.");
        }

        cpuData.get().setBaseClock(updatedCpu.getBaseClock());
        cpuData.get().setBoostClock(updatedCpu.getBoostClock());
        cpuData.get().setCores(updatedCpu.getCores());
        cpuData.get().setHasIntegratedGPU(updatedCpu.isHasIntegratedGPU());
        cpuData.get().setIncludesCooler(updatedCpu.isIncludesCooler());
        cpuData.get().setMaker(updatedCpu.getMaker());
        cpuData.get().setMaxPrice(updatedCpu.getMaxPrice());
        cpuData.get().setMinPrice(updatedCpu.getMinPrice());
        cpuData.get().setName(updatedCpu.getName());
        cpuData.get().setSocket(updatedCpu.getSocket());
        cpuData.get().setTdp(updatedCpu.getTdp());
        cpuData.get().setThreads(updatedCpu.getThreads());

        return cpuRepository.save(cpuData.get());
    }

    public void delete(long id) throws Exception {
        Optional<CPU> cpuData = cpuRepository.findById(id);
        if (!cpuData.isPresent()) {
            throw new Exception("Cpu does not exist.");
        }
        cpuRepository.delete(cpuData.get());
    }

    public Iterable<CPU> findByMaker(String maker) {
        return cpuRepository.findByMaker(maker);
    }

}