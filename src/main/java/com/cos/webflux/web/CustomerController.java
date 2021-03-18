package com.cos.webflux.web;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.webflux.domain.Customer;
import com.cos.webflux.domain.CustomerRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class CustomerController {
	
	private final CustomerRepository customerRepository;
	
	@PostMapping("/customer")
	public Mono<Customer> save(@RequestBody Customer customer){ // 원래는 Customer말고 dto를 리턴
		return customerRepository.save(customer).log();
		
	}
	
	@GetMapping(value = "/customer", produces = MediaType.TEXT_EVENT_STREAM_VALUE) //SSE프로토콜일 때 TEXT_EVENT_STREAM_VALUE 사용
	public Flux<Customer> findAll(){
		return customerRepository.findAll().delayElements(Duration.ofSeconds(1)).log(); // 1초 딜레이
	}
	
	@GetMapping("/customer/{id}")
	public Mono<Customer> findById(@PathVariable Long id){
		return customerRepository.findById(id).log();
	}
	
	@DeleteMapping("/customer/{id}")
	public Mono<Void> deleteById(@PathVariable Long id){ // Integer 대신에 CommonRespDto 만들어서 숫자 던져주면 됨
		return customerRepository.deleteById(id).log();
	}
	
	@PutMapping("/customer/{id}")
	public Mono<Customer> update(@PathVariable Long id, @RequestBody Customer customer){
		customer.setId(id);
		return customerRepository.save(customer); // 더티체킹없음
	
	}
}