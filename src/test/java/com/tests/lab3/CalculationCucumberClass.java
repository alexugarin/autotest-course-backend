package com.tests.lab3;

import com.tests.lab2.entities.Calculation;
import com.tests.lab2.repositories.CalculationRepository;
import com.tests.lab2.rest.CalculationController;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.Delimiter;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebMvc
public class CalculationCucumberClass{
    String BASE_URL = "http://localhost:1212";
    private Response response;
    @Autowired
    @MockBean
    private CalculationRepository calculationRepository;

    @Autowired
    private MockMvc mockMvc;
    ResultActions resultActions;

    @Before(order = 10)
    public void setUp(){
        System.out.println("----Before-----");
        Response removalResponse = RestAssured.given()
                .baseUri(BASE_URL)
                .get("/api/init");
        if (removalResponse.getStatusCode() == 200)
            System.out.println("База данных заполнена");
    }

    @After
    public void cleanUp(){
        System.out.println("----After-----");
        Response removalResponse = RestAssured.given()
                .baseUri(BASE_URL)
                .get("/api/remove/all");
        if (removalResponse.getStatusCode() == 200)
            System.out.println("База данных очищена");
    }

    //Correct sum in 2 system
    @When("^Send a GET request with parameters$")
    public void sendAGETRequestWithParametersTest(DataTable arg) throws Throwable {
        System.out.println("-----When------");
        List<Map<String, String>> table = arg.asMaps(String.class, String.class);
        String a = table.get(0).get("Parameter Value");
        String b = table.get(1).get("Parameter Value");
        String numberSystem = table.get(2).get("Parameter Value");
        String operation = table.get(3).get("Parameter Value");

        //resultActions = mockMvc.perform(get(String.format("/api/calculate?a=%s&b=%s&numberSystem=%s&operation=%s", a, b, numberSystem, operation)));
        String requestPattern = String.format("/api/calculate?a=%s&b=%s&numberSystem=%s&operation=%s", a, b, numberSystem, operation);
        response = RestAssured.given()
                .baseUri(BASE_URL)
                .get(requestPattern);

        System.out.println("Request is sent " + requestPattern);
    }

    @Then("^a new entry is saved in database and result = (\\d+)$")
    public void aNewEntryIsSavedInDatabaseAndResult(int arg) throws Throwable {
        System.out.println("------Then-----");
        System.out.println("Received response: " + response.asString());
        assertEquals((int)Double.parseDouble(response.asString()), arg);
    }

    //Correct sums in 10 system
    @When("send a GET request with a={string}, b={string}, numberSystem={string}, operation={string}")
    public void sendAGETRequestWithABNumberSystemOperation(String a, String b, String numberSystem, String operation) {
        System.out.println("-----When------");
        String requestPattern = String.format("/api/calculate?a=%s&b=%s&numberSystem=%s&operation=%s", a, b, numberSystem, operation);
        response = RestAssured.given()
                .baseUri(BASE_URL)
                .get(requestPattern);
        System.out.println("Request is sent " + requestPattern);
    }

    @Then("get {string}")
    public void get(String arg) {
        System.out.println("------Then-----");
        System.out.println("Received response: " + response.asString());
        assertEquals(Integer.toString((int)Double.parseDouble(response.asString())), arg);
    }

    //Correct sum in 8 system
    @When("send a GET request for system with parameters {list}")
    public void sendAGETRequestForSystemWithParametersAndAndAnd(List<String> args)  {
        System.out.println("-----When---------");
        String a = args.get(0);
        String b = args.get(1);
        String numberSystem = args.get(2);
        String operation = args.get(3);
        String requestPattern = String.format("/api/calculate?a=%s&b=%s&numberSystem=%s&operation=%s", a, b, numberSystem, operation);
        response = RestAssured.given()
                .baseUri(BASE_URL)
                .get(requestPattern);
        System.out.println("Request is sent " + requestPattern);
    }
    @Then("get result = {int}")
    public void getResult(Integer arg) {
        System.out.println("------Then-----");
        System.out.println("Received response: " + response.asString());
        assertEquals(Integer.parseInt(response.asString()), arg);
    }

    //Find entries
    @When("send a find request with parameter startDate={date}, endDate={date}, numberSystem={string}, operation={string}")
    public void sendAFindRequestWithParameterStartDateEndDateNumberSystemOperation(LocalDate startDate, LocalDate endDate, String numberSystem, String operation) {
        System.out.println("--------When--------");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String requestPattern = String.format("/api/history?startDate=%s&endDate=%s&numberSystem=%s&operation=%s", startDate.format(formatter), endDate.format(formatter), numberSystem, operation);
        response = RestAssured.given()
                .baseUri(BASE_URL)
                .get(requestPattern);
        System.out.println("Request is sent " + requestPattern);
    }
    @Then("return a result {calculation}")
    public void returnAResult(Calculation calculation) throws JSONException {
        JSONObject jsonObject = new JSONArray(response.asPrettyString()).getJSONObject(0);
        System.out.println(response.asPrettyString());
        assertEquals(response.statusCode(), 200);
        assertEquals(calculation.getNumberA(), jsonObject.get("numberA"));
        assertEquals(calculation.getSystemA(), jsonObject.get("systemA"));
        assertEquals(calculation.getNumberB(), jsonObject.get("numberB"));
        assertEquals(calculation.getSystemB(), jsonObject.get("systemB"));
        assertEquals(calculation.getOperation(), jsonObject.get("operation"));
    }

    @ParameterType("\\d{4}-\\d{2}-\\d{2}")
    public LocalDate date(String dateString){
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @ParameterType(".*")
    public List<String> list(String inputString){
        return Arrays.asList(inputString.split(" and "));
    }

    @ParameterType(".*")
    public Calculation calculation(String inputString){
        String[] rows = inputString.split(";\\s");
        Map<String, String> table = new HashMap();
        for (int i = 0; i < rows.length; i++){
            String[] parts = rows[i].split("\\s=\\s");
            table.put(parts[0], parts[1]);
        }
        String a = table.get("numberA");
        String systemA = table.get("systemA");
        String b = table.get("numberB");
        String systemB = table.get("systemB");
        String operation = table.get("operation");
        return new Calculation(1, a, systemA, b, systemB, operation, new Timestamp(12132938293L));
    }

}
