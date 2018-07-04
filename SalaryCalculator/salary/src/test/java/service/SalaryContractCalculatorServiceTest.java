package service;


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import salary.config.CalculatorAppProperties;
import salary.models.MidCurrencyResponse;
import salary.service.CurrencyCalculatorNbpClient;
import salary.service.SalaryContractCalculatorServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class SalaryContractCalculatorServiceTest {
	
	@Mock
	private CurrencyCalculatorNbpClient currencyCalculatorNbpClient;
	
	@Mock
	private CalculatorAppProperties calculatorAppProperties;
	
	@InjectMocks
	private SalaryContractCalculatorServiceImpl serviceImpl;
	private List<CalculatorAppProperties.Contract> contractList = new ArrayList<>();
	
	 
	@Before
	public  void initializeAppProperties() {
		CalculatorAppProperties.Contract contractDe = new CalculatorAppProperties.Contract();
		contractDe.setCodeCurrency("EUR");
		contractDe.setCountry("DE");
		contractDe.setTax(20);
		contractDe.setCost(800);
		
		CalculatorAppProperties.Contract contractUk = new CalculatorAppProperties.Contract();
		contractUk.setCodeCurrency("GBP");
		contractUk.setCountry("UK");
		contractUk.setTax(25);
		contractUk.setCost(600);
		
		CalculatorAppProperties.Contract contractPl = new CalculatorAppProperties.Contract();
		contractPl.setCodeCurrency("PLN");
		contractPl.setCountry("PL");
		contractPl.setTax(19);
		contractPl.setCost(1200);
		
		contractList.add(contractDe);
		contractList.add(contractUk);
		contractList.add(contractPl);
	}

	@Test
	public void notCallGetMidCurrencyWhenCountryIsPl() {
	    Mockito.when(calculatorAppProperties.getContracts()).thenReturn(contractList);
	    serviceImpl.initializeContractByCountryMap();
	    double defaultDayPriceGross = 0.0;
	    serviceImpl.calculateNetSalaryContract("PL", defaultDayPriceGross);	    
	    Mockito.verify(currencyCalculatorNbpClient, Mockito.times(0)).getMidCurrency(Mockito.any(String.class));
	}
	
	@Test
	public void callGetMidCurrencyWhenCountryIsDiffefentThanPl() {
		double defaultDayPriceGross = 0.0;   
		double defaultMidCurrency = 4.5;
	    MidCurrencyResponse midCurrencyResponse = new MidCurrencyResponse();
	    List<MidCurrencyResponse.Rate> rateList = new ArrayList<MidCurrencyResponse.Rate>();
	    midCurrencyResponse.setRates(rateList);
	    MidCurrencyResponse.Rate rate = new MidCurrencyResponse.Rate();
	    rate.setMidCurrency(defaultMidCurrency);
	    rateList.add(rate);

	    Mockito.when(currencyCalculatorNbpClient.getMidCurrency("EUR")).thenReturn(midCurrencyResponse);
	    Mockito.when(calculatorAppProperties.getContracts()).thenReturn(contractList);       
	    serviceImpl.initializeContractByCountryMap();	    
	    serviceImpl.calculateNetSalaryContract("DE", defaultDayPriceGross);
	    Mockito.verify(currencyCalculatorNbpClient, Mockito.times(1)).getMidCurrency(Mockito.any(String.class));
	    System.out.println(contractList.size());
	}
		
	
	@Test(expected=NullPointerException.class)	
	public void shouldThrowNullPointerExceptionWhenWhenCountryIsInvalid() {
		double defaultDayPriceGross = 0.0;   
	    Mockito.when(calculatorAppProperties.getContracts()).thenReturn(contractList);       
	    serviceImpl.initializeContractByCountryMap();	    
	    serviceImpl.calculateNetSalaryContract("DEE", defaultDayPriceGross);
	}
	

	@Test
	public void shouldCorrectlyValueWhenCountryIsPlAndDayPriceGrossEqualsZero() {
	    Mockito.when(calculatorAppProperties.getContracts()).thenReturn(contractList);
	    serviceImpl.initializeContractByCountryMap();
	    double defaultDayPriceGross = 0.0;
	    double result = serviceImpl.calculateNetSalaryContract("PL", defaultDayPriceGross);	    
	    Assert.assertEquals(result, -1200.0, 0.0); 
	}
	
	@Test
	public void shouldCorrectlyValueWhenCountryIsPlAndDayPriceGrossEqualsNotZero() {
	    Mockito.when(calculatorAppProperties.getContracts()).thenReturn(contractList);
	    serviceImpl.initializeContractByCountryMap();
	    double defaultDayPriceGross = 150;
	    double result = serviceImpl.calculateNetSalaryContract("PL", defaultDayPriceGross);	    
	    Assert.assertEquals(result, 1473.0, 0.0); 
	}
	
	@Test
	public void shouldCorrectlyValueWhenCountryIsDiffefentThanPlAndDayPriceGrossEqualsZero() {
		double defaultDayPriceGross = 0.0;   
		double defaultMidCurrency = 4.5;
	    MidCurrencyResponse midCurrencyResponse = new MidCurrencyResponse();
	    List<MidCurrencyResponse.Rate> rateList = new ArrayList<MidCurrencyResponse.Rate>();
	    midCurrencyResponse.setRates(rateList);
	    MidCurrencyResponse.Rate rate = new MidCurrencyResponse.Rate();
	    rate.setMidCurrency(defaultMidCurrency);
	    rateList.add(rate);

	    Mockito.when(currencyCalculatorNbpClient.getMidCurrency("EUR")).thenReturn(midCurrencyResponse);
	    Mockito.when(calculatorAppProperties.getContracts()).thenReturn(contractList);       
	    serviceImpl.initializeContractByCountryMap();	    
	    double result = serviceImpl.calculateNetSalaryContract("DE", defaultDayPriceGross);
	    Assert.assertEquals(result, -3600.0, 0.0); 
	}
	

	@Test
	public void shouldCorrectlyValueWhenCountryIsDiffefentThanPlAndDayPriceGrossEqualsNotZero() {
		double defaultDayPriceGross = 150.45;  
		double defaultMidCurrency = 4.5;
	    MidCurrencyResponse midCurrencyResponse = new MidCurrencyResponse();
	    List<MidCurrencyResponse.Rate> rateList = new ArrayList<MidCurrencyResponse.Rate>();
	    midCurrencyResponse.setRates(rateList);
	    MidCurrencyResponse.Rate rate = new MidCurrencyResponse.Rate();
	    rate.setMidCurrency(defaultMidCurrency);
	    rateList.add(rate);

	    Mockito.when(currencyCalculatorNbpClient.getMidCurrency("EUR")).thenReturn(midCurrencyResponse);
	    Mockito.when(calculatorAppProperties.getContracts()).thenReturn(contractList);       
	    serviceImpl.initializeContractByCountryMap();	    
	    double result = serviceImpl.calculateNetSalaryContract("DE", defaultDayPriceGross);
	    Assert.assertEquals(result, 8315.63, 0.01); 
	}
	
}
