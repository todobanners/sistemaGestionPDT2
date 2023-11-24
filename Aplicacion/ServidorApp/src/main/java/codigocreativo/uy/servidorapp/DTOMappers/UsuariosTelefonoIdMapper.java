package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.UsuariosTelefonoIdDto;
import codigocreativo.uy.servidorapp.entidades.UsuariosTelefonoId;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface UsuariosTelefonoIdMapper {
    UsuariosTelefonoId toEntity(UsuariosTelefonoIdDto usuariosTelefonoIdDto);

    UsuariosTelefonoIdDto toDto(UsuariosTelefonoId usuariosTelefonoId);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UsuariosTelefonoId partialUpdate(UsuariosTelefonoIdDto usuariosTelefonoIdDto, @MappingTarget UsuariosTelefonoId usuariosTelefonoId);

    List<UsuariosTelefonoId> toEntity(List<UsuariosTelefonoIdDto> usuariosTelefonoIdDto);

    List<UsuariosTelefonoIdDto> toDto(List<UsuariosTelefonoId> usuariosTelefonoId);
}