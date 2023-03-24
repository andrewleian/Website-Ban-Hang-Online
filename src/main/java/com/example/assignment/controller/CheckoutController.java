package com.example.assignment.controller;

import com.example.assignment.entity.GioHang;
import com.example.assignment.service.ChiTietSanPhamService;
import com.example.assignment.service.GioHangChiTietService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutController {
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    GioHangChiTietService gioHangChiTietService;


    @GetMapping("/showCheckout")
    public String show(Model model , HttpSession session){
        Object idGioHang = session.getAttribute("idGioHang");

        model.addAttribute("list" ,
                gioHangChiTietService.findByGioHang(Integer.parseInt(idGioHang.toString())));
        return "checkout";
    }
}
