package com.nbmateus.pcbuilder.controller;

import javax.servlet.http.HttpServletResponse;

import com.nbmateus.pcbuilder.dto.MotherboardDto;
import com.nbmateus.pcbuilder.model.RAM;
import com.nbmateus.pcbuilder.service.RamService;

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
@RequestMapping("/ram")
public class RamController {

    @Autowired
    RamService ramService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<RAM>> getAll() {
        return new ResponseEntity<Iterable<RAM>>(ramService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RAM> getRam(@PathVariable("id") long id) {
        RAM ram = null;
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            ram = ramService.findById(id);
        } catch (Exception exception) {
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<RAM>(ram, httpStatus);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public void addRam(@RequestBody RAM ram, HttpServletResponse response) {
        try {
            ramService.addRam(ram);
            response.setStatus(HttpStatus.CREATED.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.CONFLICT.value());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public void updateRam(@PathVariable("id") long id, @RequestBody RAM updatedRam, HttpServletResponse response) {
        try {
            ramService.updateRam(id, updatedRam);
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception exception) {
            if (exception.getMessage().equals("RAM duplicated.")) {
                response.setStatus(HttpStatus.CONFLICT.value());
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteRam(@PathVariable("id") long id, HttpServletResponse response) {
        try {
            ramService.delete(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @GetMapping("/filter-by-motherboard-specs")
    public ResponseEntity<Iterable<RAM>> findByMotherboardSpecs(@RequestBody MotherboardDto motherboardDto) {
        return new ResponseEntity<Iterable<RAM>>(ramService.findByMotherboardSpecs(motherboardDto), HttpStatus.OK);
    }
}