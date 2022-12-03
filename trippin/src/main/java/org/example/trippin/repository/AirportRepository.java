package org.example.trippin.repository;

import org.example.trippin.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import java.util.List;

@Component
public class AirportRepository extends SimpleJpaRepository<Airport, String> {
    private final EntityManager em;
    public AirportRepository(EntityManager em) {
        super(Airport.class, em);
        this.em = em;
    }
    @Override
    public List<Airport> findAll() {
        return em.createNativeQuery("Select * from \"Trippin\".\"Airport\"", Airport.class).getResultList();
    }
}
