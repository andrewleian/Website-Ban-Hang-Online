package com.example.assignment.controller;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.GioHangChiTietDTO;
import com.example.assignment.service.ChiTietSanPhamService;
import com.example.assignment.service.DongSanPhamService;
import com.example.assignment.service.GioHangChiTietService;
import com.example.assignment.service.GioHangService;
import com.example.assignment.service.MauSacService;
import com.example.assignment.service.NsxService;
import com.example.assignment.service.SanPhamService;
import com.example.assignment.vo.ChiTietSanPhamVO;
import com.example.assignment.vo.GioHangChiTietVO;
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

@Controller
public class GioHangChiTietController {
    @Autowired
    private GioHangChiTietService service;
    @Autowired
    private GioHangService gioHangService;
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    private Integer currentPage=0;
    private boolean isSearching = false;

    private Integer keySearch;

    public void utilities(Model model){

    }

    public void followIsSearching(Model model){

    }
    @RequestMapping("/showGioHangChiTiet")
    public String show(Model model){
        isSearching= false;
        currentPage=0;
        model.addAttribute("itemList", service.findAllByPage(currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new GioHangChiTietDTO());
        model.addAttribute("gioHangList" ,gioHangService.findAll());
        model.addAttribute("chiTietSanPhamList" ,chiTietSanPhamService.findAll());

        return "crudgiohangchitiet";
    }

    @RequestMapping("/showGioHangChiTiet/page{p}")
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

        model.addAttribute("item" , new GioHangChiTietDTO());

        if(isSearching){
            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , service.getTotalPage());

        model.addAttribute("gioHangList" ,gioHangService.findAll());
        model.addAttribute("chiTietSanPhamList" ,chiTietSanPhamService.findAll());

        return "crudgiohangchitiet";
    }

    @PostMapping("/saveGioHangChiTiet")
    public String save(@Valid @ModelAttribute GioHangChiTietVO gioHangChiTietVO , BindingResult result , Model model){
        if(result.hasErrors()){
            for (FieldError err: result.getFieldErrors()) {
                model.addAttribute(err.getField(), err.getDefaultMessage());
            }
            if(isSearching){
                model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
            }else {
                model.addAttribute("itemList", service.findAllByPage(currentPage));
            }
            model.addAttribute("totalPage" , service.getTotalPage());
            model.addAttribute("item" , gioHangChiTietVO);

            model.addAttribute("gioHangList" ,gioHangService.findAll());
            model.addAttribute("chiTietSanPhamList" ,chiTietSanPhamService.findAll());

            return "crudgiohangchitiet";
        }
        service.save(gioHangChiTietVO);
        return "redirect:/showGioHangChiTiet/page"+currentPage;
    }

    @RequestMapping("/removeGioHangChiTiet/{id}")
    public String remove(@PathVariable("id") Integer id , Model model){
        service.delete(id);
        return "redirect:/showGioHangChiTiet/page"+currentPage;
    }

    @RequestMapping("/editGioHangChiTiet/{id}")
    public String edit(@PathVariable("id") Integer id , Model model){
        GioHangChiTietDTO dto = service.getById(id);
        if(isSearching){
            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , dto);

        model.addAttribute("gioHangList" ,gioHangService.findAll());
        model.addAttribute("chiTietSanPhamList" ,chiTietSanPhamService.findAll());
        return "crudgiohangchitiet";
    }

    @RequestMapping("/searchGioHangChiTiet")
    public String search(@RequestParam("keySearch") Integer keySearch, Model model ){
        isSearching=true;
        currentPage =0;
        this.keySearch = keySearch;
        model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new GioHangChiTietDTO());

        model.addAttribute("gioHangList" ,gioHangService.findAll());
        model.addAttribute("chiTietSanPhamList" ,chiTietSanPhamService.findAll());
        return "crudgiohangchitiet";
    }
}

