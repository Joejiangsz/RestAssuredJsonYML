package com.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.api.utils.ConfigReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.api.models.ApiResponse;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    private String baseUrl;
    private Map<String, Object> config;

    @BeforeClass
    public void setup() throws IOException {
        config = ConfigReader.readYamlConfig("src/main/resources/config/testconfig.yml");
        baseUrl = (String) config.get("baseUrl");
        RestAssured.baseURI = baseUrl;
    }


    @Test
    public void testGetWithinResponse() throws IOException {

        Response response = RestAssured.given()
                .headers("Content-Type", "application/json")
                .when()
                .get("/users/1");

                response.then().statusCode(200)
                //import static org.hamcrest.Matchers.equalTo
                .body("id",equalTo(1))
                .body("name",equalTo("Leanne Graham"))
                .body("address.street",equalTo("Kulas Light"));

        // Capture test information in Allure
        Allure.addAttachment("Response", response.asString());
    }

    @Test
    public void testGetResponseSingleTag() throws IOException {

        Response response = RestAssured.given()
                .headers("Content-Type", "application/json")
                .when()
                .get("/users/1");
//        System.out.println("====this is response=======");
//        response.then().log().all();
//        System.out.println("====this is response=======");

        // Validate status code and response body
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("id"), "1");
        Assert.assertEquals(response.jsonPath().getString("name"), "Leanne Graham");
        Assert.assertEquals(response.jsonPath().getString("address.street"), "Kulas Light");

        // Capture test information in Allure
        Allure.addAttachment("Response", response.asString());
    }

    @Test
    public void testGetResponseJson() throws IOException {

        Response response = RestAssured.given()
                .headers("Content-Type", "application/json")
                .when()
                .get("/users/1");
        System.out.println("====this is response=======");
        response.then().log().all();
        System.out.println("====this is response=======");

        //get actual response json
        ObjectMapper mapper=new ObjectMapper();

        JsonNode actualJson= mapper.readTree(response.asString());

        // Load expected response from JSON file
        File responseFile = new File("src/main/resources/testdata/response.json");
        JsonNode expectJson=mapper.readTree(responseFile);

        Assert.assertEquals(actualJson, expectJson);

        // Capture test information in Allure
        Allure.addAttachment("Response", response.asString());
    }

    @Test
    public void testPOSTUser() throws IOException {
        // Load request from JSON file
        File requestFile = new File("src/main/resources/testdata/request.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> requestBody = objectMapper.readValue(requestFile, Map.class);

        Response response = RestAssured.given()
                .headers("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users");

        System.out.println("=========this is response========");
        response.then().log().all();
        System.out.println("=========this is response========");

        // Validate status code and response body
        Assert.assertEquals(response.statusCode(), 201);

        //verify the response as Json file
        ObjectMapper mapper = new ObjectMapper();
        //get the actual response and save as Json node
        JsonNode actualResponseJson = mapper.readTree(response.asString());

        //get the expect response and save as Json node
        File responseFile = new File("src/main/resources/testdata/reponsePost.json");
        JsonNode expectResponseJson = mapper.readTree(responseFile);

        Assert.assertEquals(actualResponseJson,expectResponseJson);


        // Capture test information in Allure
        Allure.addAttachment("Response", response.asString());
    }
}
