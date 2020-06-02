package com.nbmateus.pcbuilder.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class CPU extends ProcessingUnit {
    
    private int cores;
    private int threads;

    @Enumerated(EnumType.STRING)
    private Socket socket;
    private boolean unlockedMultiplier;
    private boolean includesCooler;
    private boolean hasIntegratedGPU;

    public CPU(String maker, String name, int minPrice, int maxPrice, float baseClock, float boostClock, int tdp,
            int cores, int threads, Socket socket, boolean unlockedMultiplier, boolean includesCooler,
            boolean hasIntegratedGPU) {
        super(maker, name, minPrice, maxPrice, baseClock, boostClock, tdp);
        this.cores = cores;
        this.threads = threads;
        this.socket = socket;
        this.unlockedMultiplier = unlockedMultiplier;
        this.includesCooler = includesCooler;
        this.hasIntegratedGPU = hasIntegratedGPU;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isUnlockedMultiplier() {
        return unlockedMultiplier;
    }

    public void setUnlockedMultiplier(boolean unlockedMultiplier) {
        this.unlockedMultiplier = unlockedMultiplier;
    }

    public boolean isIncludesCooler() {
        return includesCooler;
    }

    public void setIncludesCooler(boolean includesCooler) {
        this.includesCooler = includesCooler;
    }

    public boolean isHasIntegratedGPU() {
        return hasIntegratedGPU;
    }

    public void setHasIntegratedGPU(boolean hasIntegratedGPU) {
        this.hasIntegratedGPU = hasIntegratedGPU;
    }

    @Override
    public String toString() {
        return "CPU [id=" + getId() + ", maker=" + getMaker() + ", maxPrice=" + getMaxPrice() + ", minPrice=" + getMinPrice()
        + ", name=" + getName() + "baseClock=" + getBaseClock() + ", boostClock=" + getBoostClock() + ", tdp=" + getTdp() + "cores=" + cores + ", hasIntegratedGPU=" + hasIntegratedGPU + ", includesCooler=" + includesCooler
                + ", socket=" + socket + ", threads=" + threads + ", unlockedMultiplier=" + unlockedMultiplier + "]";
    }
}