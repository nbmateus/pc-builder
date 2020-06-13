package com.nbmateus.pcbuilder.controller;

import javax.servlet.http.HttpServletResponse;

import com.nbmateus.pcbuilder.model.Storage;
import com.nbmateus.pcbuilder.service.StorageService;

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
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    StorageService storageService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<Storage>> getAll() {
        return new ResponseEntity<Iterable<Storage>>(storageService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Storage> getStorage(@PathVariable("id") long id) {
        Storage storage = null;
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            storage = storageService.findById(id);
        } catch (Exception exception) {
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(storage, httpStatus);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public void addStorage(@RequestBody Storage storage, HttpServletResponse response) {
        try {
            storageService.addStorage(storage);
            response.setStatus(HttpStatus.CREATED.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.CONFLICT.value());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public void updateStorage(@PathVariable("id") long id, @RequestBody Storage updatedStorage,
            HttpServletResponse response) {

        try {
            storageService.updateStorage(id, updatedStorage);
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception exception) {
            if (exception.getMessage().equals("Storage duplicated.")) {
                response.setStatus(HttpStatus.CONFLICT.value());
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteStorage(@PathVariable("id") long id, HttpServletResponse response) {
        try {
            storageService.delete(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
}