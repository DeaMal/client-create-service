package shop.household.client.create.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import shop.household.client.create.mapper.ClientMapper;
import shop.household.client.create.repository.ClientRepository;
import shop.household.model.ClientCreateRequestDto;
import shop.household.model.ClientCreateResponseDto;
import shop.household.model.ClientDto;
import shop.household.model.ErrorDto;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientCreateService {

    private final ClientRepository clientRepository;
    private final RestTemplate restTemplate;

    public ClientCreateResponseDto create(ClientCreateRequestDto clientDto) throws DataAccessException, RestClientException {
        String url = "http://client-get-service.default.svc.cluster.local:80/client/get";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        ClientDto requestBody = ClientDto.builder().email(clientDto.getEmail()).build();
        HttpEntity<ClientDto> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<ClientCreateResponseDto> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ClientCreateResponseDto.class);
        if (Objects.requireNonNull(responseEntity.getBody()).getStatus()) {
            return new ClientCreateResponseDto()
                    .status(false)
                    .error(new ErrorDto().code(400).message("ERROR: Client with Email '" + clientDto.getEmail() + "' already exist"));
        }
        var client = clientRepository.save(ClientMapper.INSTANCE.requestDtoToClient(clientDto));
        return new ClientCreateResponseDto()
                .status(true)
                .client(ClientMapper.INSTANCE.clientToClientDto(client));
    }
}
