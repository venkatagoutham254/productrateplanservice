package aforo.productrateplanservie.service;

import aforo.productrateplanservie.domain.Currencies;
import aforo.productrateplanservie.model.CurrenciesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CurrenciesMapper {

    CurrenciesDTO updateCurrenciesDTO(Currencies currencies,
            @MappingTarget CurrenciesDTO currenciesDTO);

    @Mapping(target = "currencyId", ignore = true)
    Currencies updateCurrencies(CurrenciesDTO currenciesDTO, @MappingTarget Currencies currencies);

}
