package soa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import soa.entities.Client;

import java.util.Date;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByNom(String nom);

}
