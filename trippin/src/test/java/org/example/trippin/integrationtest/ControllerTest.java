package org.example.trippin.integrationtest;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.example.trippin.SpringApp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ControllerTest {

  @Autowired
  private WebApplicationContext context;
  @LocalServerPort
  private int port;

  @BeforeEach
  void setup() {
    RestAssuredMockMvc.webAppContextSetup(context);
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
  void  testCreateInstance() {
    given()
        .contentType("application/json")
        .body("{\"UserName\":\"lewisblack1\",\"FirstName\":\"Lewis\",\"LastName\":\"Black\",\"Income\":1234.543,\"DateOfBirth\":\"2014-01-01\",\"Gender\":\"Male\",\"Emails\":[\"lewisblack@example.com\"],\"Age\":56,\"AddressInfo\":[{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"}}],\"FavoriteFeature\":\"Feature1\",\"Features\":[\"Feature1\",\"Feature2\",\"Feature3\"],\"HomeAddress\":{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"},\"Code\":123}}")
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
    testDeletePerson("lewisblack1");
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
  void  testCreateInstanceDeepWithTrips() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("{\"Income\":126.54125,\"AddressInfo\":[{\"Address\":\"Marathalibangalore520100\",\"Code\":520100,\"City\":{\"CountryRegion\":\"India\",\"Name\":\"Bengaluru\",\"Region\":\"Karnatka\"}}],\"LastName\":\"Whyte\",\"Gender\":\"Male\",\"Photo\":null,\"DateOfBirth\":\"2014-05-01\",\"UserName\":\"lewisblack1\",\"Emails\":[\"russell.whyte2@gmail.com\"],\"Features\":[\"Feature2\",\"Feature3\"],\"FirstName\":\"Russell\",\"FavoriteFeature\":\"Feature3\",\"MiddleName\":null,\"Age\":25,\"HomeAddress\":{\"Address\":\"Marathalibangalore520100\",\"Code\":520100,\"City\":{\"CountryRegion\":\"India\",\"Name\":\"Bengaluru\",\"Region\":\"Karnatka\"}},\"Trips\":[{\"Name\":\"TripinFRANCE\",\"StartsAt\":\"2014-01-01T07:15:00Z\",\"Description\":\"TripfromParistoNewYorkCity\",\"TripId\":3,\"UserName\":\"lewisblack1\",\"EndsAt\":\"2014-02-01T07:15:00Z\",\"Budget\":null},{\"Name\":\"TripinUS\",\"StartsAt\":\"2014-01-01T07:15:00Z\",\"Description\":\"TripfromSanFranciscotoNewYorkCity\",\"TripId\":4,\"UserName\":\"lewisblack1\",\"EndsAt\":\"2014-02-01T07:15:00Z\",\"Budget\":null}]}")
        .when()
        .post("/TripPinRESTierService/Persons")
        .then()
        .statusCode(HttpStatusCode.CREATED.getStatusCode());
    given()
        .accept(ContentType.JSON)
        .when()
        .get("/TripPinRESTierService/Trips(1)")
        .then()
        .statusCode(HttpStatusCode.OK.getStatusCode());
    testDeletePerson("lewisblack1");
    testDeleteTrip(1);
    testDeleteTrip(2);
  }

  @Test
  void  testCreateInstanceDeepWithFriends() {
    given()
            .contentType("application/json")
            .body("{\"Income\":126.54125,\"AddressInfo\":[{\"Address\":\"Marathalibangalore520100\",\"Code\":520100,\"City\":{\"CountryRegion\":\"India\",\"Name\":\"Bengaluru\",\"Region\":\"Karnatka\"}}],\"LastName\":\"Whyte\",\"Gender\":\"Male\",\"Photo\":null,\"DateOfBirth\":\"2014-05-01\",\"UserName\":\"lewisblack1\",\"Emails\":[\"russell.whyte2@gmail.com\"],\"Features\":[\"Feature2\",\"Feature3\"],\"FirstName\":\"Russell\",\"FavoriteFeature\":\"Feature3\",\"MiddleName\":null,\"Age\":25,\"HomeAddress\":{\"Address\":\"Marathalibangalore520100\",\"Code\":520100,\"City\":{\"CountryRegion\":\"India\",\"Name\":\"Bengaluru\",\"Region\":\"Karnatka\"}},\"Friends\":[{\"Income\":126.54125,\"AddressInfo\":[{\"Address\":\"Marathalibangalore520100\",\"Code\":520100,\"City\":{\"CountryRegion\":\"India\",\"Name\":\"Bengaluru\",\"Region\":\"Karnatka\"}}],\"LastName\":\"Whyte\",\"Gender\":\"Male\",\"Photo\":null,\"DateOfBirth\":\"2014-05-01\",\"UserName\":\"lewisblack2\",\"Emails\":[\"russell.whyte2@gmail.com\"],\"Features\":[\"Feature2\",\"Feature3\"],\"FirstName\":\"Russell\",\"FavoriteFeature\":\"Feature3\",\"MiddleName\":null,\"Age\":25,\"HomeAddress\":{\"Address\":\"Marathalibangalore520100\",\"Code\":520100,\"City\":{\"CountryRegion\":\"India\",\"Name\":\"Bengaluru\",\"Region\":\"Karnatka\"}}}]}")
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
    testDeletePerson("lewisblack1");
    testDeletePerson("lewisblack2");
  }


  void  testDeletePerson(String userName) {
    given()
            .contentType("application/json")
            .when()
            .delete("/TripPinRESTierService/Persons('" + userName + "')")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(204).getStatusCode());
    given()
            .when()
            .get("/TripPinRESTierService/Persons('" + userName + "')")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(404).getStatusCode());
  }

  void  testDeleteTrip(Integer tripId) {

    given()
            .contentType("application/json")
            .when()
            .delete("/TripPinRESTierService/Trips("  + tripId + ")")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(204).getStatusCode());
    given()
            .when()
            .get("/TripPinRESTierService/Trips(" + tripId + ")")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(404).getStatusCode());
  }

  @Test
  void  testPatchPerson() {

    given()
            .contentType("application/json")
            .body("{\"UserName\":\"lewisblack1\",\"FirstName\":\"Lewis\",\"LastName\":\"Black\",\"Income\":1234.543,\"DateOfBirth\":\"2014-01-01\",\"Gender\":\"Male\",\"Emails\":[\"lewisblack@example.com\"],\"Age\":56,\"AddressInfo\":[{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"}}],\"FavoriteFeature\":\"Feature1\",\"Features\":[\"Feature1\",\"Feature2\",\"Feature3\"],\"HomeAddress\":{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"},\"Code\":123}}")
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
    testDeletePerson("lewisblack1");

  }

  @Test
  void  testPatchPersonBestFriend() {
    given()
            .contentType("application/json")
            .body("{\"UserName\":\"lewisblack1\",\"FirstName\":\"Lewis\",\"LastName\":\"Black\",\"Income\":1234.543,\"DateOfBirth\":\"2014-01-01\",\"Gender\":\"Male\",\"Emails\":[\"lewisblack@example.com\"],\"Age\":56,\"AddressInfo\":[{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"}}],\"FavoriteFeature\":\"Feature1\",\"Features\":[\"Feature1\",\"Feature2\",\"Feature3\"],\"HomeAddress\":{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"},\"Code\":123}}")
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .contentType("application/json")
            .body("{\"UserName\":\"lewisblack2\",\"FirstName\":\"Lewis\",\"LastName\":\"Black\",\"Income\":1234.543,\"DateOfBirth\":\"2014-01-01\",\"Gender\":\"Male\",\"Emails\":[\"lewisblack@example.com\"],\"Age\":56,\"AddressInfo\":[{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"}}],\"FavoriteFeature\":\"Feature1\",\"Features\":[\"Feature1\",\"Feature2\",\"Feature3\"],\"HomeAddress\":{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"},\"Code\":123}}")
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
    testDeletePerson("lewisblack1");
    testDeletePerson("lewisblack2");
  }

  @Test
  void  testPatchPersonHomeAddressCode() {
    given()
            .contentType("application/json")
            .body("{\"UserName\":\"lewisblack1\",\"FirstName\":\"Lewis\",\"LastName\":\"Black\",\"Income\":1234.543,\"DateOfBirth\":\"2014-01-01\",\"Gender\":\"Male\",\"Emails\":[\"lewisblack@example.com\"],\"Age\":56,\"AddressInfo\":[{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"}}],\"FavoriteFeature\":\"Feature1\",\"Features\":[\"Feature1\",\"Feature2\",\"Feature3\"],\"HomeAddress\":{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"},\"Code\":123}}")
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
    testDeletePerson("lewisblack1");
  }

  @Test
  void  testPatchPersonHomeAddressCityRegion() {
    given()
            .contentType("application/json")
            .body("{\"UserName\":\"lewisblack1\",\"FirstName\":\"Lewis\",\"LastName\":\"Black\",\"Income\":1234.543,\"DateOfBirth\":\"2014-01-01\",\"Gender\":\"Male\",\"Emails\":[\"lewisblack@example.com\"],\"Age\":56,\"AddressInfo\":[{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"}}],\"FavoriteFeature\":\"Feature1\",\"Features\":[\"Feature1\",\"Feature2\",\"Feature3\"],\"HomeAddress\":{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"},\"Code\":123}}")
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
    testDeletePerson("lewisblack1");
  }

  @Test
  void  testNavigationTrips() {
    given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body("{\"Income\":126.54125,\"AddressInfo\":[{\"Address\":\"Marathalibangalore520100\",\"Code\":520100,\"City\":{\"CountryRegion\":\"India\",\"Name\":\"Bengaluru\",\"Region\":\"Karnatka\"}}],\"LastName\":\"Whyte\",\"Gender\":\"Male\",\"Photo\":null,\"DateOfBirth\":\"2014-05-01\",\"UserName\":\"lewisblack1\",\"Emails\":[\"russell.whyte2@gmail.com\"],\"Features\":[\"Feature2\",\"Feature3\"],\"FirstName\":\"Russell\",\"FavoriteFeature\":\"Feature3\",\"MiddleName\":null,\"Age\":25,\"HomeAddress\":{\"Address\":\"Marathalibangalore520100\",\"Code\":520100,\"City\":{\"CountryRegion\":\"India\",\"Name\":\"Bengaluru\",\"Region\":\"Karnatka\"}},\"Trips\":[{\"Name\":\"TripinFRANCE\",\"StartsAt\":\"2014-01-01T07:15:00Z\",\"Description\":\"TripfromParistoNewYorkCity\",\"TripId\":3,\"UserName\":\"lewisblack1\",\"EndsAt\":\"2014-02-01T07:15:00Z\",\"Budget\":null},{\"Name\":\"TripinUS\",\"StartsAt\":\"2014-01-01T07:15:00Z\",\"Description\":\"TripfromSanFranciscotoNewYorkCity\",\"TripId\":4,\"UserName\":\"lewisblack1\",\"EndsAt\":\"2014-02-01T07:15:00Z\",\"Budget\":null}]}")
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')/Trips(1)/TripId/$value")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("1"));
    testDeleteTrip(1);
    testDeleteTrip(2);
  }

  @Test
  void  testNavigationFriends() {
    given()
            .contentType("application/json")
            .body("{\"Income\":126.54125,\"AddressInfo\":[{\"Address\":\"Marathalibangalore520100\",\"Code\":520100,\"City\":{\"CountryRegion\":\"India\",\"Name\":\"Bengaluru\",\"Region\":\"Karnatka\"}}],\"LastName\":\"Whyte\",\"Gender\":\"Male\",\"Photo\":null,\"DateOfBirth\":\"2014-05-01\",\"UserName\":\"lewisblack1\",\"Emails\":[\"russell.whyte2@gmail.com\"],\"Features\":[\"Feature2\",\"Feature3\"],\"FirstName\":\"Russell\",\"FavoriteFeature\":\"Feature3\",\"MiddleName\":null,\"Age\":25,\"HomeAddress\":{\"Address\":\"Marathalibangalore520100\",\"Code\":520100,\"City\":{\"CountryRegion\":\"India\",\"Name\":\"Bengaluru\",\"Region\":\"Karnatka\"}},\"Friends\":[{\"Income\":126.54125,\"AddressInfo\":[{\"Address\":\"Marathalibangalore520100\",\"Code\":520100,\"City\":{\"CountryRegion\":\"India\",\"Name\":\"Bengaluru\",\"Region\":\"Karnatka\"}}],\"LastName\":\"Whyte\",\"Gender\":\"Male\",\"Photo\":null,\"DateOfBirth\":\"2014-05-01\",\"UserName\":\"lewisblack2\",\"Emails\":[\"russell.whyte2@gmail.com\"],\"Features\":[\"Feature2\",\"Feature3\"],\"FirstName\":\"Russell\",\"FavoriteFeature\":\"Feature3\",\"MiddleName\":null,\"Age\":25,\"HomeAddress\":{\"Address\":\"Marathalibangalore520100\",\"Code\":520100,\"City\":{\"CountryRegion\":\"India\",\"Name\":\"Bengaluru\",\"Region\":\"Karnatka\"}}}]}")
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
    testDeletePerson("lewisblack1");
    testDeletePerson("lewisblack2");
  }

  @Test
  void  testNavigationBestFriend() {
    given()
            .contentType("application/json")
            .body("{\"UserName\":\"lewisblack1\",\"FirstName\":\"Lewis\",\"LastName\":\"Black\",\"Income\":1234.543,\"DateOfBirth\":\"2014-01-01\",\"Gender\":\"Male\",\"Emails\":[\"lewisblack@example.com\"],\"Age\":56,\"AddressInfo\":[{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"}}],\"FavoriteFeature\":\"Feature1\",\"Features\":[\"Feature1\",\"Feature2\",\"Feature3\"],\"HomeAddress\":{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"},\"Code\":123}}")
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .contentType("application/json")
            .body("{\"UserName\":\"lewisblack2\",\"FirstName\":\"Lewis\",\"LastName\":\"Black\",\"Income\":1234.543,\"DateOfBirth\":\"2014-01-01\",\"Gender\":\"Male\",\"Emails\":[\"lewisblack@example.com\"],\"Age\":56,\"AddressInfo\":[{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"}}],\"FavoriteFeature\":\"Feature1\",\"Features\":[\"Feature1\",\"Feature2\",\"Feature3\"],\"HomeAddress\":{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"},\"Code\":123},\"Friend\":\"lewisblack1\"}")
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
    testDeletePerson("lewisblack2");
    testDeletePerson("lewisblack1");

  }

  @Test
  public void testSystemFilterOptions() {
    given()
            .contentType("application/json")
            .body("{\"UserName\":\"lewisblack1\",\"FirstName\":\"Lewis\",\"LastName\":\"Black\",\"Income\":1234.543,\"DateOfBirth\":\"2015-01-01\",\"Gender\":\"Female\",\"Emails\":[\"lewisblack@example.com\"],\"Age\":56,\"AddressInfo\":[{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"}}],\"FavoriteFeature\":\"Feature1\",\"Features\":[\"Feature1\",\"Feature2\",\"Feature3\"],\"HomeAddress\":{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"},\"Code\":123}}")
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());
    given()
            .contentType("application/json")
            .body("{\"UserName\":\"danzoblack1\",\"FirstName\":\"Danzo\",\"LastName\":\"Black\",\"Income\":1234.543,\"DateOfBirth\":\"2016-01-01\",\"Gender\":\"Male\",\"Emails\":[\"lewisblack@example.com\"],\"Age\":56,\"AddressInfo\":[{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"}}],\"FavoriteFeature\":\"Feature1\",\"Features\":[\"Feature1\",\"Feature2\",\"Feature3\"],\"HomeAddress\":{\"Address\":\"187SuffolkLn.\",\"City\":{\"Name\":\"Boise\",\"CountryRegion\":\"UnitedStates\",\"Region\":\"ID\"},\"Code\":123}}")
            .when()
            .post("/TripPinRESTierService/Persons")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .contentType("application/json")
            .body("{\"Name\":\"TripinUS\",\"StartsAt\":\"2014-01-01T07:15:00Z\",\"Description\":\"TripfromSanFranciscotoNewYorkCity\",\"TripId\":10,\"UserName\":\"lewisblack1\",\"EndsAt\":\"2014-02-01T07:15:00Z\",\"Budget\":null}")
            .when()
            .post("/TripPinRESTierService/Trips")
            .then()
            .statusCode(HttpStatusCode.CREATED.getStatusCode());

    given()
            .when()
            .get("/TripPinRESTierService/Persons('lewisblack1')/Gender/$value")
            .then()
            .statusCode(HttpStatusCode.fromStatusCode(200).getStatusCode())
            .body(is("Female"));
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
            .get("/TripPinRESTierService/Persons?$filter=Gender eq Trippin.PersonGender'Female'")
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
    testDeletePerson("lewisblack1");
    testDeletePerson("danzoblack1");
    testDeleteTrip(1);

  }


  @AfterEach
  void  teardown() {
    RestAssuredMockMvc.reset();
  }
}
