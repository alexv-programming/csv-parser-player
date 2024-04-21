package com.exam.csvparser.dtos;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Data
public class BallDontliePlayerDto implements Serializable {
    private int id;
    private String first_name;
    private String last_name;
}
