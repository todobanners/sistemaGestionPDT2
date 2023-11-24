package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.EquiposUbicacioneDto;
import codigocreativo.uy.servidorapp.entidades.EquiposUbicacione;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface EquiposUbicacioneMapper {
    EquiposUbicacione toEntity(EquiposUbicacioneDto equiposUbicacioneDto);

    EquiposUbicacioneDto toDto(EquiposUbicacione equiposUbicacione);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EquiposUbicacione partialUpdate(EquiposUbicacioneDto equiposUbicacioneDto, @MappingTarget EquiposUbicacione equiposUbicacione);

    List<EquiposUbicacione> toEntity(List<EquiposUbicacioneDto> equiposUbicacioneDto);

    List<EquiposUbicacioneDto> toDto(List<EquiposUbicacione> equiposUbicacione);
}