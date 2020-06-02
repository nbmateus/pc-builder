package com.nbmateus.pcbuilder.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class PSU extends PcComponent {

    @Enumerated(EnumType.STRING)
    private PsuCertification certification;
    private int wattage;

    public PSU(String maker, String name, int minPrice, int maxPrice, PsuCertification certification, int wattage) {
        super(maker, name, minPrice, maxPrice);
        this.certification = certification;
        this.wattage = wattage;
    }

    public PsuCertification getCertification() {
        return certification;
    }

    public void setCertification(PsuCertification certification) {
        this.certification = certification;
    }

    public int getWattage() {
        return wattage;
    }

    public void setWattage(int wattage) {
        this.wattage = wattage;
    }

    @Override
    public String toString() {
        return "PSU [id=" + getId() + ", maker=" + getMaker() + ", maxPrice=" + getMaxPrice() + ", minPrice="
                + getMinPrice() + ", name=" + getName() + "certification=" + certification + ", wattage=" + wattage
                + "]";
    }

}