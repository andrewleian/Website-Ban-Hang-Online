package com.example.assignment.controller;

import com.example.assignment.dto.HoaDonDTO;
import com.example.assignment.dto.KhachHangDTO;
import com.example.assignment.dto.NhanVienDTO;
import com.example.assignment.service.HoaDonService;
import com.example.assignment.service.KhachHangService;
import com.example.assignment.service.NhanVienService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderHistoryController {
    @Autowired
    private HoaDonService service;
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private KhachHangService khachHangService;

    private Integer currentPage=0;
    private boolean isSearching = false;

    private String keySearch;

    @RequestMapping("/showOrderHistory")
    public String show(Model model, HttpSession session, HttpServletRequest request){
        isSearching= false;
        currentPage=0;
        Object vaiTro = session.getAttribute("vaiTro");

        if(vaiTro==null) {
            return "redirect:/showIndex";
        }
        if(vaiTro.toString().equals("KH")){
            Object object= session.getAttribute("user");
            KhachHangDTO khachHangDTO = new KhachHangDTO();
            BeanUtils.copyProperties(object,khachHangDTO);
            model.addAttribute("itemList", service.findAllByKhachHangID(khachHangDTO.getId(),currentPage));
            model.addAttribute("totalPage" , service.getTotalPage());
        }
        else if(vaiTro.toString().equals("NV")){
            Object object= session.getAttribute("user");
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            BeanUtils.copyProperties(object,nhanVienDTO);
            model.addAttribute("itemList", service.findAllByNhanVienID(nhanVienDTO.getId(),currentPage));
            model.addAttribute("totalPage" , service.getTotalPage());
        }

        return "orderhistory";

    }

    @RequestMapping("/showOrderHistory/page{p}")
    public String showPage(@PathVariable(value = "p") Integer p , Model model , HttpSession session){

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
        Object vaiTro = session.getAttribute("vaiTro");
        if(vaiTro.toString().equals("KH")){
            Object object= session.getAttribute("user");
            KhachHangDTO khachHangDTO = new KhachHangDTO();
            BeanUtils.copyProperties(object,khachHangDTO);
            model.addAttribute("itemList", service.findAllByKhachHangID(khachHangDTO.getId(),currentPage));
            model.addAttribute("totalPage" , service.getTotalPage());
        }
        else if(vaiTro.toString().equals("NV")){
            Object object= session.getAttribute("user");
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            BeanUtils.copyProperties(object,nhanVienDTO);
            model.addAttribute("itemList", service.findAllByNhanVienID(nhanVienDTO.getId(),currentPage));
            model.addAttribute("totalPage" , service.getTotalPage());
        }

        return "orderhistory";
    }
}
