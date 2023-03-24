package com.example.assignment.entity;


import lombok.Data;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "gio_hang_chi_tiet")
public class GioHangChiTiet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "giohangchitiet_giohang")
    private Integer giohangchitietGiohang;

    @Column(name = "giohangchitiet_chitietsanpham")
    private Integer giohangchitietChitietsanpham;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "don_gia")
    private BigDecimal donGia;

    @Column(name = "don_gia_khi_gia")
    private BigDecimal donGiaKhiGia;

    @ManyToOne
    @JoinColumn(name = "giohangchitiet_giohang", referencedColumnName = "id", insertable=false, updatable=false)
    private GioHang gioHangByGiohangchitietGiohang;
    @ManyToOne
    @JoinColumn(name = "giohangchitiet_chitietsanpham", referencedColumnName = "id", insertable=false, updatable=false)
    private ChiTietSanPham chiTietSanPhamByGiohangchitietChitietsanpham;
}
