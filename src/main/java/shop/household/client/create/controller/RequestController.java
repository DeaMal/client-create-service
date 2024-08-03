package shop.household.client.create.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import shop.household.client.create.service.ClientCreateService;
import shop.household.model.ClientCreateRequestDto;
import shop.household.model.ClientCreateResponseDto;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client/create")
public class RequestController {

    private final ClientCreateService clientCreateService;

    @PostMapping
    public ClientCreateResponseDto create(@RequestHeader HttpHeaders headers, @Validated @RequestBody ClientCreateRequestDto requestDto, BindingResult result) {
        if (result.hasErrors()) {
            return clientCreateService.createResponseDto(false, 400, "Validation error: " + Objects.requireNonNull(result.getFieldError()).getField());
        }
        try {
            return clientCreateService.create(requestDto);
        } catch (DataAccessException e) {
            return clientCreateService.createResponseDto(false, 422, Objects.requireNonNull(e.getRootCause()).getMessage());
        } catch (RestClientException e) {
            return clientCreateService.createResponseDto(false, 503, e.getMessage());
        }
    }
}
