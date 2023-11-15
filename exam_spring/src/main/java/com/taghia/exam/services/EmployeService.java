package com.taghia.exam.services;

import java.util.List;

import com.taghia.exam.entities.ServiceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taghia.exam.dao.IDao;
import com.taghia.exam.entities.Employe;
import com.taghia.exam.repositories.EmployeRepository;

@Service
public class EmployeService implements IDao<Employe>{

    @Autowired
    private EmployeRepository employeRepository;

    @Override
    public Employe create(Employe o) {
        return employeRepository.save(o);
    }

    @Override
    public boolean delete(Employe o) {
        try {
            employeRepository.delete(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe findById(Long id) {
        return employeRepository.findById(id).orElse(null);
    }

    @Override
    public Employe update(Employe o) {
        return employeRepository.save(o);
    }

    public List<Employe> findAllByService(ServiceEntity service){
        return employeRepository.findAllByServiceEntity(service);
    }

}
