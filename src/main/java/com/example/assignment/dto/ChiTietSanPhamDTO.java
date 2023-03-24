package com.example.assignment.dto;


import com.example.assignment.entity.DongSanPham;
import com.example.assignment.entity.MauSac;
import com.example.assignment.entity.Nsx;
import com.example.assignment.entity.SanPham;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ChiTietSanPhamDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer sanphamId;

    private Integer nsxId;

    private Integer mausacId;

    private Integer dongsanphamId;

    private Integer namBaoHanh;

    private String moTa;

    private Integer soLuongTon;

    private BigDecimal giaNhap;

    private BigDecimal giaBan;

    private String anh;

    private SanPham sanPhamBySanphamId;
    private Nsx nsxByNsxId;
    private MauSac mauSacByMausacId;
    private DongSanPham dongSanPhamByDongsanphamId;

}
