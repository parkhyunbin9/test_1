package com.reco.prob.domain.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer findById(Long id) throws IllegalArgumentException{
        return customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업장입니다."));
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findByName(String name) {
        return customerRepository.findByName(name).get();
    }

    @Transactional
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}
