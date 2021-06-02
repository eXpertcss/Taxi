package group.Taxi.controller;

import group.Taxi.exception.ResourceNotFoundException;
import group.Taxi.model.Car;
import group.Taxi.model.Client;
import group.Taxi.repository.CarRepository;
import group.Taxi.repository.ClientRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestContext.class})
@SpringBootTest
class CarControllerTest {

    @InjectMocks
    CarController carController;

    @Mock
    CarRepository carRepository;

    @Test
    void getAllCar() {

        System.out.println("Тест метода возвращения всех машин...");
        Car car1 = new Car("12345678910", "1234", "model1", null, null, null);
        Car car2 = new Car("12345678911", "4312", "model2", null, null, null);
        when(carController.getAllCar()).thenReturn(Arrays.asList(car1, car2));

        List<Car> cars = carController.getAllCar();

        Assert.assertEquals(2, cars.size());
        Assert.assertEquals(car1.getCarVIN(), cars.get(0).getCarVIN());
        Assert.assertEquals(car2.getCarVIN(), cars.get(1).getCarVIN());

    }

    @Test
    void getCarById() throws ResourceNotFoundException {

        System.out.println("Тест метода возвращения машин по id...");
        Car car1 = new Car("12345678910", "1234", "model1", null, null, null);

        when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(car1));

        ResponseEntity<Car> responseEntity = carController.getCarById(1L);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(car1.getCarVIN(), responseEntity.getBody().getCarVIN());

    }

    @Test
    void createCar() {
        System.out.println("Тест метода создания машин...");

        Car car = new Car("12345678910", "1234", "model1", null, null, null);

        ResponseEntity<Car> responseEntity = carController.createCar(car);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void updateCar() throws ResourceNotFoundException {
        System.out.println("Тест обновления машины...");

        Car carOld = new Car("12345678910", "1234", "model1", null, null, null);
        Car carNew = new Car("12345678911", "4312", "model2", null, null, null);

        when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(carOld));

        ResponseEntity<Car> responseEntity = carController.updateCar(1L, carNew);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteCar() throws ResourceNotFoundException {
        System.out.println("Тест удаления машины...");

        Car car = new Car("12345678910", "1234", "model1", null, null, null);

        when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(car));

        Map<String, Boolean> response = carController.deleteCar(1L);

        Assert.assertEquals(Boolean.TRUE, response.get("deleted"));
    }
}