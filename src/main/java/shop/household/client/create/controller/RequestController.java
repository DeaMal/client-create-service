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
import shop.household.model.ErrorDto;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client/create")
public class RequestController {

    private final ClientCreateService clientCreateService;

    @PostMapping
    public ClientCreateResponseDto create(@RequestHeader HttpHeaders headers, @Validated @RequestBody ClientCreateRequestDto requestDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ClientCreateResponseDto()
                    .status(false)
                    .error(new ErrorDto().code(400).message("Validation error: " + Objects.requireNonNull(result.getFieldError()).getField()));
        }
        try {
            return clientCreateService.create(requestDto);
        } catch (DataAccessException | RestClientException e) {
            return new ClientCreateResponseDto()
                    .status(false)
                    .error(new ErrorDto().code(422).message(Objects.requireNonNull(e.getRootCause()).getMessage()));
        }
    }
}
