package com.satc.medify.service;

import com.satc.medify.model.Client.Client;
import com.satc.medify.model.Client.ClientDTO;
import com.satc.medify.model.Client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClient() {return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client createClient(ClientDTO clientDTO) {
        Client client = new Client(clientDTO);
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, ClientDTO clientDTO) {
        Optional<Client> existingClient = clientRepository.findById(id);
        if (existingClient.isPresent()) {
            Client updatedClient = existingClient.get();
            updatedClient.setName(clientDTO.name());
            updatedClient.setEmail(clientDTO.email());
            updatedClient.setPhoneNumber(clientDTO.phoneNumber());
            updatedClient.setCpf(clientDTO.cpf());
            return clientRepository.save(updatedClient);
        }
        return null;
    }

    public boolean deleteClient(Long id) {
        Optional<Client> existingClient = clientRepository.findById(id);
        if (existingClient.isPresent()) {
            clientRepository.delete(existingClient.get());
            return true;
        }
        return false;
    }
}