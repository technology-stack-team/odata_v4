package com.refapps.trippin.integrationtest;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.refapps.trippin.SpringApp;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.element.Node;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
class ControllerTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final ObjectMapper mapper = new ObjectMapper();

  @Autowired
  private WebApplicationContext context;
  @LocalServerPort
  private int port;

  @BeforeEach
  void setup() {
    jdbcTemplate.execute("ALTER TABLE Trippin.Person ALTER COLUMN Cost DROP NOT NULL");
    jdbcTemplate.execute("ALTER TABLE Trippin.Person ALTER COLUMN Budget DROP NOT NULL");
    RestAssuredMockMvc.webAppContextSetup(context);
  }

  private String getPayload(String filePath) throws IOException {
    try(InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)){
      JsonNode jsonNode = mapper.readValue(in, JsonNode.class);
      String jsonString = mapper.writeValueAsString(jsonNode);
      return jsonString;
    }
    catch(Exception e){
      throw new RuntimeException(e);
    }
  }

  @Test
  void testRetrieveServiceDocument() {
    final String xml = given()
        .accept(ContentType.XML)
        .when()
        .get("/TripPinRESTierService/")
        .then()
        .statusCode(HttpStatusCode.OK.getStatusCode())
        .contentType(ContentType.XML)
        .extract()
        .asString();

    final XmlPath path = new XmlPath(xml);
    final Collection<Node> n = ((Node) ((Node) path.get("service")).get("workspace")).get("collection");
    assertNotNull(n);
    assertFalse(n.isEmpty());
  }

  @Test
  void  testRetrieveMetadataDocument() {
    final String xml = given()
        .when()
        .get("/TripPinRESTierService/$metadata")
        .then()
        .statusCode(HttpStatusCode.OK.getStatusCode())
        .contentType(ContentType.XML)
        .extract()
        .asString();

    final XmlPath path = new XmlPath(xml);
    final Node n = ((Node) ((Node) path.get("edmx:Ed mx")).get("DataServices")).get("Schema");
    assertNotNull(n);
    assertEquals("Trippin", n.getAttribute("Namespace"));
    assertNotNull(n.get("EntityContainer"));
  }

  @Test
  void  testCreateInstance() throws IOException {
    given()
        .contentType("application/json")
        .body(getPayload("PersonInstance.json"))
        .when()
        .post("/TripPinRESTierService/Persons")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
    given()
        .accept(ContentType.JSON)
        .when()
        .get("/TripPinRESTierService/Persons('lewisblack1')")
        .then()
        .statusCode(HttpStatusCode.OK.getStatusCode());
  }


