package com.tests.lab2;

import com.tests.lab2.entities.Calculation;
import com.tests.lab2.repositories.CalculationRepository;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectWriter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ComponentScan(basePackages = "com.tests.lab2")
@AutoConfigureMockMvc
class Lab2ApplicationTests extends AuditVizualizationBaseTest{
    @Autowired
    CalculationRepository calculationRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCalculateMult() throws Exception {
        mockMvc.perform(get("/api/calculate?a=5&b=2&numberSystem=10&operation=*"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"))
                .andDo(print());
    }

    @Test
    void testCalculatePlus() throws Exception {
        mockMvc.perform(get("/api/calculate?a=5&b=2&numberSystem=10&operation=+"))
                .andExpect(status().isOk())
                .andExpect(content().string("7"))
                .andDo(print());
    }

    @Test
    void testCalculateMinus() throws Exception {
        mockMvc.perform(get("/api/calculate?a=5&b=2&numberSystem=10&operation=-"))
                .andExpect(status().isOk())
                .andExpect(content().string("3"))
                .andDo(print());
    }

    @Test
    void testCalculateDiv() throws Exception {
        mockMvc.perform(get("/api/calculate?a=5&b=2&numberSystem=10&operation=/"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"))
                .andDo(print());
    }

    @Test
    @FlywayTest(locationsForMigrate = "/insert")
    void testFindAll() throws Exception {
        List<Calculation> calculations = calculationRepository.findAll();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(calculations);

        String expectedJson = Files.readString(Paths.get("C:\\javaTests\\lab3tests\\lab2\\lab2\\src\\test\\resources\\expected.json"));
        assertEquals(expectedJson, json);
    }

    @Test
    @FlywayTest(locationsForMigrate = "/insert")
    void testFindByParams() throws Exception {
        List<Calculation> calculations = calculationRepository.findByParams("+", "10", convertDate("2023-12-15"), convertDate("2023-12-20"));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(calculations);

        String expectedJson = Files.readString(Paths.get("C:\\javaTests\\lab3tests\\lab2\\lab2\\src\\test\\resources\\expected-params.json"));
        assertEquals(expectedJson, json);
    }

    private Timestamp convertDate(String date)
    {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate localDateTime = LocalDate.from(formatter.parse(date));

        return Timestamp.valueOf(localDateTime.atStartOfDay());
    }
}

