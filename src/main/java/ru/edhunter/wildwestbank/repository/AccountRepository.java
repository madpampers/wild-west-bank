package ru.edhunter.wildwestbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edhunter.wildwestbank.model.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    List<Account> findAccountsByClient_Id(String id);
}
