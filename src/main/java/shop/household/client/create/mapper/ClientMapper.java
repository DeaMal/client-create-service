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
    @Mapping(target = "phone_extra", source = "requestDto.phoneExtra")
    @Mapping(target = "create_at", ignore = true)
//    @Mapping(target = "create_at", expression = "java(new Timestamp(System.currentTimeMillis()))")
    Client requestDtoToClient(ClientCreateRequestDto requestDto);

    @Mapping(target = "phoneExtra", source = "client.phone_extra")
    ClientDto clientToClientDto(Client client);

    @AfterMapping
    default void setAdditionalFields(@MappingTarget Client client) {
        client.setCreate_at(new Timestamp(System.currentTimeMillis()));
    }
}
