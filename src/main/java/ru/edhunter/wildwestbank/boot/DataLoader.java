//package ru.edhunter.wildwestbank.boot;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import ru.edhunter.wildwestbank.model.Account;
//import ru.edhunter.wildwestbank.model.Client;
//import ru.edhunter.wildwestbank.repository.AccountRepository;
//import ru.edhunter.wildwestbank.repository.ClientRepository;
//import ru.edhunter.wildwestbank.repository.TransactionRepository;
//
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    private ClientRepository clientRepository;
//    private TransactionRepository transactionRepository;
//    private AccountRepository accountRepository;
//
//    @Autowired
//    public void setClientRepository(ClientRepository clientRepository) {
//        this.clientRepository = clientRepository;
//    }
//
//    @Autowired
//    public void setTransactionRepository(TransactionRepository transactionRepository) {
//        this.transactionRepository = transactionRepository;
//    }
//
//    @Autowired
//    public void setAccountRepository(AccountRepository accountRepository) {
//        this.accountRepository = accountRepository;
//    }
//
//    @Override
//    public void run(String... strings) throws Exception {
//        createClients();
//        createAccounts();
//        createTransactions();
//    }
//
//    private void createTransactions() {
//
//    }
//
//    private void createAccounts() {
//        Account account = new Account();
//        account.setCreateDate(new Date().toString());
//        account.setBalance(300d);
//        account.setClient(clientRepository.findByName("Django"));
//        account.setType("USD");
//        accountRepository.save(account);
//    }
//
//    private void createClients() {
//        Client client = new Client();
//        client.setName("Django");
//        client.setAddress("Eldorado");
//        Set<Account> accounts = new HashSet<>();
//        client.setAccounts(accounts);
//        client.setAge(38);
//        clientRepository.save(client);
//    }
//}
