package com.exam.csvparser.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Data
public class BallDontliePlayerResponse implements Serializable {
    private BallDontliePlayerDto data;

}
