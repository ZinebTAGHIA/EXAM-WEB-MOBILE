package com.taghia.exam.controllers;

import com.taghia.exam.entities.Employe;
import com.taghia.exam.entities.ServiceEntity;
import com.taghia.exam.services.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employes")
public class EmployeController {
    @Autowired
    private EmployeService employeService;

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody Employe employeDetails) {
        Employe employe = employeService.create(employeDetails);
        if (employe == null) {
            return new ResponseEntity<>("Invalid request Data", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(employe, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Employe employe = employeService.findById(id);
        if (employe == null) {
            return new ResponseEntity<>("Employe not found", HttpStatus.NOT_FOUND);
        } else {
            boolean deleted = employeService.delete(employe);
            if (deleted) {
                return new ResponseEntity<>("Employe deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Employe associated to a user", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Employe employe) {
        if (employeService.findById(id) == null) {
            return new ResponseEntity<>("Employe not found", HttpStatus.NOT_FOUND);
        } else {
            employe.setId(id);
            return new ResponseEntity<>(employeService.update(employe), HttpStatus.OK);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Employe>> findAll() {
        return new ResponseEntity<>(employeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Employe employe = employeService.findById(id);
        if (employe == null) {
            return new ResponseEntity<>("Employe not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(employe, HttpStatus.OK);
        }
    }

    @PostMapping("/service")
    public ResponseEntity<List<Employe>> findEmployesByService(@RequestBody ServiceEntity service){

        return new ResponseEntity<>(employeService.findAllByService(service), HttpStatus.OK);
    }
}
