package group.Taxi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import group.Taxi.exception.ResourceNotFoundException;
import group.Taxi.model.Driver;
import group.Taxi.repository.DriverRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class DriverController {

    @Autowired
    private DriverRepository driverRepository;


    //get driver
    @GetMapping("drivers")
    public List<Driver> getAllDriver() {
        return this.driverRepository.findAll();
    }

    // get employees by id
    @GetMapping("/drivers/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable(value = "id") Long driverId)
            throws ResourceNotFoundException {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found for this id:: " + driverId));
        return ResponseEntity.ok().body(driver);
    }

    //save driver
    @PostMapping("drivers")
    public ResponseEntity<Driver> createDriver(@Valid @RequestBody Driver driver) {
        return new ResponseEntity<>(driverRepository.save(driver), HttpStatus.OK);
    }

    // update driver
    @PutMapping("drivers/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable(value = "id") Long driverId,
                                               @Validated @RequestBody Driver driverDetails)
            throws ResourceNotFoundException {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found for this id:: " + driverId));

        driver.setAddress(driverDetails.getAddress());
        driver.setCar(driverDetails.getCar());
        driver.setDriverLicenseIssueDate(driverDetails.getDriverLicenseIssueDate());
        driver.setEmail(driverDetails.getEmail());
        driver.setFirstName(driverDetails.getFirstName());
        driver.setLastName(driverDetails.getLastName());
        driver.setGender(driverDetails.getGender());
        driver.setPhoneNumber(driverDetails.getPhoneNumber());

        return ResponseEntity.ok(this.driverRepository.save(driver));
    }


    // delete employees
    @DeleteMapping("drivers/{id}")
    public Map<String, Boolean> deleteDriver(@PathVariable(value = "id") Long driverId)
            throws ResourceNotFoundException {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found for this id:: " + driverId));
        this.driverRepository.delete(driver);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
