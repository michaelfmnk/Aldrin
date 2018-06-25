package com.michaelfmnk.aldrin.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ErrorDetail implements Serializable {
    private String title;
    private int status;
    private String detail;
    private long timeStamp;
    private String developerMessage;
}
