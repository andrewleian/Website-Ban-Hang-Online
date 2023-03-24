package com.example.assignment.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class SanPhamUpdateVO extends SanPhamVO implements Serializable {
    private static final long serialVersionUID = 1L;

}
