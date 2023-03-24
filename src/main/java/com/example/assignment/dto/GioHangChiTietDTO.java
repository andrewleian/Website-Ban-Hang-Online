package com.example.assignment.dto;


import com.example.assignment.entity.ChiTietSanPham;
import com.example.assignment.entity.GioHang;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GioHangChiTietDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer giohangchitietGiohang;

    private Integer giohangchitietChitietsanpham;

    private Integer soLuong;

    private BigDecimal donGia;

    private BigDecimal donGiaKhiGia;

    private GioHang gioHangByGiohangchitietGiohang;
    private ChiTietSanPham chiTietSanPhamByGiohangchitietChitietsanpham;

}
