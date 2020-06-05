package com.nbmateus.pcbuilder.service;

import java.util.Optional;

import com.nbmateus.pcbuilder.dao.MotherboardRepository;
import com.nbmateus.pcbuilder.model.Motherboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotherboardService {
    @Autowired
    MotherboardRepository motherboardRepository;

    public MotherboardService(MotherboardRepository motherboardRepository) {
        this.motherboardRepository = motherboardRepository;
    }

    public Iterable<Motherboard> findAll() {
        return motherboardRepository.findAll();
    }

    public Motherboard findById(long id) throws Exception {
        Optional<Motherboard> motherboardData = motherboardRepository.findById(id);
        if(!motherboardData.isPresent()){
            throw new Exception("Motherboard does not exist.");
        }
        return motherboardData.get();
    }

    public Motherboard findByMakerAndName(String maker, String name) {
        return motherboardRepository.findByMakerAndName(maker, name);
    }

    public Motherboard addMotherboard(Motherboard motherboard) throws Exception {
        if(findByMakerAndName(motherboard.getMaker(), motherboard.getName()) != null){
            throw new Exception("Motherboard duplicated.");
        }
        return motherboardRepository.save(motherboard);
    }

    public Motherboard updateMotherboard(long id, Motherboard updatedMotherboard) throws Exception {
        Optional<Motherboard> motherboardData = motherboardRepository.findById(id);
        Motherboard existingMotherboard = findByMakerAndName(updatedMotherboard.getMaker(), updatedMotherboard.getName());
        if(!motherboardData.isPresent()){
            throw new Exception("Motherboard does not exist.");
        }
        if (existingMotherboard != null && existingMotherboard.getId() != id){
            throw new Exception("Motherboard duplicated.");
        }

        motherboardData.get().setMaker(updatedMotherboard.getMaker());
        motherboardData.get().setMaxPrice(updatedMotherboard.getMaxPrice());
        motherboardData.get().setMinPrice(updatedMotherboard.getMinPrice());
        motherboardData.get().setName(updatedMotherboard.getName());
        motherboardData.get().setSocket(updatedMotherboard.getSocket());
        motherboardData.get().setAllowsOverclock(updatedMotherboard.isAllowsOverclock());
        motherboardData.get().setM2slots(updatedMotherboard.getM2Slots());
        motherboardData.get().setMaxRamSpeed(updatedMotherboard.getMaxRamSpeed());
        motherboardData.get().setRamSlots(updatedMotherboard.getRamSlots());
        motherboardData.get().setRamType(updatedMotherboard.getRamType());
        motherboardData.get().setSataSlots(updatedMotherboard.getSataSlots());
        motherboardData.get().setSize(updatedMotherboard.getSize());

        return motherboardRepository.save(motherboardData.get());
    }

    public void delete(long id) throws Exception {
        Optional<Motherboard> motherboardData = motherboardRepository.findById(id);
        if(!motherboardData.isPresent()){
            throw new Exception("Motherboard does not exist.");
        }
        motherboardRepository.delete(motherboardData.get());
    }
}