package com.refapps.trippin.function;

import com.refapps.trippin.model.Airport;
import com.refapps.trippin.model.Person;
import com.refapps.trippin.model.PlanItem;
import com.refapps.trippin.model.Trip;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmFunction;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmParameter;
import com.sap.olingo.jpa.metadata.core.edm.mapper.extension.ODataFunction;
import com.refapps.trippin.repository.AirportRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
public class JavaFunctions implements ODataFunction {

    private final AirportRepository airportRepository;

    public JavaFunctions(final EntityManager entityManager) {
        super();
        this.airportRepository = new AirportRepository( entityManager);
    }

    @EdmFunction(name = "GetNearestAirport", returnType = @EdmFunction.ReturnType, hasFunctionImport = true, isBound = false )
    public Airport getNearestAirport(@EdmParameter(name = "Lat") final  double lat, @EdmParameter(name = "Lon") final
    double lon) {

        List<Airport> airportList  = airportRepository.findAll();
        double min = Integer.MAX_VALUE;
        Airport result = null;
        if(airportList != null) {
            for(Airport airport : airportList) {
                double dist = calculateDis(lat, lon, airport.getLatitude(), airport.getLongitude());
                if(min > dist) {
                    min = dist;
                    result = airport;
                }
            }
        }
        return result;
    }

    private double calculateDis(double lat, double lon, Double latitude, Double longitude) {
        //actual logic of calculating distance;
        return (latitude - lat) + (longitude - lon);
    }

    @EdmFunction(name = "GetPersonTrips", isBound = false, returnType = @EdmFunction.ReturnType(isCollection = true, type = Trip.class), hasFunctionImport = true)
    public List<Trip> getPersonTrips(
            @EdmParameter(name = "person") final Person person) {
        return person.getTrips();
    }

    @EdmFunction(name = "GetPersonAllTripItems" ,  isBound = false, returnType = @EdmFunction.ReturnType(isCollection = true, type = PlanItem.class), hasFunctionImport = true)
    public List<PlanItem> getPersonAllTripItems(
            @EdmParameter(name = "Person") final Person person, @EdmParameter(name = "TripNameSearchText") final String tripNameSearchText)  {
        List<PlanItem> planItems = new ArrayList<>();
        for(Trip trip : person.getTrips()) {
            if(trip.getName()!= null && trip.getName().toLowerCase().contains(tripNameSearchText.toLowerCase())) {
                planItems.addAll(trip.getPlanItems());
            }
        }
        return planItems;
    }

    @EdmFunction(name = "GetTripItems" ,  isBound = false, returnType = @EdmFunction.ReturnType(isCollection = true, type = PlanItem.class), hasFunctionImport = true)
    public List<PlanItem> getPersonAllTripItems(
            @EdmParameter(name = "Trip") final Trip trip)  {
        return trip.getPlanItems() != null ? trip.getPlanItems() : new ArrayList<>();
    }

}
