package ru.edhunter.wildwestbank.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edhunter.wildwestbank.model.Client;
import ru.edhunter.wildwestbank.repository.ClientRepository;

import java.util.List;

@Service
public class ClientService {

    private Logger LOG = LoggerFactory.getLogger(ClientService.class);

    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getListOfAllClients() {
        LOG.info("Getting all clients...");

        return clientRepository.getAllByOrderByNameDesc();
    }

    public Client getClient(String id) {
        LOG.info("Getting client with id: " + id);

        return clientRepository.findOne(id);
    }

    public void deleteClient(String id) {
        LOG.info("Deleting client...");

        try {
            clientRepository.delete(id);
        } catch (Exception e) {
            LOG.error("An error during deleting client" + e.getMessage());
        }
    }

    public Client saveClientNewClient(Client client) {
        LOG.info("Saving client...");

        checkForValidFields(client);

        try {
            return clientRepository.save(client);
        } catch (Exception e) {
            LOG.error("An error occurred during saving client" + e.getMessage());
        }

        return client;
    }

    public Client updateClient(Client clientToUpdate) {
        LOG.info("Updating client...");

        checkForValidFields(clientToUpdate);

        Client foundClient = clientRepository.findOne(clientToUpdate.getId());

        if (foundClient != null) {
            foundClient.setName(clientToUpdate.getName());
            foundClient.setAge(clientToUpdate.getAge());
            foundClient.setAddress(clientToUpdate.getAddress());
            try {
                return clientRepository.save(foundClient);
            } catch (Exception e) {
                LOG.info("An error occurred during saving client" + e.getMessage());
            }
        } else {
            throw new NullPointerException("Not found client to update, id = " + clientToUpdate.getId());
        }
        return clientToUpdate;
    }

    private void checkForValidFields(Client client) {

        if (client.getName().equals("")
                || client.getAge() == null
                || client.getAddress().equals("")) {
            throw new IllegalArgumentException("An error occurred during creating client! " +
                    "One or more fields are empty!");
        }

        if (client.getAge() > 200 || client.getAge() < 0) {
            throw new IllegalArgumentException("An error occurred during creating client! " +
                    "Check age field.");
        }
    }
}
