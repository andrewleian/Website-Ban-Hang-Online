package com.example.assignment.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class NhanVienUpdateVO extends NhanVienVO implements Serializable {
    private static final long serialVersionUID = 1L;

}
