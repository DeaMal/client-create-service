package shop.household.client.create.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import shop.household.client.create.mapper.ClientMapper;
import shop.household.client.create.repository.ClientRepository;
import shop.household.model.ClientCreateRequestDto;
import shop.household.model.ClientCreateResponseDto;

@Service
@RequiredArgsConstructor
public class ClientCreateService {

    private final ClientRepository clientRepository;

    public ClientCreateResponseDto create(ClientCreateRequestDto clientDto) throws DataAccessException {
        var client = clientRepository.save(ClientMapper.INSTANCE.requestDtoToClient(clientDto));
        return new ClientCreateResponseDto()
                .status(true)
                .client(ClientMapper.INSTANCE.clientToClientDto(client));
    }
}
