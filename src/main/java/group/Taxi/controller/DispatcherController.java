package group.Taxi.controller;

import group.Taxi.model.Dispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import group.Taxi.exception.ResourceNotFoundException;
import group.Taxi.repository.DispatcherRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class DispatcherController {

    @Autowired
    private DispatcherRepository dispatcherRepository;

    //get dispatcher
    @GetMapping("dispatchers")
    public List<Dispatcher> getAllDispatcher() {
        return this.dispatcherRepository.findAll();
    }

    // get dispatcher by id
    @GetMapping("/dispatchers/{id}")
    public ResponseEntity<Dispatcher> getDispatcherById(@PathVariable(value = "id") Long dispatcherId)
            throws ResourceNotFoundException {
        Dispatcher dispatcher = dispatcherRepository.findById(dispatcherId)
                .orElseThrow(() -> new ResourceNotFoundException("Dispatcher not found for this id:: " + dispatcherId));
        return ResponseEntity.ok().body(dispatcher);
    }

    //save dispatcher
    @PostMapping("dispatchers")
    public ResponseEntity<Dispatcher> createDispatcher(@Valid @RequestBody Dispatcher dispatcher) {
        return new ResponseEntity<>(dispatcherRepository.save(dispatcher), HttpStatus.OK);
    }

    // update dispatcher
    @PutMapping("dispatchers/{id}")
    public ResponseEntity<Dispatcher> updateDispatcher(@PathVariable(value = "id") Long dispatcherId,
                                                       @Validated @RequestBody Dispatcher dispatcherDetails)
            throws ResourceNotFoundException {
        Dispatcher dispatcher = dispatcherRepository.findById(dispatcherId)
                .orElseThrow(() -> new ResourceNotFoundException("Dispatcher not found for this id:: " + dispatcherId));

        dispatcher.setFirstName(dispatcherDetails.getFirstName());
        dispatcher.setLastName(dispatcherDetails.getLastName());
        dispatcher.setAddress(dispatcherDetails.getAddress());
        dispatcher.setEmail(dispatcherDetails.getEmail());
        dispatcher.setGender(dispatcherDetails.getGender());
        dispatcher.setPhoneNumber(dispatcherDetails.getPhoneNumber());
        dispatcher.setSalary(dispatcherDetails.getSalary());
        dispatcher.setSsn(dispatcherDetails.getSsn());

        return ResponseEntity.ok(this.dispatcherRepository.save(dispatcher));
    }

    // delete dispatcher
    @DeleteMapping("dispatchers/{id}")
    public Map<String, Boolean> deleteDispatcher(@PathVariable(value = "id") Long dispatcherId)
            throws ResourceNotFoundException {
        Dispatcher dispatcher = dispatcherRepository.findById(dispatcherId)
                .orElseThrow(() -> new ResourceNotFoundException("Dispatcher not found for this id:: " + dispatcherId));
        this.dispatcherRepository.delete(dispatcher);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
