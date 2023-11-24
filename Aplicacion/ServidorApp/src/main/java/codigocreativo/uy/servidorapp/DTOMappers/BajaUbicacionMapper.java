package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.entidades.BajaUbicacion;
import codigocreativo.uy.servidorapp.DTO.BajaUbicacionDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.CDI, uses = {UsuarioMapper.class, UbicacionMapper.class})
public interface BajaUbicacionMapper {
    BajaUbicacion toEntity(BajaUbicacionDto bajaUbicacionDto);

    BajaUbicacionDto toDto(BajaUbicacion bajaUbicacion);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BajaUbicacion partialUpdate(BajaUbicacionDto bajaUbicacionDto, @MappingTarget BajaUbicacion bajaUbicacion);
}