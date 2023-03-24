package com.example.assignment.controller;

import com.example.assignment.dto.ChucVuDTO;
import com.example.assignment.dto.KhachHangDTO;
import com.example.assignment.dto.SanPhamDTO;
import com.example.assignment.service.ChucVuService;
import com.example.assignment.service.KhachHangService;
import com.example.assignment.service.SanPhamService;
import com.example.assignment.vo.ChucVuVO;
import com.example.assignment.vo.KhachHangVO;
import com.example.assignment.vo.SanPhamVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

@Controller
public class KhachHangController {
    @Autowired
    private KhachHangService service;

    private Integer currentPage=0;
    private boolean isSearching = false;

    private String keySearch;

    public void utilities(Model model){

    }

    public void followIsSearching(Model model){

    }
    @RequestMapping("/showKhachHang")
    public String show(Model model){
        isSearching= false;
        currentPage=0;
        model.addAttribute("itemList", service.findAllByPage(currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new KhachHangDTO());
        return "crudkhachhang";
    }

    @RequestMapping("/showKhachHang/page{p}")
    public String showPage(@PathVariable(value = "p") Integer p , Model model){

        if(p== -1){
            if(currentPage > 0) {
                p = currentPage - 1;
            }else {
                p=0;
            }
        } else if (p== -2) {
            if(currentPage < service.getTotalPage()){
                p= currentPage+1;
            }else{
                p= service.getTotalPage();
            }
        }
        currentPage= p;

        model.addAttribute("item" , new KhachHangDTO());

        if(isSearching){
            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , service.getTotalPage());

        return "crudkhachhang";
    }

    @PostMapping("/saveKhachHang")
    public String save(@Valid @ModelAttribute KhachHangVO khachHangVO , BindingResult result , Model model){
//       khachHangVO.setNgaySinh(null);
//        if(result.hasErrors()){
//            for (FieldError err: result.getFieldErrors()) {
//                model.addAttribute(err.getField(), err.getDefaultMessage());
//            }
//            if(isSearching){
//                model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
//            }else {
//                model.addAttribute("itemList", service.findAllByPage(currentPage));
//            }
//            model.addAttribute("totalPage" , service.getTotalPage());
//            model.addAttribute("item" , khachHangVO);
//            return "crudkhachhang";
//        }
        service.save(khachHangVO);
        return "redirect:/showKhachHang/page"+currentPage;
    }

    @RequestMapping("/showSignUp")
    public String showSignUp(Model model){
        model.addAttribute("item" , new KhachHangDTO());
        return "signup";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid @ModelAttribute KhachHangVO khachHangVO , BindingResult result , Model model){
        service.save(khachHangVO);
        model.addAttribute("item" , new KhachHangDTO());
        model.addAttribute("infor" , "Chúc mừng bạn đã đăng ký thành công");
        return "signup";
    }

    @RequestMapping("/removeKhachHang/{id}")
    public String remove(@PathVariable("id") Integer id , Model model){
        service.delete(id);
        return "redirect:/showKhachHang/page"+currentPage;
    }

    @RequestMapping("/editKhachHang/{id}")
    public String edit(@PathVariable("id") Integer id , Model model){
        KhachHangDTO dto = service.getById(id);
        if(isSearching){
            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , dto);
        return "crudkhachhang";
    }

    @RequestMapping("/searchKhachHang")
    public String search(@RequestParam("keySearch") String keySearch, Model model ){
        isSearching=true;
        currentPage =0;
        this.keySearch = keySearch;
        model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new KhachHangDTO());
        return "crudkhachhang";
    }
}
