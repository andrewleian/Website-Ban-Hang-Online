package com.example.assignment.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class DongSanPhamQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String ma;

    private String ten;

}