//  @Test
//  void  testCreateInstanceWithBatch() throws URISyntaxException {
//
//    URI uri = getClass().getClassLoader()
//        .getResource("requests/CreateEntityViaBatch.txt").toURI();
//
//    File myFile = new File(uri);
//    final String responce = given()
//        .contentType("multipart/mixed;boundary=abc")
//        .body(myFile)
//        .when()
//        .post("/TripPinRESTierService/$batch")
//        .then()
//        .statusCode(HttpStatusCode.ACCEPTED.getStatusCode())
//        .extract()
//        .asString();
//
//    given()
//        .accept(ContentType.JSON)
//        .when()
//        .get("/TripPinRESTierService/Persons('russellwhyte')")
//        .then()
//        .statusCode(HttpStatusCode.OK.getStatusCode());
//
//    final String[] partResults = responce.split("--changeset");
//    assertTrue(partResults[1].contains("HTTP/1.1 201"));
//    assertTrue(partResults[2].contains("HTTP/1.1 400"));
//  }
//


  @Test
  void  testCreateInstanceDeepWithTrips() throws IOException {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(getPayload("PersonDeepInstanceTrip.json"))
        .when()
        .post("/TripPinRESTierService/Persons")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
    given()
        .accept(ContentType.JSON)
        .when()
        .get("/TripPinRESTierService/Trips/$count")
        .then()
        .statusCode(HttpStatusCode.OK.getStatusCode())
                .body(is("2"));
  }

  @Test
  void  testCreateInstanceDeepWithFriends() throws IOException {
    given()
            .contentType("application/json")
            .body(getPayload("PersonDeepInstanceFriend.json"))
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());
    given()
            .accept(ContentType.JSON)
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')")
            .then()
            .statusCode(HttpStatusCode.OK.getStatusCode());
    given()
            .accept(ContentType.JSON)
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack2')")
            .then()
            .statusCode(HttpStatusCode.OK.getStatusCode());
  }


 @Test
  void  testDeletePerson() throws IOException {
    given()
            .contentType("application/json")
            .body(getPayload("PersonInstance.json"))
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .contentType("application/json")
            .when()
            .delete("/TripPinRESTierService/Persons('lewisblack1')")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(204).getStatusCode());
    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(404).getStatusCode());
  }

  @Test
  void  testDeleteTrip() throws IOException {

    given()
            .contentType("application/json")
            .body(getPayload("PersonInstance.json"))
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .contentType("application/json")
            .body(getPayload("TripInstance.json"))
            .when()
            .post("/TripPinRESTierService/Trips")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .contentType("application/json")
            .when()
            .delete("/TripPinRESTierService/Trips("  + 1 + ")")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(204).getStatusCode());
    given()
            .when()
            .get("/TripPinRESTierService/Trips(" + 1 + ")")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(404).getStatusCode());
  }

  @Test
  void  testPatchPerson() throws IOException {

    given()
            .contentType("application/json")
            .body(getPayload("PersonInstance.json"))
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .contentType("application/json")
            .body("{\"FirstName\":\"lewis2\",\"LastName\":\"black2\"}")
            .when()
            .patch("/TripPinRESTierService/Persons('lewisblack1')")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode());
    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')/FirstName/$value")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("lewis2"));
  }

  @Test
  void  testPatchPersonBestFriend() throws IOException {
    given()
            .contentType("application/json")
            .body(getPayload("personInstance.json"))
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .contentType("application/json")
            .body(getPayload("PersonInstance2.json"))
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .contentType("application/json")
            .body("{\"Friend\":\"lewisblack2\"}")
            .when()
            .patch("/TripPinRESTierService/Persons('lewisblack1')")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode());
    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')/BestFriend/UserName/$value")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("lewisblack2"));
  }

  @Test
  void  testPatchPersonHomeAddressCode() throws IOException {
    given()
            .contentType("application/json")
            .body(getPayload("PersonInstance.json"))
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());
    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')/HomeAddress/Code/$value")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("123"));
    given()
            .contentType("application/json")
            .body("{\"FirstName\":\"russellbhai\",\"LastName\":\"whyte\",\"HomeAddress\":{\"Code\":5201023}}")
            .when()
            .patch("/TripPinRESTierService/Persons('lewisblack1')")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode());
    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')/HomeAddress/Code/$value")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("5201023"));
  }

  @Test
  void  testPatchPersonHomeAddressCityRegion() throws IOException {
    given()
            .contentType("application/json")
            .body(getPayload("PersonInstance.json"))
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());
    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')/HomeAddress/City/Region/$value")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("ID"));

    given()
            .contentType("application/json")
            .body("{\"FirstName\":\"russellbhai\",\"LastName\":\"whyte\",\"HomeAddress\":{\"Code\":543282,\"City\":{\"Region\":\"China\"}}}")
            .when()
            .patch("/TripPinRESTierService/Persons('lewisblack1')")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode());
    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')/HomeAddress/City/Region/$value")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("China"));
  }

  @Test
  void  testNavigationTrips() throws IOException {
    given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(getPayload("PersonDeepInstanceTrip.json"))
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')/Trips/$count")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("2"));
  }

  @Test
  void  testNavigationFriends() throws IOException {
    given()
            .contentType("application/json")
            .body(getPayload("PersonDeepInstanceFriend.json"))
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')/Friends('lewisblack2')/UserName/$value")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("lewisblack2"));
  }

  @Test
  void  testNavigationBestFriend() throws IOException {
    given()
            .contentType("application/json")
            .body(getPayload("PersonInstance.json"))
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .contentType("application/json")
            .body(getPayload("PersonInstance2BestFriend.json"))
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack2')/BestFriend/UserName/$value")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("lewisblack1"));
  }

  @Test
  public void testSystemFilterOptions() throws IOException {
    given()
            .contentType("application/json")
            .body(getPayload("PersonInstance.json"))
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());
    given()
            .contentType("application/json")
            .body(getPayload("PersonInstance3.json"))
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .contentType("application/json")
            .body(getPayload("TripInstance.json"))
            .when()
            .post("/TripPinRESTierService/Trips")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')/Gender/$value")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("Male"));
    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')/FirstName/$value")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("Lewis"));
    given()
            .when()
            .get("/TripPinRESTierService/Persons?$filter=FirstName eq 'Lewis'")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("value.get(0).UserName",  equalTo("lewisblack1"));

    given()
            .when()
            .get("/TripPinRESTierService/Persons?$filter=contains(HomeAddress/Address, '187SuffolkLn.')")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("value.get(0).UserName",  equalTo("lewisblack1"));
    given()
            .when()
            .get("/TripPinRESTierService/Persons?$filter=Gender eq Trippin.PersonGender'Male'")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("value.get(0).UserName", equalTo("lewisblack1"));
    try {
      given()
              .when()
              .get("/TripPinRESTierService/Persons?$select=FirstName,LastName,UserName,AddressInfo")
              .then()
              .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
              .body("value.get(0).Gender", is(null));
    } catch (NullPointerException ne) {

    }
    given()
            .when()
            .get("/TripPinRESTierService/Persons?$orderby=DateOfBirth desc")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("value.get(0).UserName", equalTo("danzoblack1"));
    given()
            .when()
            .get("/TripPinRESTierService/Persons?$top=1")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("value.get(0).UserName", equalTo("danzoblack1"))
            .body("value.size()", is(1));
    given()
            .when()
            .get("/TripPinRESTierService/Persons?$skip=1")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("value.get(0).UserName", equalTo("lewisblack1"))
            .body("value.size()", is(1));
    given()
            .when()
            .get("/TripPinRESTierService/Persons/$count")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("2"));
    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')?$expand=Trips")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("Trips.get(0).TripId",is(1));
    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')?$expand=Trips($top=1)")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("Trips.get(0).TripId",is(1));
    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')?$expand=Trips($select=TripId,Name)")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("Trips.get(0).TripId",is(1))
            .body("Trips.get(0).size()", is(2));
    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')?$expand=Trips($filter=Name eq 'TripinUS')")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body("Trips.get(0).TripId",is(1))
            .body("Trips.size()", is(1));
  }

    @Test
    public void testLinkPlanItemWithTrip() throws IOException {

        given()
                .contentType("application/json")
                .body(getPayload("TripInstance2.json"))
        .when()
                .post("/TripPinRESTierService/Trips")
        .then()
                .statusCode(HttpStatusCode.CREATED.getStatusCode())
                .body("TripId",is(1));

        given()
                .contentType("application/json")
                .body(getPayload("PlanItemInstance.json"))
        .when()
                .post("/TripPinRESTierService/PlanItems")
        .then()
                .statusCode(HttpStatusCode.CREATED.getStatusCode())
                .body("PlanItemId",is(1));

        given()
                .contentType("application/json").body("{\n" + " \"TripId\":1,\n" + "    \"PlanItemId\" : 1\n" + "}")
        .when()
                .post("/TripPinRESTierService/LinkTripWithPlanItem")
        .then()
                .statusCode(HttpStatusCode.OK.getStatusCode())
                .body("value", is(true));

        //Trip does not exist
        given()
                .contentType("application/json")
                .body("{\n" + " \"TripId\":2,\n" + "    \"PlanItemId\" : 1\n" + "}")
        .when()
                .post("/TripPinRESTierService/LinkTripWithPlanItem")
        .then()
                .statusCode(HttpStatusCode.OK.getStatusCode())
                .body("value", is(false));

        //PlanItem does not exist
        given()
                .contentType("application/json")
                .body("{\n" + " \"TripId\":1,\n" + "    \"PlanItemId\" : 10\n" + "}")
        .when()
                .post("/TripPinRESTierService/LinkTripWithPlanItem")
        .then()
                .statusCode(HttpStatusCode.OK.getStatusCode())
                .body("value", is(false));
    }




  @AfterEach
  void  teardown() {
    jdbcTemplate.execute("DELETE FROM Trippin.TripTag");
    jdbcTemplate.execute("DELETE FROM Trippin.TripPlanItem");
    jdbcTemplate.execute("DELETE FROM Trippin.BasePlanItem");
    jdbcTemplate.execute("DELETE FROM Trippin.Trip");
    jdbcTemplate.execute("DELETE FROM Trippin.PersonEmail");
    jdbcTemplate.execute("DELETE FROM Trippin.PersonAddressInfo");
    jdbcTemplate.execute("DELETE FROM Trippin.PersonFeature");
    jdbcTemplate.execute("DELETE FROM Trippin.PersonFriend");
    jdbcTemplate.execute("DELETE FROM Trippin.Person");
    jdbcTemplate.execute("DELETE FROM Trippin.BasePlanItem");
    jdbcTemplate.execute("DELETE FROM Trippin.Airline");
    jdbcTemplate.execute("DELETE FROM Trippin.Airport");

    RestAssuredMockMvc.reset();
  }
}
