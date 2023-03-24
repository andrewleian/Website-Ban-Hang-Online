package com.example.assignment.dto;


import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class KhachHangDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String ma;

    private String hoTen;

    private Date ngaySinh;

    private String sdt;

    private String diaChi;

    private String matKhau;

}
