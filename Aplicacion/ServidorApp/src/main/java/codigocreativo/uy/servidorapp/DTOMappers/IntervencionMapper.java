package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.entidades.Intervencion;
import codigocreativo.uy.servidorapp.DTO.IntervencionDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface IntervencionMapper {
    Intervencion toEntity(IntervencionDto intervencionDto);

    IntervencionDto toDto(Intervencion intervencion);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Intervencion partialUpdate(IntervencionDto intervencionDto, @MappingTarget Intervencion intervencion);

    List<Intervencion> toEntity(List<IntervencionDto> intervencionDto);

    List<IntervencionDto> toDto(List<Intervencion> intervencion);
}