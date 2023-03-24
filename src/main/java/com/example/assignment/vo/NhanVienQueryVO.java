package com.example.assignment.vo;


import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class NhanVienQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

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
