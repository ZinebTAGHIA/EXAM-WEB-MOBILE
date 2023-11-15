package com.taghia.exam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taghia.exam.dao.IDao;
import com.taghia.exam.entities.ServiceEntity;
import com.taghia.exam.repositories.ServiceRepository;

@Service
public class ServiceService implements IDao<ServiceEntity>{

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public ServiceEntity create(ServiceEntity o) {
        return serviceRepository.save(o);
    }

    @Override
    public boolean delete(ServiceEntity o) {
        try {
            serviceRepository.delete(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ServiceEntity> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public ServiceEntity findById(Long id) {
        return serviceRepository.findById(id).orElse(null);
    }

    @Override
    public ServiceEntity update(ServiceEntity o) {
        return serviceRepository.save(o);
    }

}
