package com.nbmateus.pcbuilder.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ProcessingUnit extends PcComponent{

    private float baseClock;
    private float boostClock;
    private int tdp;

    public ProcessingUnit(String maker, String name, int minPrice, int maxPrice, float baseClock, float boostClock,
            int tdp) {
        super(maker, name, minPrice, maxPrice);
        this.baseClock = baseClock;
        this.boostClock = boostClock;
        this.tdp = tdp;
    }

    public ProcessingUnit(){
        super();
    }

    public float getBaseClock() {
        return baseClock;
    }

    public void setBaseClock(float baseClock) {
        this.baseClock = baseClock;
    }

    public float getBoostClock() {
        return boostClock;
    }

    public void setBoostClock(float boostClock) {
        this.boostClock = boostClock;
    }

    public int getTdp() {
        return tdp;
    }

    public void setTdp(int tdp) {
        this.tdp = tdp;
    }

    @Override
    public String toString() {
        return "ProcessingUnit [id=" + getId() + ", maker=" + getMaker() + ", maxPrice=" + getMaxPrice() + ", minPrice=" + getMinPrice()
        + ", name=" + getName() + "baseClock=" + baseClock + ", boostClock=" + boostClock + ", tdp=" + tdp + "]";
    }
}