package com.reco.prob.domain.customer;

import com.reco.prob.utils.TimeUtil;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String locale;

    private LocalDate contractDate;

    @NonNull
    private String ownerName;

    @PrePersist
    @PreUpdate
    public void onPrePersist(){
        this.contractDate = TimeUtil.getLocalDateWithSimpleFormat(this.contractDate);
    }

    public static Customer createCustomer(String name, String locale, LocalDate contractDate, String ownerName) {
        return Customer.builder()
                .name(name)
                .locale(locale)
                .contractDate(contractDate)
                .ownerName(ownerName)
                .build();
    }
}
