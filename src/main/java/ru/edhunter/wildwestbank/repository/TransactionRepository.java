package ru.edhunter.wildwestbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edhunter.wildwestbank.model.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findAllByFromAccountOrderByTimestampDesc(String id);

    List<Transaction> findAllByToAccountOrderByTimestampDesc(String id);

    List<Transaction> findAllByOrderByTimestampDesc();
}
