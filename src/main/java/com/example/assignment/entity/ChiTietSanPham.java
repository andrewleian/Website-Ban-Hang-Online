package com.example.assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.io.Serializable;
import java.math.BigDecimal;

//@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chi_tiet_san_pham")
public class ChiTietSanPham implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sanpham_id")
    private Integer sanphamId;

    @Column(name = "nsx_id" )
    private Integer nsxId;

    @Column(name = "mausac_id")
    private Integer mausacId;

    @Column(name = "dongsanpham_id")
    private Integer dongsanphamId;

    @Column(name = "nam_bao_hanh")
    private Integer namBaoHanh;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "so_luong_ton")
    private Integer soLuongTon;

    @Column(name = "gia_nhap")
    private BigDecimal giaNhap;

    @Column(name = "gia_ban")
    private BigDecimal giaBan;

    @Column(name = "anh")
    private String anh;

    @ManyToOne
    @JoinColumn(name = "sanpham_id", referencedColumnName = "id", insertable=false, updatable=false)
    private SanPham sanPhamBySanphamId;
    @ManyToOne
    @JoinColumn(name = "nsx_id", referencedColumnName = "id", insertable=false, updatable=false)
    private Nsx nsxByNsxId;
    @ManyToOne
    @JoinColumn(name = "mausac_id", referencedColumnName = "id", insertable=false, updatable=false)
    private MauSac mauSacByMausacId;
    @ManyToOne
    @JoinColumn(name = "dongsanpham_id", referencedColumnName = "id", insertable=false, updatable=false)
    private DongSanPham dongSanPhamByDongsanphamId;
    @OneToMany(mappedBy = "chiTietSanPhamByGiohangchitietChitietsanpham")
    private Collection<GioHangChiTiet> gioHangChiTietsById;
    @OneToMany(mappedBy = "chiTietSanPhamByHoadonchitietChitietsanpham")
    private Collection<HoaDonChiTiet> hoaDonChiTietsById;

}
