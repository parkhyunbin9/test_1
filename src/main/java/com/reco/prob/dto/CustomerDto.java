package com.reco.prob.dto;

import com.reco.prob.domain.customer.Customer;
import com.reco.prob.utils.TimeUtil;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
public class CustomerDto {
    @NotEmpty(message = "업체 명을 입력해주셔야 합니다.")
    private String name;

    @NotEmpty(message = "지역을 입력해주셔야 합니다.")
    private String locale;

    @NotEmpty(message = "계약 날짜를 입력해주셔야 합니다.")
    private String contractDate;

    @NotEmpty(message = "사업자 이름 입력해주셔야 합니다.")
    private String ownerName;

    public CustomerDto(String name, String locale, String ownerName) {
        this.name = name;
        this.locale = locale;
        this.contractDate = TimeUtil.SIMPLE_DATE_FORMATTER.format(LocalDate.now());
        this.ownerName = ownerName;
    }

    public CustomerDto(String name, String locale, String contractDate, String ownerName) {
        this.name = name;
        this.locale = locale;
        this.contractDate = contractDate;
        this.ownerName = ownerName;
    }

    public CustomerDto() {}

    public Customer getCustomer(CustomerDto dto) {
        return Customer.createCustomer(dto.getName(), dto.getLocale()
                , TimeUtil.getLocalDate(dto.getContractDate()), dto.getOwnerName());
    }
}
