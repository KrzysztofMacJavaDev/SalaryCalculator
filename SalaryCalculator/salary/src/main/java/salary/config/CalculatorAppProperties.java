package salary.config;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties("config.calculator")
public class CalculatorAppProperties {


	@Valid
    @Size(min=1, max=5)
	private List<Contract> contracts;

	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public static class Contract {
		@NotEmpty
		private String country;
		
		@NotEmpty
		private String codeCurrency;
		
		@PositiveOrZero 
		private Integer tax;
		
	
		@PositiveOrZero 
		private Integer cost;
		
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		
		public String getCodeCurrency() {
			return codeCurrency;
		}
		public void setCodeCurrency(String codeCurrency) {
			this.codeCurrency = codeCurrency;
		}
		public int getTax() {
			return tax;
		}
		public void setTax(int tax) {
			this.tax = tax;
		}
		public int getCost() {
			return cost;
		}
		public void setCost(int cost) {
			this.cost = cost;
		}
		@Override
		public String toString() {
			return "Contract [country=" + country + ", codeCurrency=" + codeCurrency + ", tax=" + tax + ", cost=" + cost
					+ "]";
		}	
	}

	@Override
	public String toString() {
		return "CalculatorAppProperties [contractList=" + contracts + "]";
	}
}
