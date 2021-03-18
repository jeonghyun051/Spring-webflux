package com.cos.webflux.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import com.cos.webflux.domain.Customer;
import com.cos.webflux.domain.CustomerRepository;

import io.r2dbc.spi.ConnectionFactory;

@Configuration
public class DBInit {
	
	@Bean
	public ConnectionFactoryInitializer dbInit(ConnectionFactory connectionFactory) { // 데이터베이스 초기화를 할수 있음 ConnectionFactoryInitializer
		ConnectionFactoryInitializer init = new ConnectionFactoryInitializer();
		init.setConnectionFactory(connectionFactory);
		init.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql"))); // 커넥션꺼
		return init; // r2dbc 쓸 때 이 코드만 복붙해서 쓰면됨 
		
	}
	
	@Bean
	public CommandLineRunner dataInit(CustomerRepository customerRepository) { // ioc에서 해당하는 customerRepository 주입이 된다.
		return (args) -> { // run 함수
			// 데이터 초기화 하기
			customerRepository.saveAll(Arrays.asList(
					new Customer("Jack", "Bauer"),
					new Customer("Choi", "Bauer"),
					new Customer("Hong", "Bauer"),
					new Customer("Han", "Bauer"),
					new Customer("Joo", "Bauer")
					
					)
					).blockLast(); // 끝인걸 알려줘야함
		};
	}
}