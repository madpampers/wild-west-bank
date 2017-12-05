package ru.edhunter.wildwestbank.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edhunter.wildwestbank.model.Transaction;
import ru.edhunter.wildwestbank.repository.TransactionRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    private AccountService accountService;

    private TransactionRepository transactionRepository;

    private Logger LOG = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public List<Transaction> getListOfAllTransactions() {
        LOG.info("Getting all transactions...");

        return transactionRepository.findAllByOrderByTimestampDesc();
    }

    public List<Transaction> getIncomeTransactions(String id) {
        LOG.info("Getting income transactions of account, with id:" + id);

        return transactionRepository.findAllByToAccountOrderByTimestampDesc(id);
    }

    public List<Transaction> getOutcomeTransactions(String id) {
        LOG.info("Getting outcome transactions of account, with id:" + id);

        return transactionRepository.findAllByFromAccountOrderByTimestampDesc(id);
    }

    @Transactional
    public Transaction processTransaction(Transaction transaction) {
        LOG.info("Processing transaction");

        transaction.setTimestamp(new Date());

        if (!transaction.getFromAccount().equals("CASH")) {
            accountService.withdrawFromAccount(transaction.getFromAccount(), transaction.getAmount());
        }

        if (!transaction.getToAccount().equals("CASH")) {
            accountService.depositToAccount(transaction.getToAccount(), transaction.getAmount());
        }

        return transactionRepository.save(transaction);
    }

    public Transaction getBlankTransactionWithTimestamp() {
        LOG.info("Creating new blank transaction...");

        Transaction transaction = new Transaction();
        transaction.setTimestamp(new Date());
        return transaction;
    }

    public Transaction getWithdrawTransactionWithTimestamp(String id) {
        LOG.info("Creating new blank withdraw transaction...");

        Transaction withdraw = new Transaction();
        withdraw.setTimestamp(new Date());
        withdraw.setFromAccount(id);
        withdraw.setToAccount("CASH");
        return withdraw;
    }

    public Transaction getDepositTransactionWithTimestamp(String id) {
        LOG.info("Creating new blank deposit transaction...");

        Transaction deposit = new Transaction();
        deposit.setTimestamp(new Date());
        deposit.setFromAccount("CASH");
        deposit.setToAccount(id);
        return deposit;
    }
}
