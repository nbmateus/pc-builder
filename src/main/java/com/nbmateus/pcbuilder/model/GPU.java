package com.nbmateus.pcbuilder.model;

import javax.persistence.Entity;

@Entity
public class GPU extends ProcessingUnit {

    private int memory;
    private boolean displayPort;
    private boolean hdmi;
    private boolean dvi;
    private boolean vga;

    public GPU(String maker, String name, int minPrice, int maxPrice, float baseClock, float boostClock, int tdp,
            int memory, boolean displayPort, boolean hdmi, boolean dvi, boolean vga) {
        super(maker, name, minPrice, maxPrice, baseClock, boostClock, tdp);
        this.memory = memory;
        this.displayPort = displayPort;
        this.hdmi = hdmi;
        this.dvi = dvi;
        this.vga = vga;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public boolean isDisplayPort() {
        return displayPort;
    }

    public void setDisplayPort(boolean displayPort) {
        this.displayPort = displayPort;
    }

    public boolean isHdmi() {
        return hdmi;
    }

    public void setHdmi(boolean hdmi) {
        this.hdmi = hdmi;
    }

    public boolean isDvi() {
        return dvi;
    }

    public void setDvi(boolean dvi) {
        this.dvi = dvi;
    }

    public boolean isVga() {
        return vga;
    }

    public void setVga(boolean vga) {
        this.vga = vga;
    }

    @Override
    public String toString() {
        return "GPU [id=" + getId() + ", maker=" + getMaker() + ", maxPrice=" + getMaxPrice() + ", minPrice="
                + getMinPrice() + ", name=" + getName() + "baseClock=" + getBaseClock() + ", boostClock="
                + getBoostClock() + ", tdp=" + getTdp() + "displayPort=" + displayPort + ", dvi=" + dvi + ", hdmi="
                + hdmi + ", memory=" + memory + ", vga=" + vga + "]";
    }
}