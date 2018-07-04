package salary.service;

import salary.models.MidCurrencyResponse;

public interface CurrencyCalculatorNbpClient {
	
	public MidCurrencyResponse getMidCurrency( String codeCurrency);

}
