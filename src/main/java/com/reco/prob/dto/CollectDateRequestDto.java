package com.reco.prob.dto;

import com.reco.prob.utils.TimeUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CollectDateRequestDto {


    @NotEmpty(message = "수거 날짜를 입력해주셔야 합니다.")
    private String collectDate;

    private LocalDateTime collectStart;
    private LocalDateTime collectEnd;



    public CollectDateRequestDto(String collectDate) throws IllegalArgumentException{
        try{
            LocalDate inputDate = LocalDate.parse(collectDate, TimeUtil.SIMPLE_DATE_FORMATTER);
            if(!inputDate.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("수거시간은 과거여야 합니다.");
            }else {
                this.collectDate = collectDate;
                this.collectStart = TimeUtil.getLocalDateTimeWithSimpleFormat(inputDate.atStartOfDay());
                this.collectEnd = TimeUtil.getLocalDateTimeWithSimpleFormat(inputDate.plusDays(1).atStartOfDay());
            }
        }catch (Exception e ) {
            throw new IllegalArgumentException("시간 포맷이 적절하지 않습니다.");
        }

    }
}
