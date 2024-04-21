package com.exam.csvparser.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Player implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String firstName;
    private String lastName;

}
