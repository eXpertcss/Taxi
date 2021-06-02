package group.Taxi.controller;

import group.Taxi.exception.ResourceNotFoundException;
import group.Taxi.model.Dispatcher;
import group.Taxi.repository.DispatcherRepository;
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
class DispatcherControllerTest {

    @InjectMocks
    DispatcherController dispatcherController;

    @Mock
    DispatcherRepository dispatcherRepository;

    @Test
    void getAllDispatcher() {

        System.out.println("Тест метода возвращения всех диспетчеров...");


        Dispatcher dispatcher1 = new Dispatcher("Dispatcher1", "LM_Dispatcher1", "male", null, null, null, 0,  null);
        Dispatcher dispatcher2 = new Dispatcher("Dispatcher2", "LM_Dispatcher2", "female", null, null, null, 0,  null);

        when(dispatcherRepository.findAll()).thenReturn(Arrays.asList(dispatcher1, dispatcher2));

        List<Dispatcher> dispatchers = dispatcherController.getAllDispatcher();

        Assert.assertEquals(2, dispatchers.size());
        Assert.assertEquals(dispatcher1.getFirstName(), dispatchers.get(0).getFirstName());
        Assert.assertEquals(dispatcher2.getFirstName(), dispatchers.get(1).getFirstName());

    }

    @Test
    void getDispatcherById() throws ResourceNotFoundException {

        System.out.println("Тест метода возвращения диспетчера по id...");
        Dispatcher dispatcher = new Dispatcher("Dispatcher2", "LM_Dispatcher2", "female", null, null, null, 0,  null);

        when(dispatcherRepository.findById(1L)).thenReturn(java.util.Optional.of(dispatcher));

        ResponseEntity<Dispatcher> responseEntity = dispatcherController.getDispatcherById(1L);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(dispatcher.getFirstName(), responseEntity.getBody().getFirstName());
    }

    @Test
    void createDispatcher() {
                System.out.println("Тест метода создания диспетчера...");

        Dispatcher dispatcher = new Dispatcher("Dispatcher2", "LM_Dispatcher2", "female", null, null, null, 0,  null);

        ResponseEntity<Dispatcher> responseEntity = dispatcherController.createDispatcher(dispatcher);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void updateDispatcher() throws ResourceNotFoundException {

        System.out.println("Тест обновления диспетчера...");

        Dispatcher dispatcherOld = new Dispatcher("Dispatcher1", "LM_Dispatcher1", "male", null, null, null, 0,  null);
        Dispatcher dispatcherNew = new Dispatcher("Dispatcher2", "LM_Dispatcher2", "female", null, null, null, 0,  null);

        when(dispatcherRepository.findById(1L)).thenReturn(java.util.Optional.of(dispatcherOld));

        ResponseEntity<Dispatcher> responseEntity = dispatcherController.updateDispatcher(1L, dispatcherNew);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteDispatcher() throws ResourceNotFoundException {

        System.out.println("Тест удаления диспетчера...");

        Dispatcher dispatcher = new Dispatcher("Dispatcher2", "LM_Dispatcher2", "female", null, null, null, 0,  null);

        when(dispatcherRepository.findById(1L)).thenReturn(java.util.Optional.of(dispatcher));

        Map<String, Boolean> response = dispatcherController.deleteDispatcher(1L);

        Assert.assertEquals(Boolean.TRUE, response.get("deleted"));
    }
}