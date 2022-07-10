package com.reco.prob.domain.customer;

import com.reco.prob.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/api/customers")
    public ResponseEntity<Customer> select(@RequestParam Long id) throws Exception {
        Customer customer = customerService.findById(id);
        return ResponseEntity.ok().body(customer);
    }

    @PostMapping("/api/customers")
    public ResponseEntity<?> create(final @Valid @RequestBody CustomerDto customerDto) {
        Customer savedCustmer = customerService.save(customerDto.getCustomer(customerDto));
        return ResponseEntity.created(URI.create("/customers/" + savedCustmer.getId())).build();
    }

}
