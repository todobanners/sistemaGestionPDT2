package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.UsuarioDto;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDto usuarioDto);

    UsuarioDto toDto(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Usuario partialUpdate(UsuarioDto usuarioDto, @MappingTarget Usuario usuario);

    List<Usuario> toEntity(List<UsuarioDto> usuarioDto);

    List<UsuarioDto> toDto(List<Usuario> usuario);
}