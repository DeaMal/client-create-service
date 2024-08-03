package shop.household.client.create.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
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

    public ClientCreateResponseDto create(ClientCreateRequestDto clientDto) throws DataAccessException, RestClientException {
        ResponseEntity<ClientCreateResponseDto> emailIsExist = checkUniqueEmail(clientDto.getEmail());
        if (emailIsExist.getStatusCode() != HttpStatus.OK) {
            return createResponseDto(false, emailIsExist.getStatusCode().value(), "ERROR: client-get-service: " + emailIsExist.getStatusCode());
        } else if (Objects.requireNonNull(emailIsExist.getBody()).getStatus()) {
            return createResponseDto(false, 302, "ERROR: Client with Email '" + clientDto.getEmail() + "' already exist");
        }
        var client = clientRepository.save(ClientMapper.INSTANCE.requestDtoToClient(clientDto));
        return createResponseDto(true, ClientMapper.INSTANCE.clientToClientDto(client));
    }

    public ClientCreateResponseDto createResponseDto(Boolean status, Integer errorCode, String errorMessage) {
        return ClientCreateResponseDto.builder()
                .status(status)
                .error(new ErrorDto().code(errorCode).message(errorMessage))
                .build();
    }

    public ClientCreateResponseDto createResponseDto(Boolean status, ClientDto client) {
        return ClientCreateResponseDto.builder()
                .status(status)
                .client(client)
                .build();
    }

    private ResponseEntity<ClientCreateResponseDto> checkUniqueEmail(String email) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestUrl = "http://client-get-service/client/get";
        ClientDto requestBody = ClientDto.builder().email(email).build();
        HttpEntity<ClientDto> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity, ClientCreateResponseDto.class);
    }
}
