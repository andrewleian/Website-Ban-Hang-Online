package com.example.assignment.controller;

import com.example.assignment.service.KhachHangService;
import com.example.assignment.service.NhanVienService;
import com.example.assignment.vo.KhachHangVO;
import com.example.assignment.vo.NhanVienVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChangePasswordController {
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private KhachHangService khachHangService;


    @PostMapping ("/changePassword")
    public String changePassword(Model model , HttpSession session , String oldPass , String newPass){
        Object vaiTro = session.getAttribute("vaiTro");
        Object user = session.getAttribute("user");
        System.out.println(oldPass);
        System.out.println(newPass);
        if(vaiTro.toString().equals("KH")){
            KhachHangVO khachHangVO = new KhachHangVO();
            BeanUtils.copyProperties(user , khachHangVO);
            if (khachHangVO.getMatKhau().equals(oldPass)){
                khachHangVO.setMatKhau(newPass);
                khachHangService.save(khachHangVO);
                model.addAttribute("message" , "Chúc mừng bạn đã đổi mật khẩu thành công");
                return "changepassword";
            }else {
                model.addAttribute("message" , "Bạn đã nhập sai mật khẩu");
                return "changepassword";
            }
        }else {
            NhanVienVO nhanVienVO = new NhanVienVO();
            BeanUtils.copyProperties(user , nhanVienVO);
            if (nhanVienVO.getMatKhau().equals(oldPass)){
                nhanVienVO.setMatKhau(newPass);
                nhanVienService.save(nhanVienVO);
                model.addAttribute("message" , "Chúc mừng bạn đã đổi mật khẩu thành công");
                return "changepassword";
            }else {
                model.addAttribute("message" , "Bạn đã nhập sai mật khẩu");
                return "changepassword";
            }
        }

    }

    @RequestMapping("/showChangePassword")
    public String showChangePassword(Model model){
        return "changepassword";
    }
}
