package com.example.assignment.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GioHangQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer khachhangId;

    private Integer nhanvienId;

    private String ma;

    private Date ngayTao;

    private Date ngayThanhToan;

    private String tenNguoiNhan;

    private String diaChi;

    private String sdt;

    private Integer tinhTrang;

}
