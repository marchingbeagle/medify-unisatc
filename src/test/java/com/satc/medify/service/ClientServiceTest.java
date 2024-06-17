package com.satc.medify.service;

import com.satc.medify.model.Client.Client;
import com.satc.medify.model.Client.ClientDTO;
import com.satc.medify.model.Client.ClientRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ClientServiceTest {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should return client passing the id")
    void getClientByIdCase1() {
        Long id = (long) 1;
        ClientDTO data = new ClientDTO(id, "John", "test", "test", "test");
        this.createClientFake(data);

        Optional<Client> result = this.clientRepository.findById(id);
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not return client passing the id")
    void getClientByIdCase2() {
        Long id = (long) 1;

        Optional<Client> result = this.clientRepository.findById(id);
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should successfully create an client")
    void createClient() {
        Long id = (long) 1;

        ClientDTO data = new ClientDTO(id, "John", "test", "test", "test");
        Client employee = createClientFake(data);

        Client result = this.clientRepository.findById(employee.getId()).orElse(null);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(data.name());
        assertThat(result.getEmail()).isEqualTo(data.email());
        assertThat(result.getCpf()).isEqualTo(data.cpf());
        assertThat(result.getPhoneNumber()).isEqualTo(data.phoneNumber());
    }

    @Test
    @DisplayName("Should successfully update an client")
    void updateClient() {
        Long id = (long) 1;
        ClientDTO data = new ClientDTO(id, "John", "test", "test", "test");
        createClientFake(data);

        Client updatedData = new Client(new ClientDTO(id, "John Updated", "test1@gmail.com", "123", "11111111111"));
        this.clientRepository.save(updatedData);

        Client result = this.clientRepository.findById(id).orElse(null);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John Updated");
        assertThat(result.getEmail()).isEqualTo("test1@gmail.com");
        assertThat(result.getCpf()).isEqualTo("11111111111");
        assertThat(result.getPhoneNumber()).isEqualTo("123");
    }

    @Test
    @DisplayName("Should successfully delete an client")
    void deleteClient() {
        Long id = (long) 1;
        ClientDTO data = new ClientDTO(id, "John", "test@gmail.com","312312312", "11111111111");
        Client client = createClientFake(data);

        this.clientRepository.delete(client);

        Optional<Client> result = this.clientRepository.findById(id);
        assertThat(result.isEmpty()).isTrue();
    }

    private Client createClientFake(ClientDTO clientDTO){
        Client client = new Client(clientDTO);
        this.entityManager.merge(client);
        this.entityManager.flush();
        return client;
    }
}