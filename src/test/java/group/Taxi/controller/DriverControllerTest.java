package group.Taxi.controller;

import group.Taxi.exception.ResourceNotFoundException;
import group.Taxi.model.Driver;
import group.Taxi.repository.DriverRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestContext.class})
@SpringBootTest
class DriverControllerTest {

    @InjectMocks
    DriverController driverController;

    @Mock
    DriverRepository driverRepository;

    @Test
    void getAllDriver() {
        System.out.println("Тест метода возвращения всех водителей...");


        Driver driver1 = new Driver("Driver1", "LM_Dirver1", "male", null, null, null, null,  null);
        Driver driver2 = new Driver("Driver2", "LM_Dirver2", "female", null, null, null, null,  null);

        when(driverRepository.findAll()).thenReturn(Arrays.asList(driver1, driver2));

        List<Driver> drivers = driverController.getAllDriver();

        Assert.assertEquals(2, drivers.size());
        Assert.assertEquals(driver1.getFirstName(), drivers.get(0).getFirstName());
        Assert.assertEquals(driver2.getFirstName(), drivers.get(1).getFirstName());

    }

    @Test
    void getDriverById() throws ResourceNotFoundException {

        System.out.println("Тест метода возвращения водителя по id...");
        Driver driver = new Driver("Driver2", "LM_Dirver2", "female", null, null, null, null,  null);

        when(driverRepository.findById(1L)).thenReturn(java.util.Optional.of(driver));

        ResponseEntity<Driver> responseEntity = driverController.getDriverById(1L);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(driver.getFirstName(), responseEntity.getBody().getFirstName());

    }

    @Test
    void createDriver() {
        System.out.println("Тест метода создания водителя...");

        Driver driver = new Driver("Driver2", "LM_Dirver2", "female", null, null, null, null,  null);

        ResponseEntity<Driver> responseEntity = driverController.createDriver(driver);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void updateDriver() throws ResourceNotFoundException {

        System.out.println("Тест обновления водителя...");

        Driver driverOld = new Driver("Driver1", "LM_Dirver1", "male", null, null, null, null,  null);
        Driver driverNew = new Driver("Driver2", "LM_Dirver2", "female", null, null, null, null,  null);

        when(driverRepository.findById(1L)).thenReturn(java.util.Optional.of(driverOld));

        ResponseEntity<Driver> responseEntity = driverController.updateDriver(1L, driverNew);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    void deleteDriver() throws ResourceNotFoundException {

        System.out.println("Тест удаления водителя...");

        Driver driver = new Driver("Driver2", "LM_Dirver2", "female", null, null, null, null,  null);

        when(driverRepository.findById(1L)).thenReturn(java.util.Optional.of(driver));

        Map<String, Boolean> response = driverController.deleteDriver(1L);

        Assert.assertEquals(Boolean.TRUE, response.get("deleted"));

    }
}