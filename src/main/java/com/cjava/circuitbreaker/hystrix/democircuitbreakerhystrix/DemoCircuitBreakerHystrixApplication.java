package com.cjava.circuitbreaker.hystrix.democircuitbreakerhystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@SpringBootApplication
@RestController
@EnableCircuitBreaker
@EnableHystrixDashboard
public class DemoCircuitBreakerHystrixApplication {

	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoCircuitBreakerHystrixApplication.class, args);
	}
	
	@RequestMapping(value="/customer", method=RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "fallback")
	public String myCircuitBreaker()
	{
		return this.restTemplate.getForObject("http://localhost:8080/v1/customer",String.class);
	}
	
	public String fallback() {
		    return "my fall back is called...";
		  }
}
