package com.example.assignment.controller;

import com.example.assignment.dto.KhachHangDTO;
import com.example.assignment.service.KhachHangService;
import com.example.assignment.vo.KhachHangVO;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Controller
public class ForgotPasswordController {
    @Autowired
    KhachHangService khachHangService;
    @RequestMapping("/showForgotPass")
    public String showForgotPass(Model model){
//        model.addAttribute("itemKH" , new KhachHangDTO());
        return "forgotpassword";
    }

    @PostMapping("/submitForgotPass")
    public String showForgotPass( @ModelAttribute KhachHangVO khachHangVO, Model model){
//        KhachHangVO khachHangVO = new KhachHangVO();
//        BeanUtils.copyProperties(khachHangDTO,khachHangVO);

        KhachHangDTO khachHangDTO = khachHangService.checkForgotPass(khachHangVO);


        if(khachHangDTO != null){
            BeanUtils.copyProperties(khachHangDTO , khachHangVO);
            Random random = new Random();
            int ranNum = random.nextInt(10000000);
            khachHangVO.setMatKhau(ranNum+"");
            khachHangService.save(khachHangVO);

            model.addAttribute("mess", "Mật khẩu mới của bạn là: '"+ranNum+"' . Vui lòng đăng nhập và đổi mật khẩu"  );

        }else {
            model.addAttribute("mess","Sai thông tin");
        }
        return "forgotpassword";
    }
}
