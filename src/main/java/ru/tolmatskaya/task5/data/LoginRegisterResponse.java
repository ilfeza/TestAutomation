package ru.tolmatskaya.task5.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LoginRegisterResponse {
    private int id;
    private String token;
    private String error;
}