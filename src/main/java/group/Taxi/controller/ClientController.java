package group.Taxi.controller;

import group.Taxi.exception.ResourceNotFoundException;
import group.Taxi.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import group.Taxi.repository.ClientRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    //get client
    @GetMapping("clients")
    public List<Client> getAllClient() {
        return this.clientRepository.findAll();
    }

    // get client by id
    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable(value = "id") Long clientId)
            throws ResourceNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id:: " + clientId));
        return ResponseEntity.ok().body(client);
    }

    //save client
    @PostMapping("clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        return new ResponseEntity<>(clientRepository.save(client), HttpStatus.OK);
    }

    // update client
    @PutMapping("clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable(value = "id") Long clientId,
                                               @Validated @RequestBody Client clientDetail)
            throws ResourceNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id:: " + clientId));

        client.setFirstName(clientDetail.getFirstName());
        client.setLastName(clientDetail.getLastName());
        client.setPhoneNumber(clientDetail.getPhoneNumber());

        return ResponseEntity.ok(this.clientRepository.save(client));
    }

    // delete client
    @DeleteMapping("clients/{id}")
    public Map<String, Boolean> deleteClient(@PathVariable(value = "id") Long clientId)
            throws ResourceNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id:: " + clientId));
        this.clientRepository.delete(client);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
