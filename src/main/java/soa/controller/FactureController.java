package soa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import soa.entities.Facture;
import soa.repository.FactureRepository;

import java.util.List;

@RestController
@RequestMapping("/factures")
public class FactureController {

    @Autowired
    private FactureRepository factureRepository;

    // Welcome message
    @GetMapping("/index")
    public String accueil() {
        return "Bienvenue au service Web REST 'factures'.....";
    }

    // Get all factures
    @GetMapping(value = "/", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    // Get a facture by specifying its 'id'
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public Facture getFacture(@PathVariable Long id) {
        return factureRepository.findById(id).orElse(null);
    }

    // Delete a facture by 'id' using the 'GET' method
    @GetMapping(value = "/delete/{id}")
    public void deleteFacture(@PathVariable Long id) {
        factureRepository.deleteById(id);
    }

    // Add a facture using the "POST" method
    @PostMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Facture saveFacture(@RequestBody Facture facture) {
        return factureRepository.save(facture);
    }

    // Update a facture using the "PUT" method
    @PutMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Facture updateFacture(@RequestBody Facture facture) {
        return factureRepository.save(facture);
    }

    // Delete a facture using the 'DELETE' method
    @DeleteMapping("/")
    public void deleteFacture(@RequestBody Facture facture) {
        factureRepository.delete(facture);
    }
}
