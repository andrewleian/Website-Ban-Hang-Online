package com.example.assignment.controller;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.KhachHangDTO;
import com.example.assignment.dto.NhanVienDTO;
import com.example.assignment.service.ChiTietSanPhamService;
import com.example.assignment.service.KhachHangService;
import com.example.assignment.service.NhanVienService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    NhanVienService nhanVienService;
    @Autowired
    KhachHangService khachHangService;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;

    @GetMapping("/showLogin")
    public String show(Model model){
        return "login";
    }

    @PostMapping ("/signIn")
    public String signIn(String username , String password, Model model , HttpSession session){
        KhachHangDTO khachHangDTO = khachHangService.findByUsernameAndPassword(username,password);
        NhanVienDTO nhanVienDTO = nhanVienService.findByUsernameAndPassword(username , password);
        if(khachHangDTO != null){
            session.setAttribute("vaiTro" , "KH");
            session.setAttribute("user" , khachHangDTO);
            return "redirect:/showIndex";
        } else if (nhanVienDTO != null) {
            session.setAttribute("vaiTro" , "NV");
            session.setAttribute("user" , nhanVienDTO);
            return "redirect:/showIndex";
        }
        model.addAttribute("infor" , "Tài khoản hoặc mật khẩu không chính xác");
        return "login";
    }

    @RequestMapping("/signOut")
    public String signIn(Model model , HttpSession session){
        session.removeAttribute("vaiTro");
        session.removeAttribute("user");
        session.removeAttribute("idGioHang");
        return "redirect:/showIndex";
    }
}
