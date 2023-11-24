package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.DTO.EquipoDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface EquipoMapper {
    Equipo toEntity(EquipoDto equipoDto);

    EquipoDto toDto(Equipo equipo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Equipo partialUpdate(EquipoDto equipoDto, @MappingTarget Equipo equipo);

    List<Equipo> toEntity(List<EquipoDto> equipoDto);

    List<EquipoDto> toDto(List<Equipo> equipo);
}