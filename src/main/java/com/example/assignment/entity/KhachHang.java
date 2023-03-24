package com.example.assignment.entity;


import lombok.Data;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.sql.Date;
import java.util.Collection;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "khach_hang")
public class KhachHang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "ngay_sinh")
    private Date ngaySinh;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "mat_khau")
    private String matKhau;

    @OneToMany(mappedBy = "khachHangByKhachhangId")
    private Collection<GioHang> gioHangsById;
    @OneToMany(mappedBy = "khachHangByKhachhangId")
    private Collection<HoaDon> hoaDonsById;

}
