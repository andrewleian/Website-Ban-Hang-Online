package com.example.assignment.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;


@Data
public class NhanVienVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private Integer id;

    private String ma;

    private String hoTen;

    private String gioiTinh;

    private Date ngaySinh;

    private String diaChi;

    private String matKhau;

    private Integer cuahangId;

    private Integer chucvuId;

    private Integer trangthai;

}
