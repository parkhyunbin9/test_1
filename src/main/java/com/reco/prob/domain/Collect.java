package com.reco.prob.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.security.InvalidParameterException;

@Embeddable
@Getter
public class Collect {
    private int canCount = 0;
    private int quantaty = 0;

    protected Collect() {
    }

    public Collect(int canCount, int quantaty) {
        if(canCount < 0 ) {
            throw new InvalidParameterException("수거통은 음수가 될수 없습니다.");
        }else if(quantaty <= 0 ) {
            throw new InvalidParameterException("수거량은 0보다 커야합니다.");
        } else {
            this.canCount = canCount;
            this.quantaty = quantaty;
        }
    }
}
