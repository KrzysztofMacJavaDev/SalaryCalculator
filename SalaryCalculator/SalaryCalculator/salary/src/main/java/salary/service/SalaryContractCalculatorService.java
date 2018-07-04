package salary.service;
import java.util.Set;


public interface SalaryContractCalculatorService {

	public Set<String> getListOfCountryFromContracts() ;
	public double calculateNetSalaryContract(String country, double dayPriceGross);
		

}
