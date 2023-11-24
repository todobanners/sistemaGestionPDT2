package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
import codigocreativo.uy.servidorapp.DTO.BajaEquipoDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface BajaEquipoMapper {
    BajaEquipo toEntity(BajaEquipoDto bajaEquipoDto);

    BajaEquipoDto toDto(BajaEquipo bajaEquipo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BajaEquipo partialUpdate(BajaEquipoDto bajaEquipoDto, @MappingTarget BajaEquipo bajaEquipo);

    List<BajaEquipo> toEntity(List<BajaEquipoDto> bajaEquipoDto);

    List<BajaEquipoDto> toDto(List<BajaEquipo> bajaEquipo);
}