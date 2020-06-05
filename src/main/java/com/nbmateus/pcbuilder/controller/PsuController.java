package com.nbmateus.pcbuilder.controller;

import javax.servlet.http.HttpServletResponse;

import com.nbmateus.pcbuilder.model.PSU;
import com.nbmateus.pcbuilder.service.PsuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/psu")
public class PsuController {

    @Autowired
    PsuService psuService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<PSU>> getAll() {
        return new ResponseEntity<Iterable<PSU>>(psuService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PSU> getPsu(@PathVariable("id") long id) {
        PSU psu = null;
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            psu = psuService.findById(id);
        } catch (Exception exception) {
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<PSU>(psu, httpStatus);
    }

    @PostMapping("/add")
    public void addPsu(@RequestBody PSU psu, HttpServletResponse response) {
        try {
            psuService.addPsu(psu);
            response.setStatus(HttpStatus.CREATED.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.CONFLICT.value());
        }
    }

    @PutMapping("/{id}")
    public void updatePsu(@PathVariable("id") long id, @RequestBody PSU updatedPsu, HttpServletResponse response) {
        try {
            psuService.updatePsu(id, updatedPsu);
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception exception) {
            if (exception.getMessage().equals("PSU duplicated.")) {
                response.setStatus(HttpStatus.CONFLICT.value());
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
        }
    }

    @DeleteMapping("/{id}")
    public void deletePsu(@PathVariable("id") long id, HttpServletResponse response) {
        try {
            psuService.delete(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
}