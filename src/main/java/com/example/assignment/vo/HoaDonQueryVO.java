package com.example.assignment.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HoaDonQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer nhanvienId;

    private Integer khachhangId;

    private String ma;

    private Date ngayTao;

    private Date ngayThanhToan;

    private Date ngayShip;

    private Date ngayNhan;

    private Integer tinhTrang;

    private String tenNguoiNhan;

    private String diaChi;

    private String sdt;

}
