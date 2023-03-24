package com.example.assignment.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@Component
public class ChiTietSanPhamVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
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
