package shop.household.client.create.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.household.client.create.service.ClientCreateService;
import shop.household.model.ClientCreateRequestDto;
import shop.household.model.ClientCreateResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client/create")
public class RequestController {

    private final ClientCreateService clientCreateService;

    @PostMapping
    public ClientCreateResponseDto create(@Valid @RequestBody ClientCreateRequestDto client) {
        return clientCreateService.create(client);
    }
}
