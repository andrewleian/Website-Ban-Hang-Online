package com.example.assignment.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class HoaDonChiTietQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer hoadonchitietHoadon;

    private Integer hoadonchitietChitietsanpham;

    private Integer soLuong;

    private BigDecimal donGia;

}
