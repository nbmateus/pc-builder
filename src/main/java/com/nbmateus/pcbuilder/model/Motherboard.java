package com.nbmateus.pcbuilder.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Motherboard extends PcComponent {

    @Enumerated(EnumType.STRING)
    private MotherboardSize size;

    @Enumerated(EnumType.STRING)
    private Socket socket;

    @Enumerated(EnumType.STRING)
    private RamType ramType;

    private int ramSlots;
    private int maxRamSpeed;
    private boolean allowsOverclock;
    private int m2Slots;
    private int sataSlots;

    public Motherboard(String maker, String name, int minPrice, int maxPrice, MotherboardSize size, Socket socket,
            RamType ramType, int ramSlots, int maxRamSpeed, boolean allowsOverclock, int m2slots, int sataSlots) {
        super(maker, name, minPrice, maxPrice);
        this.size = size;
        this.socket = socket;
        this.ramType = ramType;
        this.ramSlots = ramSlots;
        this.maxRamSpeed = maxRamSpeed;
        this.allowsOverclock = allowsOverclock;
        this.m2Slots = m2slots;
        this.sataSlots = sataSlots;
    }

    public MotherboardSize getSize() {
        return size;
    }

    public void setSize(MotherboardSize size) {
        this.size = size;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public RamType getRamType() {
        return ramType;
    }

    public void setRamType(RamType ramType) {
        this.ramType = ramType;
    }

    public int getRamSlots() {
        return ramSlots;
    }

    public void setRamSlots(int ramSlots) {
        this.ramSlots = ramSlots;
    }

    public int getMaxRamSpeed() {
        return maxRamSpeed;
    }

    public void setMaxRamSpeed(int maxRamSpeed) {
        this.maxRamSpeed = maxRamSpeed;
    }

    public boolean isAllowsOverclock() {
        return allowsOverclock;
    }

    public void setAllowsOverclock(boolean allowsOverclock) {
        this.allowsOverclock = allowsOverclock;
    }

    public int getM2Slots() {
        return m2Slots;
    }

    public void setM2slots(int m2slots) {
        this.m2Slots = m2slots;
    }

    public int getSataSlots() {
        return sataSlots;
    }

    public void setSataSlots(int sataSlots) {
        this.sataSlots = sataSlots;
    }

    @Override
    public String toString() {
        return "Motherboard [id=" + getId() + ", maker=" + getMaker() + ", maxPrice=" + getMaxPrice() + ", minPrice="
                + getMinPrice() + ", name=" + getName() + "allowsOverclock=" + allowsOverclock
                + ", chassisFanConnectors=" + ", m2Slots=" + m2Slots + ", maxRamSpeed=" + maxRamSpeed + ", ramSlots="
                + ramSlots + ", ramType=" + ramType + ", sataSlots=" + sataSlots + ", size=" + size + ", socket="
                + socket + "]";
    }
}