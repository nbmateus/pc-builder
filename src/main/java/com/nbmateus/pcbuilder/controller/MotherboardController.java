package com.nbmateus.pcbuilder.controller;

import javax.servlet.http.HttpServletResponse;

import com.nbmateus.pcbuilder.dto.CpuDto;
import com.nbmateus.pcbuilder.model.Motherboard;
import com.nbmateus.pcbuilder.service.MotherboardService;

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
@RequestMapping("/motherboard")
public class MotherboardController {

    @Autowired
    MotherboardService motherboardService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<Motherboard>> getAll() {
        return new ResponseEntity<Iterable<Motherboard>>(motherboardService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motherboard> getMotherboard(@PathVariable("id") long id) {
        Motherboard motherboard = null;
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            motherboard = motherboardService.findById(id);
        } catch (Exception exception) {
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<Motherboard>(motherboard, httpStatus);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public void addMotherboard(@RequestBody Motherboard motherboard, HttpServletResponse response) {
        try {
            motherboardService.addMotherboard(motherboard);
            response.setStatus(HttpStatus.CREATED.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.CONFLICT.value());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public void updateMotherboard(@PathVariable("id") long id, @RequestBody Motherboard updatedMotherboard,
            HttpServletResponse response) {
        try {
            motherboardService.updateMotherboard(id, updatedMotherboard);
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception exception) {
            if (exception.getMessage().equals("Motherboard duplicated.")) {
                response.setStatus(HttpStatus.CONFLICT.value());
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteMotherboard(@PathVariable("id") long id, HttpServletResponse response) {
        try {
            motherboardService.delete(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @GetMapping("/filtered-by-cpu-specs")
    public ResponseEntity<Iterable<Motherboard>> getMobo(@RequestBody CpuDto cpuDto) {
        return new ResponseEntity<Iterable<Motherboard>>(motherboardService.findByCpuSpecs(cpuDto), HttpStatus.OK);
    }
}