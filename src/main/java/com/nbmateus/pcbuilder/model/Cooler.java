package com.nbmateus.pcbuilder.model;
import javax.persistence.Entity;

@Entity
public class Cooler extends PcComponent {

    private int maxCpuTDP;
    private int maxRPM;
    private int noiseLevel;

    public Cooler(String maker, String name, int minPrice, int maxPrice, int maxCpuTDP, int maxRPM, int noiseLevel) {
        super(maker, name, minPrice, maxPrice);
        this.maxCpuTDP = maxCpuTDP;
        this.maxRPM = maxRPM;
        this.noiseLevel = noiseLevel;
    }

    public Cooler() {
        super();
    }

    public int getMaxCpuTDP() {
        return maxCpuTDP;
    }

    public void setMaxCpuTDP(int maxCpuTDP) {
        this.maxCpuTDP = maxCpuTDP;
    }

    public int getMaxRPM() {
        return maxRPM;
    }

    public void setMaxRPM(int maxRPM) {
        this.maxRPM = maxRPM;
    }

    public int getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(int noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    @Override
    public String toString() {
        return "Cooler [id=" + getId() + ", maker=" + getMaker() + ", maxPrice=" + getMaxPrice() + ", minPrice="
                + getMinPrice() + ", name=" + getName() + ", maxCpuTDP=" + maxCpuTDP + ", maxRPM=" + maxRPM
                + ", noiseLevel=" + noiseLevel + "]";
    }
}