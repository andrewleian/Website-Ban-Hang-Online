package com.example.assignment.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class ChucVuDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String ma;

    private String ten;

}
