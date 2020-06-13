package com.nbmateus.pcbuilder.controller;

import javax.servlet.http.HttpServletResponse;

import com.nbmateus.pcbuilder.model.CPU;
import com.nbmateus.pcbuilder.service.CpuService;

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
@RequestMapping("/cpu")
public class CpuController {

    @Autowired
    CpuService cpuService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<CPU>> getAll() {
        return new ResponseEntity<Iterable<CPU>>(cpuService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CPU> getCpu(@PathVariable("id") long id) {
        CPU cpu = null;
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            cpu = cpuService.findById(id);
        } catch (Exception exception) {
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<CPU>(cpu, httpStatus);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public void addCpu(@RequestBody CPU cpu, HttpServletResponse response) {
        try {
            cpuService.addCpu(cpu);
            response.setStatus(HttpStatus.CREATED.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.CONFLICT.value());

        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public void updateCpu(@PathVariable("id") long id, @RequestBody CPU updatedCpu, HttpServletResponse response) {
        try {
            cpuService.updateCpu(id, updatedCpu);
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception exception) {
            if (exception.getMessage().equals("CPU duplicated.")) {
                response.setStatus(HttpStatus.CONFLICT.value());
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteCpu(@PathVariable("id") long id, HttpServletResponse response) {
        try {
            cpuService.delete(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @GetMapping("/made-by/{maker}")
    public ResponseEntity<Iterable<CPU>> getCpuByMaker(@PathVariable("maker") String maker) {
        return new ResponseEntity<Iterable<CPU>>(cpuService.findByMaker(maker), HttpStatus.OK);
    }

}