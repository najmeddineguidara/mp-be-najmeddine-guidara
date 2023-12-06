package soa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import soa.entities.Client;
import soa.entities.Facture;
import soa.repository.ClientRepository;
import soa.repository.FactureRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SpringJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner anotherDemo(ClientRepository clientRepository, FactureRepository factureRepository) {
        return (args) -> {
            // Add more examples of data here for Client and Facture
            Date currentDate = new Date();
            Date threeDaysAgo = new Date(System.currentTimeMillis() - 259200000);
            Date fourDaysAgo = new Date(System.currentTimeMillis() - 345600000);
            // Créer une date correspondant à cinq jours avant la date actuelle
            Date fiveDaysAgo = new Date(120, 0, 1); // 1 janvier 2020


            List<Client> otherClients = List.of(
                    new Client("John", "Doe", "12345678", "123 Main St", currentDate),
                    new Client("Jane", "Smith", "87654321", "456 Oak St", threeDaysAgo),
                    new Client("Bob", "Johnson", "55555555", "789 Pine St", fourDaysAgo),
                    new Client("mohamed", "Johnson", "55555555", "789 Pine St", fourDaysAgo),
                    new Client("ali", "Johnson", "55555555", "789 Pine St", fiveDaysAgo)
            );

            // Save the clients to the database
            clientRepository.saveAll(otherClients);

            List<Facture> otherFactures = List.of(
                    new Facture("F004", BigDecimal.valueOf(500.0), new Date(), otherClients.get(0)),
                    new Facture("F004", BigDecimal.valueOf(500.0), new Date(), otherClients.get(0)),
                    new Facture("F005", BigDecimal.valueOf(750.0), new Date(), otherClients.get(1)),
                    new Facture("F005", BigDecimal.valueOf(750.0), new Date(), otherClients.get(1)),
                    new Facture("F006", BigDecimal.valueOf(300.0), new Date(), otherClients.get(2)),
                    new Facture("F006", BigDecimal.valueOf(300.0),fiveDaysAgo, otherClients.get(4))
            );

            // Save the invoices to the database
            factureRepository.saveAll(otherFactures);

            // Update chiffreAffaires for each client
            for (Client client : otherClients) {
                client.updateChiffreAffaires();
                clientRepository.save(client);
            }
        };
    }
}
