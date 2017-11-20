package ru.edhunter.wildwestbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edhunter.wildwestbank.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
