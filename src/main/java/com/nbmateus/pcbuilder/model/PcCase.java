package com.nbmateus.pcbuilder.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class PcCase extends PcComponent {

    @Enumerated(EnumType.STRING)
    private MotherboardSize maxMoboSize;
    private int driveBays25;
    private int driveBays35;

    public PcCase(String maker, String name, int minPrice, int maxPrice, MotherboardSize maxMoboSize, int driveBays25,
            int driveBays35) {
        super(maker, name, minPrice, maxPrice);
        this.maxMoboSize = maxMoboSize;
        this.driveBays25 = driveBays25;
        this.driveBays35 = driveBays35;
    }

    public PcCase() {
        super();
    }

    public MotherboardSize getMaxMoboSize() {
        return maxMoboSize;
    }

    public void setMaxMoboSize(MotherboardSize maxMoboSize) {
        this.maxMoboSize = maxMoboSize;
    }

    public int getDriveBays25() {
        return driveBays25;
    }

    public void setDriveBays25(int driveBays25) {
        this.driveBays25 = driveBays25;
    }

    public int getDriveBays35() {
        return driveBays35;
    }

    public void setDriveBays35(int driveBays35) {
        this.driveBays35 = driveBays35;
    }

    @Override
    public String toString() {
        return "Case [id=" + getId() + ", maker=" + getMaker() + ", maxPrice=" + getMaxPrice() + ", minPrice="
                + getMinPrice() + ", name=" + getName() + "driveBays25=" + driveBays25 + ", driveBays35=" + driveBays35
                + ", maxMoboSize=" + maxMoboSize + "]";
    }

}