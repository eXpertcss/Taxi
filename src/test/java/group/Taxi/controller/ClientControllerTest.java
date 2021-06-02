package group.Taxi.controller;

import group.Taxi.exception.ResourceNotFoundException;
import group.Taxi.model.Client;
import group.Taxi.repository.ClientRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestContext.class})
//@WebAppConfiguration
@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @InjectMocks
    ClientController clientController;

    @Mock
    ClientRepository clientRepository;


    @Test
    void getAllClient() {
        System.out.println("Тест метода возвращения всех клиентов...");
        Client client1 = new Client("12345678910", "Eva", "Ave");
        Client client2 = new Client("12345678911", "Max", "Xam");
        when(clientController.getAllClient()).thenReturn(Arrays.asList(client1, client2));

        List<Client> clients = clientController.getAllClient();

        Assert.assertEquals(2, clients.size());
        Assert.assertEquals(client1.getFirstName(), clients.get(0).getFirstName());
        Assert.assertEquals(client2.getFirstName(), clients.get(1).getFirstName());
    }

    @Test
    void getClientById() throws ResourceNotFoundException {
        System.out.println("Тест метода возвращения клиента по id...");
        Client client1 = new Client("12345678910", "Eva", "Ave");

        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(client1));

        ResponseEntity<Client> responseEntity = clientController.getClientById(1L);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(client1.getFirstName(), responseEntity.getBody().getFirstName());
    }

    @Test
    void createClient() {
        System.out.println("Тест метода создания клиента...");

        Client client = new Client("12345678910", "Eva", "Ave");

        ResponseEntity<Client> responseEntity = clientController.createClient(client);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void updateClient() throws ResourceNotFoundException {
        System.out.println("Тест обновления клиента...");

        Client clientOld = new Client("12345678910", "Eva", "Ave");
        Client clientNew = new Client("12345678910", "Max", "Xam");

        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(clientOld));

        ResponseEntity<Client> responseEntity = clientController.updateClient(1L, clientNew);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteClient() throws ResourceNotFoundException {
        System.out.println("Тест удаления клиента...");

        Client client = new Client("12345678910", "Eva", "Ave");

        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(client));

        Map<String, Boolean> response = clientController.deleteClient(1L);

        Assert.assertEquals(Boolean.TRUE, response.get("deleted"));

    }
}