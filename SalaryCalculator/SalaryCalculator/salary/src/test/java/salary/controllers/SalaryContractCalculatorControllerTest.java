package salary.controllers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import salary.config.CalculatorAppProperties;
import salary.controllers.SalaryContractCalculatorController;
import salary.service.SalaryContractCalculatorService;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@EnableConfigurationProperties(CalculatorAppProperties.class)
@WebMvcTest(SalaryContractCalculatorController.class)
public class SalaryContractCalculatorControllerTest {

		@Autowired
		private MockMvc mvc;

		@MockBean
		private SalaryContractCalculatorService service;
		
		private static final String DEFAULT_COUNTRY ="pl";
		private static final String DEFAULT_GROSS_DAY_VALUE = "0";
	
		
		//Below test what should be Status of Response Controllers and Media Type 
		//When Mock Service throws different exceptions and without them.
		@Test
		public void shouldExpectClientErrorStatusWhenSerivceThrowExceptionBadRequest() throws Exception {
			HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST);
			given(service.calculateNetSalaryContract(Mockito.any(String.class), Mockito.any(Double.class))).willThrow(exception);
			this.mvc.perform(post("/netpricesalary")
					.param("country", DEFAULT_COUNTRY)
					.param("daypricegross", DEFAULT_GROSS_DAY_VALUE)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().is4xxClientError());
		}
		
		@Test
		public void shouldExpectClientErrorStatusWhenSerivceThrowExceptionNotFoundStatus() throws Exception {
			HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND);
			given(service.calculateNetSalaryContract(Mockito.any(String.class), Mockito.any(Double.class))).willThrow(exception);
			this.mvc.perform(post("/netpricesalary")
					.param("country", DEFAULT_COUNTRY)
					.param("daypricegross", DEFAULT_GROSS_DAY_VALUE)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().is4xxClientError());
		}
		
		
		@Test
		public void shouldExpectServerErrorStatusWhenSerivceThrowSomeServerException() throws Exception {
			given(service.calculateNetSalaryContract(Mockito.any(String.class), Mockito.any(Double.class))).willThrow(RuntimeException.class);
			this.mvc.perform(post("/netpricesalary")
					.param("country", DEFAULT_COUNTRY) 
					.param("daypricegross", DEFAULT_GROSS_DAY_VALUE)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().is5xxServerError());
		}
		
		@Test
		public void shouldExpectStatusOkWhenServerDontThrowException() throws Exception {
			given(service.calculateNetSalaryContract(Mockito.any(String.class), Mockito.any(Double.class))).willReturn(1.0);
			this.mvc.perform(post("/netpricesalary")
					.param("country",DEFAULT_COUNTRY)
					.param("daypricegross", DEFAULT_GROSS_DAY_VALUE)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		}
		
		//Below test for calculatorview name 
		@Test
		public void shouldExcpetCorrectlyViewNameResponse() throws Exception {
			this.mvc.perform(get("/"))	
			.andExpect(view().name("calculatorview"));          
		}
		
}
