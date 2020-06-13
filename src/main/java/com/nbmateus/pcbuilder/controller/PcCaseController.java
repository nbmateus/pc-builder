package com.nbmateus.pcbuilder.controller;

import javax.servlet.http.HttpServletResponse;

import com.nbmateus.pcbuilder.model.PcCase;
import com.nbmateus.pcbuilder.service.PcCaseService;

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
@RequestMapping("/pcCase")
public class PcCaseController {

    @Autowired
    PcCaseService pcCaseService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<PcCase>> getAll() {
        return new ResponseEntity<Iterable<PcCase>>(pcCaseService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PcCase> getPcCase(@PathVariable("id") long id) {
        PcCase pcCase = null;
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            pcCase = pcCaseService.findById(id);
        } catch (Exception exception) {
            httpStatus =  HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<PcCase>(pcCase, httpStatus);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public void addPcCase(@RequestBody PcCase pcCase, HttpServletResponse response) {
        try {
            pcCaseService.addPcCase(pcCase);
            response.setStatus(HttpStatus.CREATED.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.CONFLICT.value());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public void updatePcCase(@PathVariable("id") long id, @RequestBody PcCase updatedPcCase,
            HttpServletResponse response) {
        try {
            pcCaseService.updatePcCase(id, updatedPcCase);
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception exception) {
            if (exception.getMessage().equals("PcCase duplicated.")) {
                response.setStatus(HttpStatus.CONFLICT.value());
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deletePcCase(@PathVariable("id") long id, HttpServletResponse response) {
        try {
            pcCaseService.delete(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
}