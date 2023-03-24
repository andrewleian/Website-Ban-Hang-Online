package com.example.assignment.controller;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.GioHangDTO;
import com.example.assignment.service.ChiTietSanPhamService;
import com.example.assignment.service.DongSanPhamService;
import com.example.assignment.service.GioHangService;
import com.example.assignment.service.KhachHangService;
import com.example.assignment.service.MauSacService;
import com.example.assignment.service.NhanVienService;
import com.example.assignment.service.NsxService;
import com.example.assignment.service.SanPhamService;
import com.example.assignment.vo.ChiTietSanPhamVO;
import com.example.assignment.vo.GioHangVO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
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

@Controller
public class GioHangController {
    @Autowired
    private GioHangService service;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private NhanVienService nhanVienService;

    private Integer currentPage=0;
    private boolean isSearching = false;

    private String keySearch;

    public void utilities(Model model){

    }

    public void followIsSearching(Model model){

    }
    @RequestMapping("/showGioHang")
    public String show(Model model){
        isSearching= false;
        currentPage=0;
        model.addAttribute("itemList", service.findAllByPage(currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new GioHangDTO());
        model.addAttribute("khachHangList" ,khachHangService.findAll());
        model.addAttribute("nhanVienList" ,nhanVienService.findAll());
        return "crudgiohang";
    }

    @RequestMapping("/showGioHang/page{p}")
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

        model.addAttribute("item" , new GioHangDTO());

        if(isSearching){
            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , service.getTotalPage());

        model.addAttribute("khachHangList" ,khachHangService.findAll());
        model.addAttribute("nhanVienList" ,nhanVienService.findAll());

        return "crudgiohang";
    }

    @PostMapping("/saveGioHang")
    public String save(@Valid @ModelAttribute GioHangVO gioHangVO , BindingResult result , Model model){
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
//            model.addAttribute("item" , gioHangVO);
//
//            model.addAttribute("khachHangList" ,khachHangService.findAll());
//            model.addAttribute("nhanVienList" ,nhanVienService.findAll());
//
//            return "crudgiohang";
//        }
        service.save(gioHangVO);
        return "redirect:/showGioHang/page"+currentPage;
    }

    @RequestMapping("/clearGioHang")
    public String clearGioHang(HttpSession session, Model model){
        Object idCart = session.getAttribute("idGioHang");
        GioHangDTO gioHangDTO = service.getById(Integer.parseInt(idCart.toString()));
        GioHangVO gioHangVO = new GioHangVO();
        BeanUtils.copyProperties(gioHangDTO , gioHangVO);
        gioHangVO.setTinhTrang(1);
        service.save(gioHangVO);
        session.setAttribute("orderSuccess", "Chúc mừng bạn đã đặt hàng thành công." +
                " Nhân viên chăm sóc khách hàng của chúng tôi sẽ sớm liên lạc tới bạn để xác nhận");
        return "redirect:/showIndex";
    }

    @RequestMapping("/removeGioHang/{id}")
    public String remove(@PathVariable("id") Integer id , Model model){
        service.delete(id);
        return "redirect:/showGioHang/page"+currentPage;
    }

    @RequestMapping("/editGioHang/{id}")
    public String edit(@PathVariable("id") Integer id , Model model){
        GioHangDTO dto = service.getById(id);
        if(isSearching){
            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , dto);

        model.addAttribute("khachHangList" ,khachHangService.findAll());
        model.addAttribute("nhanVienList" ,nhanVienService.findAll());
        return "crudgiohang";
    }

    @RequestMapping("/searchGioHang")
    public String search(@RequestParam("keySearch") String keySearch, Model model ){
        isSearching=true;
        currentPage =0;
        this.keySearch = keySearch;
        model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new GioHangDTO());

        model.addAttribute("khachHangList" ,khachHangService.findAll());
        model.addAttribute("nhanVienList" ,nhanVienService.findAll());
        return "crudgiohang";
    }
}


