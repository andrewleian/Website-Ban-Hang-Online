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

import java.sql.Date;
import java.util.Collection;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "nhan_vien")
public class NhanVien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "gioi_tinh")
    private String gioiTinh;

    @Column(name = "ngay_sinh")
    private Date ngaySinh;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "cuahang_id")
    private Integer cuahangId;

    @Column(name = "chucvu_id")
    private Integer chucvuId;

    @Column(name = "trangthai")
    private Integer trangthai;

    @OneToMany(mappedBy = "nhanVienByNhanvienId")
    private Collection<GioHang> gioHangsById;
    @OneToMany(mappedBy = "nhanVienByNhanvienId")
    private Collection<HoaDon> hoaDonsById;
    @ManyToOne
    @JoinColumn(name = "cuahang_id", referencedColumnName = "id", insertable=false, updatable=false)
    private CuaHang cuaHangByCuahangId;
    @ManyToOne
    @JoinColumn(name = "chucvu_id", referencedColumnName = "id", insertable=false, updatable=false)
    private ChucVu chucVuByChucvuId;

}
