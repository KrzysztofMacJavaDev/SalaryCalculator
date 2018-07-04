package salary.models;


import java.text.DecimalFormat;

public class NetSalaryContract {
	private static final DecimalFormat df2 = new DecimalFormat(".##");
	private double amount;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public NetSalaryContract() {
	}
	
	public NetSalaryContract(double amount) {
		this.amount = Double.valueOf( df2.format(amount).replace(',', '.'));
	}
	
}
