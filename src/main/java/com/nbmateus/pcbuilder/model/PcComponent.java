package com.nbmateus.pcbuilder.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PcComponent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String maker;
    private String name;
    private int minPrice;
    private int maxPrice;

    public PcComponent(String maker, String name, int minPrice, int maxPrice) {
        this.maker = maker;
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public PcComponent(){
        super();
    }

    public long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public String toString() {
        return "PcComponent [id=" + id + ", maker=" + maker + ", maxPrice=" + maxPrice + ", minPrice=" + minPrice
                + ", name=" + name + "]";
    }

    public boolean equals(PcComponent pcComponent) {
        return this.id == pcComponent.getId() && this.maker.equals(pcComponent.getMaker()) && this.name.equals(pcComponent.getName());
    }
}