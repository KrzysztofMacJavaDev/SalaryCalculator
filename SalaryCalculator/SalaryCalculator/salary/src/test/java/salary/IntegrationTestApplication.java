package salary;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.greaterThan;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import salary.models.NetSalaryContract;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTestApplication {
	
   @Autowired
   private TestRestTemplate restTemplate;
	
   //Test on real server
   @Test
   public void testCorrectlyResponseWhenValidCountry()  {
	    HttpHeaders headers = new HttpHeaders();
	    MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
	    params.add("country", "PL");
	    params.add("daypricegross","200");
	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
	    ResponseEntity<NetSalaryContract> response = restTemplate.postForEntity( "/netpricesalary", request , NetSalaryContract.class );
	    Assert.assertThat(response.getBody().getAmount(), greaterThan(0.0));
	    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
//      
   @Test
   public void testCorrectlyResponseWhenInvalidCountry()  {
	    HttpHeaders headers = new HttpHeaders();
	    MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
	    params.add("country", "FR");
	    params.add("daypricegross","23");
	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
	    ResponseEntity<NetSalaryContract> response = restTemplate.postForEntity( "/netpricesalary", request , NetSalaryContract.class );
	    Assert.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
   }
   

   @Test
   public void testCorrectlyResponseWhenCountryDifferentThenPl()  {
	    HttpHeaders headers = new HttpHeaders();
	    MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
	    params.add("country", "DE");
	    params.add("daypricegross","200");
	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
	    ResponseEntity<NetSalaryContract> response = restTemplate.postForEntity( "/netpricesalary", request , NetSalaryContract.class );
	    Assert.assertThat(response.getBody().getAmount(), greaterThan(0.0));
	    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}