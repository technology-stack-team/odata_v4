package com.refapps.trippin.function;

import com.refapps.trippin.model.Person;
import com.refapps.trippin.model.Trip;
import com.refapps.trippin.model.jointable.PersonTrip;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmAction;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmParameter;
import com.sap.olingo.jpa.metadata.core.edm.mapper.extension.ODataAction;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

@Component
public class JavaActions implements ODataAction {
    private final EntityManager entityManager;

    public JavaActions(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @EdmAction(name = "ResetDataSource", isBound = false)
    public void resetDataSource() {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.createNativeQuery("DELETE FROM \"Trippin\".\"Airport\"").executeUpdate();
        entityTransaction.commit();
    }

    @EdmAction(name = "UpdateLastName", isBound = true)
    public boolean updateLastName(@EdmParameter(name = "Person") final Person person, @EdmParameter(name = "LastName", isNullable = false) final String lastName) {
        if(person == null)
            return false;
        entityManager.getTransaction().begin();
        person.setLastName(lastName);
        entityManager.merge(person);
        entityManager.getTransaction().commit();
        return true;
    }

    @EdmAction(name = "ShareTrip", isBound = true)
    public void shareTrip(@EdmParameter(name = "PersonInstance") final Person person,
                          @EdmParameter(name = "UserName", isNullable = false) final String userName,
                          @EdmParameter(name = "TripId", isNullable = false) final int tripId) {
        if(person != null && !CollectionUtils.isEmpty(person.getTrips())) {
            for(Trip trip : person.getTrips()) {
                if(trip.getTripId().equals(tripId)) {
                    Person traveller = entityManager.find(Person.class, userName);
                    if(traveller != null) {
                        PersonTrip personTrip = new PersonTrip();
                        personTrip.setTripId(tripId);
                        personTrip.setUserName(userName);
                        entityManager.getTransaction().begin();
                        entityManager.merge(personTrip);
                        entityManager.getTransaction().commit();
                    }
                }
            }
        }
    }
}
