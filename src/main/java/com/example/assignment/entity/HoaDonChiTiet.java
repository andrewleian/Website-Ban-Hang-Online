package com.example.assignment.entity;


import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "hoa_don_chi_tiet")
public class HoaDonChiTiet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "hoadonchitiet_hoadon")
    private Integer hoadonchitietHoadon;

    @Column(name = "hoadonchitiet_chitietsanpham")
    private Integer hoadonchitietChitietsanpham;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "don_gia")
    private BigDecimal donGia;

    @ManyToOne
    @JoinColumn(name = "hoadonchitiet_hoadon", referencedColumnName = "id", insertable=false, updatable=false)
    private HoaDon hoaDonByHoadonchitietHoadon;
    @ManyToOne
    @JoinColumn(name = "hoadonchitiet_chitietsanpham", referencedColumnName = "id", insertable=false, updatable=false)
    private ChiTietSanPham chiTietSanPhamByHoadonchitietChitietsanpham;
}
