package com.example.assignment.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;


@Data
public class KhachHangVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private Integer id;

    private String ma;

    private String hoTen;

    private Date ngaySinh;

    private String sdt;

    private String diaChi;

    private String matKhau;

}
