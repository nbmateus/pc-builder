package com.nbmateus.pcbuilder.controller;

import javax.servlet.http.HttpServletResponse;

import com.nbmateus.pcbuilder.model.Cooler;
import com.nbmateus.pcbuilder.service.CoolerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cooler")
public class CoolerController {

    @Autowired
    CoolerService coolerService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<Cooler>> getAll() {
        return new ResponseEntity<Iterable<Cooler>>(coolerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cooler> getCooler(@PathVariable("id") long id) {
        Cooler cooler = null;
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            cooler = coolerService.findById(id);
        } catch (Exception exception) {
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<Cooler>(cooler, httpStatus);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public void addCooler(@RequestBody Cooler cooler, HttpServletResponse response) {
        try {
            coolerService.addCooler(cooler);
            response.setStatus(HttpStatus.CREATED.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.CONFLICT.value());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public void updateCooler(@PathVariable("id") long id, @RequestBody Cooler updatedCooler,
            HttpServletResponse response) {
        try {
            coolerService.updateCooler(id, updatedCooler);
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception exception) {
            if (exception.getMessage().equals("Cooler duplicated.")) {
                response.setStatus(HttpStatus.CONFLICT.value());
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteCooler(@PathVariable("id") long id, HttpServletResponse response) {
        try {
            coolerService.delete(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @GetMapping(value="/minimum-tdp/{cpuTdp}")
    public ResponseEntity<Iterable<Cooler>> findByCpuTdp(@PathVariable("cpuTdp") int cpuTdp) {
        return new ResponseEntity<Iterable<Cooler>>(coolerService.findByCpuTdp(cpuTdp), HttpStatus.OK);
    }
    

}