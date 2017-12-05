package ru.edhunter.wildwestbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(path = "/clients/save", method = RequestMethod.POST)
    @ResponseBody
    public Client saveClient(@RequestBody Client client) {
        if (client.getId() == null) return clientService.saveClientNewClient(client);
        return clientService.updateClient(client);
    }

    @RequestMapping(path = "/clients/{clientId}", method = RequestMethod.GET)
    public String getClientInfo(Model model, @PathVariable(value = "clientId") String id) {
        model.addAttribute("client", clientService.getClient(id));
        model.addAttribute("clientAccounts", accountService.getListOfAccountsByClientId(id));
        return "info";
    }

    @RequestMapping(value = "/clients/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteClient(@RequestParam("id") String id) {
        clientService.deleteClient(id);
        return id;
    }

    @RequestMapping(path = "/account/create", method = RequestMethod.POST)
    @ResponseBody
    public String saveAccount(@RequestParam("type") String type,
                              @RequestParam("balance") Double balance,
                              @RequestParam("client") String clientName) {
        accountService.saveAccount(type, balance, clientName);
        return clientName;
    }

    @RequestMapping(path = "/account/close", method = RequestMethod.POST)
    @ResponseBody
    public String closeAccount(@RequestParam(value = "id") String id) {
        accountService.deleteAccount(id);
        return id;
    }

    @RequestMapping(path = "/account/{accountId}", method = RequestMethod.GET)
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

    @RequestMapping(path = "/transactions/process", method = RequestMethod.POST)
    @ResponseBody
    public Transaction processTransaction(@RequestBody Transaction transaction) {
        return transactionService.processTransaction(transaction);
    }
}
