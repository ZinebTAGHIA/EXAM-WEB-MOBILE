package com.taghia.exam.controllers;

import com.taghia.exam.entities.ServiceEntity;
import com.taghia.exam.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody ServiceEntity serviceDetails) {
        ServiceEntity service = serviceService.create(serviceDetails);
        if (service == null) {
            return new ResponseEntity<>("Invalid request Data", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(service, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        ServiceEntity service = serviceService.findById(id);
        if (service == null) {
            return new ResponseEntity<>("Service not found", HttpStatus.NOT_FOUND);
        } else {
            boolean deleted = serviceService.delete(service);
            if (deleted) {
                return new ResponseEntity<>("Service deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Service associated to a user", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody ServiceEntity service) {
        if (serviceService.findById(id) == null) {
            return new ResponseEntity<>("Service not found", HttpStatus.NOT_FOUND);
        } else {
            service.setId(id);
            return new ResponseEntity<>(serviceService.update(service), HttpStatus.OK);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<ServiceEntity>> findAll() {
        return new ResponseEntity<>(serviceService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        ServiceEntity service = serviceService.findById(id);
        if (service == null) {
            return new ResponseEntity<>("Service not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(service, HttpStatus.OK);
        }
    }
}
