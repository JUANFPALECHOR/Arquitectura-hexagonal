package com.pragma.usuarios.configuration.exceptionhandler;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private String details;
    private Date timestamp;

}
