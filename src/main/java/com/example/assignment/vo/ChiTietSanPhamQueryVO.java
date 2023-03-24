package com.example.assignment.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ChiTietSanPhamQueryVO implements Serializable {
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

}
