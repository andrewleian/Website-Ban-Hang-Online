package com.example.assignment.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GioHangChiTietQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer giohangchitietGiohang;

    private Integer giohangchitietChitietsanpham;

    private Integer soLuong;

    private BigDecimal donGia;

    private BigDecimal donGiaKhiGia;

}
