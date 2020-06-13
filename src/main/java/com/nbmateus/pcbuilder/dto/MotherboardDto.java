package com.nbmateus.pcbuilder.dto;

import com.nbmateus.pcbuilder.model.RamType;

public class MotherboardDto {
    private RamType ramType;
    private int maxRamSpeed;

    public MotherboardDto(RamType ramType, int maxRamSpeed) {
        this.ramType = ramType;
        this.maxRamSpeed = maxRamSpeed;
    }

    public RamType getRamType() {
        return ramType;
    }

    public void setRamType(RamType ramType) {
        this.ramType = ramType;
    }

    public int getMaxRamSpeed() {
        return maxRamSpeed;
    }

    public void setMaxRamSpeed(int maxRamSpeed) {
        this.maxRamSpeed = maxRamSpeed;
    }
}

