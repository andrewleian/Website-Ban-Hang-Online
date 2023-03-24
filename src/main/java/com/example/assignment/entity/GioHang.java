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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.sql.Timestamp;
import java.util.Collection;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "gio_hang")
public class GioHang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "khachhang_id")
    private Integer khachhangId;

    @Column(name = "nhanvien_id")
    private Integer nhanvienId;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_thanh_toan")
    private Date ngayThanhToan;

    @Column(name = "ten_nguoi_nhan")
    private String tenNguoiNhan;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "tinh_trang")
    private Integer tinhTrang;

    @ManyToOne
    @JoinColumn(name = "khachhang_id", referencedColumnName = "id", insertable=false, updatable=false)
    private KhachHang khachHangByKhachhangId;
    @ManyToOne
    @JoinColumn(name = "nhanvien_id", referencedColumnName = "id", insertable=false, updatable=false)
    private NhanVien nhanVienByNhanvienId;
    @OneToMany(mappedBy = "gioHangByGiohangchitietGiohang")
    private Collection<GioHangChiTiet> gioHangChiTietsById;

}
