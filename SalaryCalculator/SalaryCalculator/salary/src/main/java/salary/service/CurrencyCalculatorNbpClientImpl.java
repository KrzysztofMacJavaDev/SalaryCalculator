package salary.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import salary.models.MidCurrencyResponse;

@Service
public class CurrencyCalculatorNbpClientImpl implements CurrencyCalculatorNbpClient{

	private static final String URL_MID_CURRENCY = "http://api.nbp.pl/api/exchangerates/rates/a/{code}/";
		
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public MidCurrencyResponse getMidCurrency( String codeCurrency) {	
		Map<String, String> params = new HashMap<String, String>();
	    params.put("code", codeCurrency);	
	    MidCurrencyResponse midCurrencyReponse = restTemplate.getForObject(URL_MID_CURRENCY, MidCurrencyResponse.class, params);
	    return midCurrencyReponse;
	}
	
}
