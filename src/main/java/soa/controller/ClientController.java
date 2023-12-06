package soa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soa.entities.Client;
import soa.repository.ClientRepository;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/clients")
public class ClientController {


    private final ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Client> getClientById(@PathVariable Long id) {
        return clientRepository.findById(id);
    }

    @PostMapping
    public Client saveClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        try {
            Optional<Client> optionalClient = clientRepository.findById(id);
            if (optionalClient.isPresent()) {
                clientRepository.deleteById(id);
                return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error deleting client", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if (optionalClient.isPresent()) {
            Client existingClient = optionalClient.get();

            // Mettez à jour les propriétés du client existant avec les nouvelles valeurs
            existingClient.setNom(updatedClient.getNom());
            existingClient.setPrenom(updatedClient.getPrenom());
            existingClient.setNumero(updatedClient.getNumero());
            existingClient.setAdresse(updatedClient.getAdresse());
            existingClient.setDateInscription(updatedClient.getDateInscription());

            // Enregistrez les modifications dans la base de données
            clientRepository.save(existingClient);

            return new ResponseEntity<>(existingClient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Client>> getClientsByNom(@RequestParam String nom) {
        List<Client> matchingClients = clientRepository.findByNom(nom);

        if (!matchingClients.isEmpty()) {
            return new ResponseEntity<>(matchingClients, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/chiffreAffaires")
    public List<Client> getClientChiffreAffaires() {
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            client.updateChiffreAffaires(); // Ensure chiffreAffaires is up-to-date
        }
        return clients;
    }


    @GetMapping("/clientsWithChiffreAffaires")
    public List<Client> getClientsWithChiffreAffaires() {
        List<Client> clients = clientRepository.findAll();

        for (Client client : clients) {
            client.updateChiffreAffaires();
            client.updateActifStatus();
        }

        return clients;
    }



    @GetMapping("/searchWithChiffreAffaires")
    public ResponseEntity<List<Client>> searchClientsWithChiffreAffaires(@RequestParam String nom) {
        List<Client> matchingClients = clientRepository.findByNom(nom);

        if (!matchingClients.isEmpty()) {
            // Update Chiffre d'Affaires for the matching clients
            for (Client client : matchingClients) {
                client.updateChiffreAffaires();
            }
            return new ResponseEntity<>(matchingClients, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
