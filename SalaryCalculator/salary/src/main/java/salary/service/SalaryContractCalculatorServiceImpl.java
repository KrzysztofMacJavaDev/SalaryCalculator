package salary.service;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import salary.config.CalculatorAppProperties;
import salary.models.MidCurrencyResponse;

@Service
public class SalaryContractCalculatorServiceImpl implements SalaryContractCalculatorService {
	
	@Autowired 
	private CurrencyCalculatorNbpClient currencyCalculatorNbpClientImpl;
	
	@Autowired
	private CalculatorAppProperties calculatorAppProperties;

	private final static int MONTH_DAYS = 22;
	private final Map<String,CalculatorAppProperties.Contract> contractByCountryMap = new HashMap<>();
	
	
	@PostConstruct
    public void init(){
		initializeContractByCountryMap();
    }
	
	public void initializeContractByCountryMap(){
		List<CalculatorAppProperties.Contract> contractList = calculatorAppProperties.getContracts(); 
		for(CalculatorAppProperties.Contract contract:contractList) {
			contractByCountryMap.put(contract.getCountry(), contract);
		}
    }
	
	
	@Override
	public Set<String> getListOfCountryFromContracts() {
		Set<String> keys = contractByCountryMap.keySet();
		return keys;  
	}

	
	@Override
	public double calculateNetSalaryContract(String country, double dayPriceGross) {
		CalculatorAppProperties.Contract contract = contractByCountryMap.get(country);
		double midCurrency;
		if(!contract.getCodeCurrency().equalsIgnoreCase("pln")) {
			MidCurrencyResponse midCurrencyResponse = currencyCalculatorNbpClientImpl.getMidCurrency(contract.getCodeCurrency());
			midCurrency = midCurrencyResponse.getRates().get(0).getMidCurrency();
		} else {
			midCurrency = 1;
		}
		double monthGrossPrice = MONTH_DAYS * dayPriceGross;
		double tax =  ( monthGrossPrice * contract.getTax() / 100);
		int cost = contract.getCost() ;
		double netContractValue = (monthGrossPrice - tax - cost) * midCurrency;	
		return netContractValue;
	}	

}