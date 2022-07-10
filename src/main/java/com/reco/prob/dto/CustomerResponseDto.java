package com.reco.prob.dto;

import com.reco.prob.domain.history.CollectHistory;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CustomerResponseDto {

    private String name;
    private String locale;
    private int quantaty;
    private int canCount;

    public CustomerResponseDto(CollectHistory history) {
        CustomerResponseDto.builder()
                .name(history.getCustomer().getName())
                .locale(history.getCustomer().getLocale())
                .quantaty(history.getCollect().getQuantaty())
                .canCount(history.getCollect().getCanCount())
                .build();
    }

}
