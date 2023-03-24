package com.example.assignment.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class GioHangChiTietVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private Integer id;

    private Integer giohangchitietGiohang;

    private Integer giohangchitietChitietsanpham;

    private Integer soLuong;

    private BigDecimal donGia;

    private BigDecimal donGiaKhiGia;

}
