package com.example.assignment.dto;


import com.example.assignment.entity.ChiTietSanPham;
import com.example.assignment.entity.HoaDon;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class HoaDonChiTietDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer hoadonchitietHoadon;

    private Integer hoadonchitietChitietsanpham;

    private Integer soLuong;

    private BigDecimal donGia;
    private HoaDon hoaDonByHoadonchitietHoadon;
    private ChiTietSanPham chiTietSanPhamByHoadonchitietChitietsanpham;
}
