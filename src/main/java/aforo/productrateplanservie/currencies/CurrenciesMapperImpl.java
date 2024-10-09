package aforo.productrateplanservie.currencies;

public class CurrenciesMapperImpl implements CurrenciesMapper {

	@Override
	public CurrenciesDTO updateCurrenciesDTO(Currencies currencies, CurrenciesDTO currenciesDTO) {
		CurrenciesDTO dto = currenciesDTO;
		dto.setCurrencyId(currencies.getCurrencyId());
		dto.setCurrencyCode(currencies.getCurrencyCode());
		dto.setCurrencyName(currencies.getCurrencyName());
		dto.setIsActive(currencies.getIsActive());
		dto.setDateCreated(currencies.getDateCreated());
		dto.setLastUpdated(currencies.getLastUpdated());

		return dto;
	}

	@Override
	public Currencies updateCurrencies(CurrenciesDTO currenciesDTO, Currencies currencies) {
		Currencies localCurrency = currencies;
	        localCurrency.setCurrencyId(currenciesDTO.getCurrencyId());
	        localCurrency.setCurrencyCode(currenciesDTO.getCurrencyCode());
	        localCurrency.setCurrencyName(currenciesDTO.getCurrencyName());
	        localCurrency.setIsActive(currenciesDTO.getIsActive());
	        localCurrency.setDateCreated(currenciesDTO.getDateCreated());
	        localCurrency.setLastUpdated(currenciesDTO.getLastUpdated());
	        
	        return localCurrency;
	}

}
