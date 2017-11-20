package ru.edhunter.wildwestbank.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edhunter.wildwestbank.model.Account;
import ru.edhunter.wildwestbank.model.Client;
import ru.edhunter.wildwestbank.repository.AccountRepository;

import java.util.Date;
import java.util.List;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    private Logger LOG = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getListOfAccountsByClientId(String id) {
        LOG.info("Getting all client's accounts with given client id:" + id);

        return accountRepository.findAccountsByClient_Id(id);
    }

    public Account getAccountById(String id) {
        LOG.info("Getting the account with given account id:" + id);

        return accountRepository.findOne(id);
    }

    public void saveAccount(Account account) {
        try {
            LOG.info("Saving account...");
            accountRepository.save(account);
        } catch (Exception e) {
            LOG.error("An error occurred during account saving: " + e.getMessage());
        }
    }

    public void deleteAccount(String id) {
        LOG.info("Deleting account with id: " + id);
        try {
            accountRepository.delete(id);
        } catch (Exception e) {
            LOG.error("Account to delete not found: " + e.getMessage());
        }
    }

    public Account getBlankAccountWithCreateDate(Client id) {
        LOG.info("Making blank account, with creation date...");

        Account account = new Account();
        account.setClient(id);
        account.setCreateDate(new Date());

        return account;
    }

    void withdrawFromAccount(String id, Double amount) {
        LOG.info("Making withdraw to account with id: " + id);

        Account account = accountRepository.findOne(id);

        if (account != null) {
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                accountRepository.save(account);
            } else {
                throw new IllegalArgumentException("Insufficient funds on account with id: " + id);
            }
        } else {
            throw new NullPointerException("Account to withdraw not found, id: " + id);
        }
    }

    void depositToAccount(String id, Double amount) {
        LOG.info("Making deposit from account with id: " + id);

        checkAmountForValid(amount);

        Account account = accountRepository.findOne(id);

        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);
        } else {
            throw new NullPointerException("Account to deposit not found, id: " + id);
        }
    }

    private void checkAmountForValid(Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid amount, must be positive.");
        }
    }
}
