package com.refapps.trippin.function;

import java.util.List;

import com.refapps.trippin.model.Person;
import com.refapps.trippin.model.PlanItem;
import com.refapps.trippin.model.Trip;
import com.refapps.trippin.model.jointable.PersonTrip;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmAction;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmParameter;
import com.sap.olingo.jpa.metadata.core.edm.mapper.extension.ODataAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Component
public class JavaActions implements ODataAction {
    private static final Log LOGGER = LogFactory.getLog(JavaActions.class);
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

    @EdmAction(name="LinkTripWithPlanItem", isBound = false)
    public boolean linkTripWithPlanItem(@EdmParameter(name = "TripId")final int tripId, @EdmParameter(name = "PlanItemId") int planItemId) {
        try {
            // Find trip object by tripId
            Trip trip = entityManager.find(Trip.class, tripId);
            if (trip == null) {
                LOGGER.warn("Trip with TripId not found.");
                return false;
            }
            // Get plan items by plan Id
            List<PlanItem> planItems = trip.getPlanItems();
            PlanItem planItem = entityManager.find(PlanItem.class, planItemId);
            if (planItem == null) {
                LOGGER.warn("PlanItem with planItemId not found.");
                return false;
            }
            //Associate trip with plan
            planItems.add(planItem);
            trip.setPlanItems(planItems);
            entityManager.getTransaction().begin();
            entityManager.merge(trip);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            LOGGER.error("Error in LinkTripWithPlanItem", e);
            return false;
        }
    }
}
