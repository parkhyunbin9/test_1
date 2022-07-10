package com.reco.prob.dto;

import com.reco.prob.utils.TimeUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CollectDateRequestDtoTest {

    @Test
    public void 날짜로_시간기준_수거범위_구하기() throws Exception {
        //given
        String inputDateStr = "2022-05-05";

        //when
        System.out.println(new CollectDateRequestDto(inputDateStr));
        System.out.println(new CollectDateRequestDto(inputDateStr).getCollectStart().toString());


        //then
    }

}