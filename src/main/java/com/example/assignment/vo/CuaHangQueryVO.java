package com.example.assignment.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class CuaHangQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String ma;

    private String ten;

    private String diaChi;

    private String thanhPho;

    private String quocGia;

}
