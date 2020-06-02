package com.nbmateus.pcbuilder.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class PC {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    private int totalMinPrice;
    private int totalMaxPrice;
    private int estimatedPrice;
    private int recommendedPsuWattage;

    @ManyToOne
    private Motherboard motherboard;

    @ManyToOne
    private CPU cpu;

    @ManyToOne
    private GPU gpu;

    @ManyToOne
    private Cooler cpuCooler;

    @ManyToOne
    private RAM ram;
    private int ramUnits;
    
    @ManyToMany
    private Set<Storage> storage;

    @ManyToOne
    private Case pcCase;

    @ManyToOne
    private PSU psu;

    public PC(String name, String description, int totalMinPrice, int totalMaxPrice, int estimatedPrice,
            int recommendedPsuWattage, Motherboard motherboard, CPU cpu, GPU gpu, Cooler cpuCooler, RAM ram,
            int ramUnits, Set<Storage> storage, Case pcCase, PSU psu) {
        this.name = name;
        this.description = description;
        this.totalMinPrice = totalMinPrice;
        this.totalMaxPrice = totalMaxPrice;
        this.estimatedPrice = estimatedPrice;
        this.recommendedPsuWattage = recommendedPsuWattage;
        this.motherboard = motherboard;
        this.cpu = cpu;
        this.gpu = gpu;
        this.cpuCooler = cpuCooler;
        this.ram = ram;
        this.ramUnits = ramUnits;
        this.storage = storage;
        this.pcCase = pcCase;
        this.psu = psu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalMinPrice() {
        return totalMinPrice;
    }

    public void setTotalMinPrice(int totalMinPrice) {
        this.totalMinPrice = totalMinPrice;
    }

    public int getTotalMaxPrice() {
        return totalMaxPrice;
    }

    public void setTotalMaxPrice(int totalMaxPrice) {
        this.totalMaxPrice = totalMaxPrice;
    }

    public int getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(int estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public int getRecommendedPsuWattage() {
        return recommendedPsuWattage;
    }

    public void setRecommendedPsuWattage(int recommendedPsuWattage) {
        this.recommendedPsuWattage = recommendedPsuWattage;
    }

    public Motherboard getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(Motherboard motherboard) {
        this.motherboard = motherboard;
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public GPU getGpu() {
        return gpu;
    }

    public void setGpu(GPU gpu) {
        this.gpu = gpu;
    }

    public Cooler getCpuCooler() {
        return cpuCooler;
    }

    public void setCpuCooler(Cooler cpuCooler) {
        this.cpuCooler = cpuCooler;
    }

    public RAM getRam() {
        return ram;
    }

    public void setRam(RAM ram) {
        this.ram = ram;
    }

    public int getRamUnits() {
        return ramUnits;
    }

    public void setRamUnits(int ramUnits) {
        this.ramUnits = ramUnits;
    }

    public Set<Storage> getstorage() {
        return storage;
    }

    public void setstorage(Set<Storage> storage) {
        this.storage = storage;
    }

    public Case getPcCase() {
        return pcCase;
    }

    public void setPcCase(Case pcCase) {
        this.pcCase = pcCase;
    }

    public PSU getPsu() {
        return psu;
    }

    public void setPsu(PSU psu) {
        this.psu = psu;
    }

    @Override
    public String toString() {
        return "PC [cpu=" + cpu + ", cpuCooler=" + cpuCooler + ", description=" + description + ", estimatedPrice="
                + estimatedPrice + ", gpu=" + gpu + ", motherboard=" + motherboard + ", name=" + name + ", pcCase="
                + pcCase + ", psu=" + psu + ", ram=" + ram + ", ramUnits=" + ramUnits + ", recommendedPsuWattage="
                + recommendedPsuWattage + ", storage=" + storage + ", totalMaxPrice=" + totalMaxPrice
                + ", totalMinPrice=" + totalMinPrice + "]";
    }
}