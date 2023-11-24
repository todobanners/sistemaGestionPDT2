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

    @AfterMapping
    default void linkEquiposUbicaciones(@MappingTarget Equipo equipo) {
        equipo.getEquiposUbicaciones().forEach(equiposUbicacione -> equiposUbicacione.setIdEquipo(equipo));
    }

    List<EquipoDto> toDto(List<Equipo> equipo);
}