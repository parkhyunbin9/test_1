package com.reco.prob.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;
class CollectTest {

    @DisplayName("수거통은 음수가 될수 없다.")
    @Test
    public void 수거통_테스트() throws Exception {
        //given
        int zeroCanCount = 0;
        int negativeCanCount = -1;
        int quantatiy = 1;

        //when
        //then
        assertDoesNotThrow(() ->new Collect(zeroCanCount, quantatiy), "수거 통은 음수가 될수 없습니다.");
        assertThrows(InvalidParameterException.class,() ->new Collect(negativeCanCount, quantatiy), "수거 통은 음수가 될수 없습니다.");
    }

    @DisplayName("수거량은 0보다 커야한다.")
    @Test
    public void 수거량_테스트() throws Exception {
        //given
        int canCount = 1;
        int negativeQuantaty = -1;
        int zeroQuantaty = 0;
        //when
        //then
        assertThrows(InvalidParameterException.class,() ->new Collect(canCount, zeroQuantaty), "수거량은 0보다 커야합니다.");
        assertThrows(InvalidParameterException.class,() ->new Collect(canCount, negativeQuantaty), "수거량은 0보다 커야합니다.");
    }


}