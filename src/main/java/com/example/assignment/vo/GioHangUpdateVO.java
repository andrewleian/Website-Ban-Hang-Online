package com.example.assignment.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class GioHangUpdateVO extends GioHangVO implements Serializable {
    private static final long serialVersionUID = 1L;

}
