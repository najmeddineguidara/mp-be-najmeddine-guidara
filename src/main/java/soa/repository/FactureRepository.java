package soa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soa.entities.Facture;

import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {
    // Recherche des factures par un numéro donné
    Facture findByNumeroFacture(String numeroFacture);

    // Retourne la liste des factures dont le montant est supérieur à une valeur donnée
    List<Facture> findByMontantGreaterThan(double montant);

    // Retourne la liste des factures associées à un client donné
    List<Facture> findByClientNom(String nomClient);


}
