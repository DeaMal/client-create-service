package shop.household.client.create.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import shop.household.client.create.entity.Client;
import shop.household.model.ClientCreateRequestDto;
import shop.household.model.ClientDto;

import java.sql.Timestamp;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phoneExtra", source = "requestDto.phoneExtra")
    @Mapping(target = "totalOrders", constant = "0")
    @Mapping(target = "totalSpent", expression = "java(java.math.BigDecimal.ZERO)")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastOrderDate", ignore = true)
    @Mapping(target = "createAt", ignore = true)
//    @Mapping(target = "createAt", expression = "java(new java.sql.Timestamp(System.currentTimeMillis()))")
    Client requestDtoToClient(ClientCreateRequestDto requestDto);

    @Mapping(target = "phoneExtra", source = "client.phoneExtra")
    ClientDto clientToClientDto(Client client);

    @AfterMapping
    default void setAdditionalFields(@MappingTarget Client client) {
        client.setCreateAt(new Timestamp(System.currentTimeMillis()));
    }
}
