package com.taghia.exam.repositories;


import com.taghia.exam.entities.Employe;
import com.taghia.exam.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
     List<Employe> findAllByServiceEntity(ServiceEntity service);
}
