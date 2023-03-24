package com.example.assignment.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Data
@Component
public class SanPhamVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private Integer id;

    private String ma;

    private String ten;

}
