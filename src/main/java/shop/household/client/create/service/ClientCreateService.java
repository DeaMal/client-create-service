package shop.household.client.create.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.household.client.create.entity.Client;
import shop.household.client.create.repository.ClientRepository;
import shop.household.model.ClientCreateRequestDto;
import shop.household.model.ClientCreateResponseDto;
import shop.household.model.ClientDto;
import shop.household.model.ErrorDto;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class ClientCreateService {

    private final ClientRepository clientRepository;

    public ClientCreateResponseDto create(ClientCreateRequestDto clientDto) {
        var client = clientRepository.save(new Client()
                .setFirstname(clientDto.getFirstname())
                .setLastname(clientDto.getLastname())
                .setPhone(clientDto.getPhone())
                .setPhone_extra(clientDto.getPhoneExtra())
                .setEmail(clientDto.getEmail())
                .setAddress(clientDto.getAddress())
                .setCreate_at(new Timestamp(System.currentTimeMillis())));
        return new ClientCreateResponseDto()
                .status(true)
                .client(new ClientDto()
                        .id(client.getId())
                        .firstname(client.getFirstname())
                        .lastname(client.getLastname())
                        .phone(client.getPhone())
                        .phoneExtra(client.getPhone_extra())
                        .email(client.getEmail())
                        .address(client.getAddress()))
                .error(new ErrorDto()
                        .code(200)
                        .message("OK"));
    }
}
