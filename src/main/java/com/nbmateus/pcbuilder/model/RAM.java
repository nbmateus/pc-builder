package com.nbmateus.pcbuilder.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class RAM extends PcComponent {

    @Enumerated(EnumType.STRING)
    private RamType type;
    private int capacity;
    private int speed;
    private String latency;

    public RAM(String maker, String name, int minPrice, int maxPrice, RamType type, int capacity, int speed,
            String latency) {
        super(maker, name, minPrice, maxPrice);
        this.type = type;
        this.capacity = capacity;
        this.speed = speed;
        this.latency = latency;
    }

    public RamType getType() {
        return type;
    }

    public void setType(RamType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getLatency() {
        return latency;
    }

    public void setLatency(String latency) {
        this.latency = latency;
    }

    @Override
    public String toString() {
        return "RAM [id=" + getId() + ", maker=" + getMaker() + ", maxPrice=" + getMaxPrice() + ", minPrice="
                + getMinPrice() + ", name=" + getName() + "capacity=" + capacity + ", latency=" + latency + ", speed="
                + speed + ", type=" + type + "]";
    }

}