package com.tests.lab2.rest;

import com.tests.lab2.Calculator;
import com.tests.lab2.entities.Calculation;
import com.tests.lab2.repositories.CalculationRepository;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CalculationController {

    private final CalculationRepository calculationRepository;

    public CalculationController(CalculationRepository calculationRepository){
        this.calculationRepository = calculationRepository;
    }

    @GetMapping(value ="/calculate", produces="application/json")
    public ResponseEntity<String> calculate(@RequestParam(name="a") String a,
                            @RequestParam(name="b") String b,
                            @RequestParam(name="numberSystem") String numberSystem,
                            @RequestParam(name="operation") String operation)
    {
        Calculation calculation = new Calculation();
        calculation.setNumberA(a);
        calculation.setSystemA(numberSystem);
        calculation.setNumberB(b);
        calculation.setSystemB(numberSystem);
        calculation.setOperation(operation);
        calculation.setCalcDate(new Timestamp(System.currentTimeMillis()));

        Calculation c = calculationRepository.save(calculation);

        Pair<String, List<String>> result = Calculator.calculate(a, b, operation, numberSystem);

        if (result.getValue().isEmpty())
        {
            System.out.println(result.getKey());
            return new ResponseEntity<>(result.getKey(), HttpStatus.OK);
        }
        else
        {
            StringBuilder errors = new StringBuilder();
            for (String error : result.getValue())
            {
                errors.append(error).append("\n");
            }
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value ="/history", produces="application/json")
    public ResponseEntity<List<Calculation>> getHistory(@RequestParam(name="startDate") String startDate,
                                        @RequestParam(name="endDate") String endDate,
                                        @RequestParam(name="operation") String operation,
                                        @RequestParam(name="numberSystem") String numberSystem)
    {
        System.out.println(startDate + " " + endDate + " " + operation + " " + numberSystem);
        if (!validateParams(startDate, endDate, operation, numberSystem))
        {
            return ResponseEntity.badRequest().build();
        }

        Timestamp startDateConverted = convertDate(startDate);
        Timestamp endDateConverted = convertDate(endDate);

        return new ResponseEntity<>(calculationRepository.findByParams(operation, numberSystem, startDateConverted, endDateConverted), HttpStatus.OK);
    }

    @GetMapping(value="/all", produces="application/json")
    public ResponseEntity<List<Calculation>> getAll(){
        return new ResponseEntity<>(calculationRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value="/remove/all", produces = "application/json")
    public ResponseEntity removeAll(){
        calculationRepository.deleteAll();
        return ResponseEntity.ok().build();
    }



    private boolean validateParams(Object... params)
    {
        for (Object param : params)
        {
            if (param == null)
                return false;
        }
        return true;
    }

    private Timestamp convertDate(String date)
    {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate localDateTime = LocalDate.from(formatter.parse(date));

        return Timestamp.valueOf(localDateTime.atStartOfDay());
    }
}
