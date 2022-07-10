package com.reco.prob.service;

import com.reco.prob.domain.customer.Customer;
import com.reco.prob.domain.customer.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Test
    @DisplayName("테스트용 데이터가 정상적으로 저장되는지 봅니다.")
    void 업장_저장_테스트() throws Exception {
        //given
        List<Customer> customerList = new ArrayList<>();
        customerList.add(Customer.createCustomer("맘스터치", "강남", LocalDate.of(2021, 10, 01), "김맘스"));
        customerList.add(Customer.createCustomer("롯데리아", "서초", LocalDate.of(2021, 11, 01), "박롯데"));
        customerList.add(Customer.createCustomer("베스킨라빈스", "역삼", LocalDate.of(2021, 11, 05), "베스킨"));
        customerList.add(Customer.createCustomer("본죽", "삼성", LocalDate.of(2021, 11, 03), "최본죽"));

        //when
        customerList.forEach(customerService::save);

        //then
        assertEquals(customerService.findAll().size(), customerList.size());
    }

    @DisplayName("날짜유형은 yyyy-MM-dd 형식으로 저장되야 합니다.")
    @Test
    public void 저장된_날짜_유형_테스트() throws Exception {
        String name = "테스트식당";
        //given
        Customer customer = Customer.createCustomer(name, "강남", LocalDate.of(2021, 10, 01), "김맘스");

        //when
        customerService.save(customer);
        String savedDate = customerService.findByName(name).get(0).getContractDate().toString();
        SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy-MM-dd"); //검증할 날짜 포맷 설정
        dateFormatParser.setLenient(false);

        //then
        assertDoesNotThrow(() ->dateFormatParser.parse(savedDate));
    }

}