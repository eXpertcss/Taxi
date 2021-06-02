package group.Taxi.controller;

import group.Taxi.exception.ResourceNotFoundException;
import group.Taxi.model.Client;
import group.Taxi.model.Dispatcher;
import group.Taxi.model.Driver;
import group.Taxi.model.Order;
import group.Taxi.repository.ClientRepository;
import group.Taxi.repository.DispatcherRepository;
import group.Taxi.repository.DriverRepository;
import group.Taxi.repository.OrderRepository;
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
class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderRepository orderRepository;
    @Mock
    ClientRepository clientRepository;
    @Mock
    DriverRepository driverRepository;
    @Mock
    DispatcherRepository dispatcherRepository;

    @Test
    void getAllOrder() {
        System.out.println("Тест метода возвращения все заказы...");

        Date date1 = Date.valueOf("1950-03-31");
        Date date2 = Date.valueOf("2000-03-31");

        Order order1 = new Order(date1, null, 0.5, 0., 0., null, null);
        Order order2 = new Order(date2, null, 0.5, 0., 0., null, null);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderController.getAllOrder();

        Assert.assertEquals(2, orders.size());
        Assert.assertEquals(order1.getDate(), orders.get(0).getDate());
        Assert.assertEquals(order2.getDate(), orders.get(1).getDate());

    }

    @Test
    void getChart() {
        System.out.println("Тест графика...");

        Date date1 = Date.valueOf("1950-03-31");

        Order order1 = new Order(date1, null, 0.5, 0., 50.01, null, null);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1));

        Map<String, Double> response = orderController.getChart(date1);

        Assert.assertEquals(new Double(50.01), response.get("count"));

    }

    @Test
    void getDeposit() {
        System.out.println("Тест цен...");

        Date date1 = Date.valueOf("1950-03-31");
        Date date2 = Date.valueOf("2000-03-31");

        Order order1 = new Order(date1, null, 0.5, 0., 530., null, null);
        Order order2 = new Order(date2, null, 0.5, 0., 20., null, null);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        Map<String, Double> response = orderController.getDeposit();
        Assert.assertEquals(new Double(530.), response.get("max"));
        Assert.assertEquals(new Double(20.), response.get("min"));
    }

    @Test
    void getOrderById() throws ResourceNotFoundException {

        System.out.println("Тест метода возвращения заказа по id...");

        Date date1 = Date.valueOf("1950-03-31");
        Order order = new Order(date1, null, 0.5, 0., 530., null, null);

        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));

        ResponseEntity<Order> responseEntity = orderController.getOrderById(1L);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(order.getDate(), responseEntity.getBody().getDate());

    }

    @Test
    void createOrder() throws ResourceNotFoundException {
        System.out.println("Тест метода создания заказа...");

        Date date = Date.valueOf("1950-03-31");

        Order order = new Order(date, null, 0.5, 0., 530., null, null);

        Client client = new Client("12345678910", "Eva", "Ave");
        Driver driver = new Driver("Driver2", "LM_Dirver2", "female", null, null, null, null,  null);
        Dispatcher dispatcher = new Dispatcher("Dispatcher1", "LM_Dispatcher1", "male", null, null, null, 0,  null);

        client.setId(1L);
        driver.setId(1L);
        dispatcher.setId(1L);

        order.setClient(client);
        order.setDriver(driver);
        order.setDispatcher(dispatcher);

        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(client));
        when(driverRepository.findById(1L)).thenReturn(java.util.Optional.of(driver));
        when(dispatcherRepository.findById(1L)).thenReturn(java.util.Optional.of(dispatcher));

        ResponseEntity<Order> responseEntity = orderController.createOrder(order);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());


    }

    @Test
    void updateOrder() throws ResourceNotFoundException {


        System.out.println("Тест обновления заказа...");

        Date date1 = Date.valueOf("1950-03-31");
        Date date2 = Date.valueOf("2000-03-31");

        Order orderOld = new Order(date1, null, 0.5, 0., 530., null, null);
        Order orderNew = new Order(date2, null, 0.5, 0., 20., null, null);

        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(orderOld));

        Client client = new Client("12345678910", "Eva", "Ave");
        Driver driver = new Driver("Driver2", "LM_Dirver2", "female", null, null, null, null,  null);
        Dispatcher dispatcher = new Dispatcher("Dispatcher1", "LM_Dispatcher1", "male", null, null, null, 0,  null);

        client.setId(1L);
        driver.setId(1L);
        dispatcher.setId(1L);

        orderOld.setClient(client);
        orderOld.setDriver(driver);
        orderOld.setDispatcher(dispatcher);

        orderNew.setClient(client);
        orderNew.setDriver(driver);
        orderNew.setDispatcher(dispatcher);

        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(client));
        when(driverRepository.findById(1L)).thenReturn(java.util.Optional.of(driver));
        when(dispatcherRepository.findById(1L)).thenReturn(java.util.Optional.of(dispatcher));

        ResponseEntity<Order> responseEntity = orderController.updateOrder(1L, orderNew);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    void deleteOrder() throws ResourceNotFoundException {


        System.out.println("Тест удаления заказа...");

        Date date = Date.valueOf("1950-03-31");
        Order order = new Order(date, null, 0.5, 0., 530., null, null);

        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));

        Map<String, Boolean> response = orderController.deleteOrder(1L);

        Assert.assertEquals(Boolean.TRUE, response.get("deleted"));

    }
}