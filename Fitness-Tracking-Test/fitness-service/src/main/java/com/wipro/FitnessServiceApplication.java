package com.wipro;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class FitnessServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessServiceApplication.class, args);
	}
	
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
