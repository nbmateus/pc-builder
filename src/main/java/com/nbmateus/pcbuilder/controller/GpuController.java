package com.nbmateus.pcbuilder.controller;

import javax.servlet.http.HttpServletResponse;

import com.nbmateus.pcbuilder.model.GPU;
import com.nbmateus.pcbuilder.service.GpuService;

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
@RequestMapping("/gpu")
public class GpuController {

    @Autowired
    GpuService gpuService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<GPU>> getAll() {
        return new ResponseEntity<Iterable<GPU>>(gpuService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GPU> getGpu(@PathVariable("id") long id) {
        GPU gpu = null;
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            gpu = gpuService.findById(id);
        } catch (Exception exception) {
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<GPU>(gpu, httpStatus);
    }

    @PostMapping("/add")
    public void addGpu(@RequestBody GPU gpu, HttpServletResponse response) {
        try {
            gpuService.addGpu(gpu);
            response.setStatus(HttpStatus.CREATED.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.CONFLICT.value());
        }
    }

    @PutMapping("/{id}")
    public void updateGpu(@PathVariable("id") long id, @RequestBody GPU updatedGpu, HttpServletResponse response) {
        try {
            gpuService.updateGpu(id, updatedGpu);
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception exception) {
            if (exception.getMessage().equals("GPU duplicated.")) {
                response.setStatus(HttpStatus.CONFLICT.value());
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
        }
    }

    @DeleteMapping("/{id}")
    public void deleteGpu(@PathVariable("id") long id, HttpServletResponse response) {
        try {
            gpuService.delete(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
        } catch (Exception exception) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
}