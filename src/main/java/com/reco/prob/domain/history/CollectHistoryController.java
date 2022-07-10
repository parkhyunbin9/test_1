package com.reco.prob.domain.history;

import com.reco.prob.domain.customer.Customer;
import com.reco.prob.domain.customer.CustomerService;
import com.reco.prob.dto.CollectDateRequestDto;
import com.reco.prob.dto.CollectResponse;
import com.reco.prob.dto.CustomerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CollectHistoryController {

    private final CollectHistoryService collectHistoryService;
    private final CustomerService customerService;

    @GetMapping("/api/histories")
    public ResponseEntity<CustomerResponseDto> selectByHistoryId(@RequestParam Long id ) {
        CustomerResponseDto response = collectHistoryService.getCustomerResponseDto(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/api/histories/customer")
    public ResponseEntity<List<CustomerResponseDto>> selectByCustomerId(@RequestParam Long id) {
        Customer customer = customerService.findById(id);
        List<CustomerResponseDto> customerResponseDto = collectHistoryService.getCustomerResponseDto(customer);
        return ResponseEntity.ok().body(customerResponseDto);
    }

    @GetMapping("/api/page/histories/customer")
    public ResponseEntity<List<CollectHistory>> selectByCustomerId(@RequestParam Long id, Pageable pageable) {
        Customer customer = customerService.findById(id);
        List<CollectHistory> histories = collectHistoryService.findByCustomer(customer, pageable);
        return ResponseEntity.ok().body(histories);
    }

    @GetMapping("/api/histories/date")
    public ResponseEntity<List<CollectResponse>> selectByDate(@RequestParam String dateStr) {
        CollectDateRequestDto inputDate = new CollectDateRequestDto(dateStr);
        List<CollectResponse> collectResponse = collectHistoryService.getCollectResponse(inputDate.getCollectStart(), inputDate.getCollectEnd());
        return  ResponseEntity.ok().body(collectResponse);
    }
}
