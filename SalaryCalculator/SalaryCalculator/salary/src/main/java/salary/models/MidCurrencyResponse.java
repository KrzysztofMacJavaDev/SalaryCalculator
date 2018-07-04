package salary.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class MidCurrencyResponse {

	private List<Rate> rates;
	public static class Rate {

		@JsonProperty("mid")
		private double midCurrency;

		public double getMidCurrency() {
			return midCurrency;
		}

		public void setMidCurrency(double midCurrency) {
			this.midCurrency = midCurrency;
		}

		@Override
		public String toString() {
			return "Rate [midCurrency=" + midCurrency + "]";
		}
		
	}
	
	public List<Rate> getRates() {
		return rates;
	}
	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}
	@Override
	public String toString() {
		return "MidCurrencyResponse [rates=" + rates + "]";
	}
	
	

}
