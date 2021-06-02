package group.Taxi.controller;

import group.Taxi.exception.ResourceNotFoundException;
import group.Taxi.model.Client;
import group.Taxi.model.Dispatcher;
import group.Taxi.model.Driver;
import group.Taxi.model.Order;
import group.Taxi.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import group.Taxi.repository.ClientRepository;
import group.Taxi.repository.DispatcherRepository;
import group.Taxi.repository.OrderRepository;

import javax.validation.Valid;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private DispatcherRepository dispatcherRepository;
    @Autowired
    private ClientRepository clientRepository;

    //get order
    @GetMapping("orders")
    public List<Order> getAllOrder() {
        return this.orderRepository.findAll();
    }

    @GetMapping("orders/chart")
    public Map<String, Double> getChart(@RequestParam(value = "date") Date date) {
        Map<String, Double> response = new HashMap<>();

        List<Order> orders = this.orderRepository.findAll();

        response.put("count", 0.);
        for (Order o : orders) {
            if (date.toString().equals(o.getDateSql().toString())) {
                response.put("count", o.getCost());
            }
        }

        return response;
    }

    @GetMapping("orders/deposit")
    public Map<String, Double> getDeposit() {
        Map<String, Double> response = new HashMap<>();

        List<Order> orders = this.orderRepository.findAll();

        response.put("min", 9999.);
        response.put("max", 0.);
        response.put("sum", 0.);

        for (Order o : orders) {
            if (response.get("min") > o.getCost())
                response.put("min", o.getCost());
            if (response.get("max") < o.getCost())
                response.put("max", o.getCost());
            response.put("sum", response.get("sum")+o.getCost());
        }
        return response;
    }


    // get order by id
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") Long orderId)
            throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id:: " + orderId));
        return ResponseEntity.ok().body(order);
    }

    //save order
    @PostMapping("orders")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody
                                                         Order order) throws ResourceNotFoundException {

        Client client = clientRepository.findById(order.getClient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id:: " + order.getClient().getId()));
        Driver driver = driverRepository.findById(order.getDriver().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found for this id:: " + order.getClient().getId()));
        Dispatcher dispatcher = dispatcherRepository.findById(order.getDispatcher().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Dispatcher not found for this id:: " + order.getClient().getId()));

        order.setClient(client);
        order.setDriver(driver);
        order.setDispatcher(dispatcher);

        return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
    }

    // update order
    @PutMapping("orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable(value = "id") Long orderId,
                                             @Validated @RequestBody Order orderDetails)
            throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id:: " + orderId));

        Driver driver = driverRepository.findById(orderDetails.getDriver().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found for this id:: " + orderDetails.getDriver().getId()));

        Client client = clientRepository.findById(orderDetails.getClient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id:: " + orderDetails.getClient().getId()));

        Dispatcher dispatcher = dispatcherRepository.findById(orderDetails.getDispatcher().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Dispatcher not found for this id:: " + orderDetails.getDispatcher().getId()));

        order.setCost(orderDetails.getCost());
        order.setDate(orderDetails.getDateSql());
        order.setRoadTime(orderDetails.getRoadTime());
        order.setAddressFrom(orderDetails.getAddressFrom());
        order.setAddressTo(orderDetails.getAddressTo());
        order.setTime(orderDetails.getTime());
        order.setDistance(orderDetails.getDistance());

        order.setDispatcher(dispatcher);
        order.setDriver(driver);
        order.setClient(client);

        return ResponseEntity.ok(this.orderRepository.save(order));
    }

    // delete order
    @DeleteMapping("orders/{id}")
    public Map<String, Boolean> deleteOrder(@PathVariable(value = "id") Long orderId)
            throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id:: " + orderId));
        this.orderRepository.delete(order);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
