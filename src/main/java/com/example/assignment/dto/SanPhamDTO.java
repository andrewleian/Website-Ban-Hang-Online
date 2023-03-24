package com.example.assignment.dto;


import com.example.assignment.entity.ChiTietSanPham;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
public class SanPhamDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String ma;

    private String ten;

    private List<ChiTietSanPham> chiTietSanPhamsById;

}
