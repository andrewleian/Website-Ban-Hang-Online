package com.example.assignment.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class HoaDonChiTietVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private Integer id;

    private Integer hoadonchitietHoadon;

    private Integer hoadonchitietChitietsanpham;

    private Integer soLuong;

    private BigDecimal donGia;

}
