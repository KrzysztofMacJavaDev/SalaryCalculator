package salary.controllers;


import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.ModelAndView;
import salary.models.NetSalaryContract;
import salary.service.SalaryContractCalculatorService;

@Controller
public class SalaryContractCalculatorController {
	
	private static Logger logger = LoggerFactory.getLogger(SalaryContractCalculatorController.class);	
	
	@Autowired
	private SalaryContractCalculatorService salaryContractCalculatorServiceImpl; 
			
	@RequestMapping(value = "/netpricesalary", method = RequestMethod.POST)
	public ResponseEntity<NetSalaryContract> getNetPriceSalary(
	    @RequestParam(value="country", required=true) String country,
	    @RequestParam(value="daypricegross") double dayPriceGross) {
	    try {  	
			double amount =  salaryContractCalculatorServiceImpl.calculateNetSalaryContract(country, dayPriceGross);
			NetSalaryContract netSalaryContract = new NetSalaryContract(amount);
			return  new ResponseEntity<NetSalaryContract>(netSalaryContract, HttpStatus.OK);
		}catch(HttpStatusCodeException e) {
			logger.error("NBP Api Exception" , e);
			return  new ResponseEntity<>(e.getStatusCode());
		} catch(Exception e) {
			logger.error("Internal Server Error" ,e);
			return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getCalculatorView() {
    	Set<String> countries = salaryContractCalculatorServiceImpl.getListOfCountryFromContracts();
    	ModelAndView mav = new ModelAndView("calculatorview", "countries", countries );	
    	return mav;
    }
    
}