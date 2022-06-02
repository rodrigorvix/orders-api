package com.letscode.ordersapi.Request;

import lombok.Data;

@Data
public class UserRequest {

    private Long id;
    private String name;
    private String profile;
    private  String birthDate;
}
