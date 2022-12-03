package org.example.trippin.function;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmFunction;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmParameter;
import com.sap.olingo.jpa.metadata.core.edm.mapper.extension.ODataFunction;
import org.example.trippin.model.Airport;
import org.example.trippin.repository.AirportRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
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


}
