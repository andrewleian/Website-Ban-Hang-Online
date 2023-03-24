package com.example.assignment.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class HoaDonVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
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
