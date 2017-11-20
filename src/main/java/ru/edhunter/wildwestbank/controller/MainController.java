package ru.edhunter.wildwestbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.edhunter.wildwestbank.model.*;
import ru.edhunter.wildwestbank.service.AccountService;
import ru.edhunter.wildwestbank.service.ClientService;
import ru.edhunter.wildwestbank.service.TransactionService;

@Controller
public class MainController {

    private AccountService accountService;
    private TransactionService transactionService;
    private ClientService clientService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(path = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(path = "/clients", method = RequestMethod.GET)
    public String getAllClients(Model model) {
        model.addAttribute("clients", clientService.getListOfAllClients());
        return "clients";
    }

    @RequestMapping(path = "/clients/add", method = RequestMethod.GET)
    public String createClient(Model model) {
        model.addAttribute("client", new Client());
        return "add";
    }

    @RequestMapping(path = "/clients/add", method = RequestMethod.POST)
    public String saveClient(Client client) {
        return "redirect:/clients/" + clientService.saveClientAndReturnId(client);
    }

    @RequestMapping(path = "/clients/{clientId}", method = RequestMethod.GET)
    public String getClientInfo(Model model, @PathVariable(value = "clientId") String id) {
        model.addAttribute("client", clientService.getClient(id));
        model.addAttribute("clientAccounts", accountService.getListOfAccountsByClientId(id));
        return "info";
    }

    @RequestMapping(path = "/clients/{clientId}/delete", method = RequestMethod.GET)
    public String deleteClient(@PathVariable(value = "clientId") String id) {
        clientService.deleteClient(id);
        return "redirect:/clients";
    }

    @RequestMapping(path = "/clients/{clientId}/edit", method = RequestMethod.GET)
    public String editClient(Model model, @PathVariable(value = "clientId") String id) {
        model.addAttribute("client", clientService.getClient(id));
        return "edit";
    }

    @RequestMapping(path = "/clients/{clientId}/edit", method = RequestMethod.POST)
    public String editClient(Client client, @PathVariable(value = "clientId") String id) {
        clientService.updateClient(client, id);
        return "redirect:/clients/" + id;
    }

    @RequestMapping(path = "/clients/{clientId}/newaccount", method = RequestMethod.GET)
    public String createAccount(Model model, @PathVariable(value = "clientId") String id) {
        model.addAttribute("account", accountService.getBlankAccountWithCreateDate(clientService.getClient(id)));
        model.addAttribute("id", id);
        return "newaccount";
    }

    @RequestMapping(path = "/clients/{clientId}/newaccount", method = RequestMethod.POST)
    public String saveAccount(Account account, @PathVariable(value = "clientId") String id) {
        accountService.saveAccount(account);
        return "redirect:/clients/" + id;
    }

    @RequestMapping(path = "/clients/{clientId}/{accountId}/close", method = RequestMethod.GET)
    public String closeAccount(@PathVariable(value = "clientId") String clientId,
                               @PathVariable(value = "accountId") String accountId) {
        accountService.deleteAccount(accountId);
        return "redirect:/clients/" + clientId;
    }

    @RequestMapping(path = "/clients/{clientId}/{accountId}/details", method = RequestMethod.GET)
    public String getAccountDetails(Model model, @PathVariable(value = "accountId") String accountId) {
        model.addAttribute("account", accountService.getAccountById(accountId));
        model.addAttribute("incomes", transactionService.getIncomeTransactions(accountId));
        model.addAttribute("outcomes", transactionService.getOutcomeTransactions(accountId));
        return "details";
    }

    @RequestMapping(path = "/transactions", method = RequestMethod.GET)
    public String getAllTransactions(Model model) {
        model.addAttribute("transactions", transactionService.getListOfAllTransactions());
        return "transactions";
    }

    @RequestMapping(path = "/transactions/transfer/", method = RequestMethod.GET)
    public String newTransaction(Model model) {
        model.addAttribute("transfer", transactionService.getBlankTransactionWithTimestamp());
        return "transfer";
    }

    @RequestMapping(path = "/transactions/process", method = RequestMethod.POST)
    public String processTransaction(Transaction transaction) {
        transactionService.processTransaction(transaction);
        return "redirect:/transactions";
    }

    @RequestMapping(path = "/transactions/deposit/{id}", method = RequestMethod.GET)
    public String accountDeposit(Model model, @PathVariable(value = "id") String id) {
        model.addAttribute("deposit", transactionService.getDepositTransactionWithTimestamp(id));
        return "deposit";
    }

    @RequestMapping(path = "/transactions/withdraw/{id}", method = RequestMethod.GET)
    public String accountWithdraw(Model model, @PathVariable(value = "id") String id) {
        model.addAttribute("withdraw", transactionService.getWithdrawTransactionWithTimestamp(id));
        return "withdraw";
    }
}
