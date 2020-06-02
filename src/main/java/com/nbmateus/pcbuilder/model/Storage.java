package com.nbmateus.pcbuilder.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Storage extends PcComponent {

    @Enumerated(EnumType.STRING)
    private StorageType storageType;
    private int writeSpeed;
    private int readSpeed;
    private String capacity;

    public Storage(String maker, String name, int minPrice, int maxPrice, StorageType storageType, int writeSpeed,
            int readSpeed, String capacity) {
        super(maker, name, minPrice, maxPrice);
        this.storageType = storageType;
        this.writeSpeed = writeSpeed;
        this.readSpeed = readSpeed;
        this.capacity = capacity;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }

    public int getWriteSpeed() {
        return writeSpeed;
    }

    public void setWriteSpeed(int writeSpeed) {
        this.writeSpeed = writeSpeed;
    }

    public int getReadSpeed() {
        return readSpeed;
    }

    public void setReadSpeed(int readSpeed) {
        this.readSpeed = readSpeed;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Storage [id=" + getId() + ", maker=" + getMaker() + ", maxPrice=" + getMaxPrice() + ", minPrice="
                + getMinPrice() + ", name=" + getName() + "capacity=" + capacity + ", readSpeed=" + readSpeed
                + ", storageType=" + storageType + ", writeSpeed=" + writeSpeed + "]";
    }
}