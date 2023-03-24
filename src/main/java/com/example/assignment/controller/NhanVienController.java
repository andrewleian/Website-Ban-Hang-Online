package com.example.assignment.controller;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.NhanVienDTO;
import com.example.assignment.service.ChiTietSanPhamService;
import com.example.assignment.service.ChucVuService;
import com.example.assignment.service.CuaHangService;
import com.example.assignment.service.DongSanPhamService;
import com.example.assignment.service.MauSacService;
import com.example.assignment.service.NhanVienService;
import com.example.assignment.service.NsxService;
import com.example.assignment.service.SanPhamService;
import com.example.assignment.vo.ChiTietSanPhamVO;
import com.example.assignment.vo.NhanVienVO;
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
public class NhanVienController {
    @Autowired
    private NhanVienService service;
    @Autowired
    private CuaHangService cuaHangService;
    @Autowired
    private ChucVuService chucVuService;
    private Integer currentPage=0;
    private boolean isSearching = false;

    private String keySearch;

    public void utilities(Model model){

    }

    public void followIsSearching(Model model){

    }
    @RequestMapping("/showNhanVien")
    public String show(Model model){
        isSearching= false;
        currentPage=0;
        model.addAttribute("itemList", service.findAllByPage(currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new NhanVienDTO());
        model.addAttribute("cuaHangList" ,cuaHangService.findAll());
        model.addAttribute("chucVuList" ,chucVuService.findAll());
        return "crudnhanvien";
    }

    @RequestMapping("/showNhanVien/page{p}")
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

        model.addAttribute("item" , new NhanVienDTO());

        if(isSearching){
            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , service.getTotalPage());

        model.addAttribute("cuaHangList" ,cuaHangService.findAll());
        model.addAttribute("chucVuList" ,chucVuService.findAll());

        return "crudnhanvien";
    }

    @PostMapping("/saveNhanVien")
    public String save(@Valid @ModelAttribute NhanVienVO nhanVienVO , BindingResult result , Model model){
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
//            model.addAttribute("item" , nhanVienVO);
//
//            model.addAttribute("cuaHangList" ,cuaHangService.findAll());
//            model.addAttribute("chucVuList" ,chucVuService.findAll());
//
//            return "crudnhanvien";
//        }
        service.save(nhanVienVO);
        return "redirect:/showNhanVien/page"+currentPage;
    }

    @RequestMapping("/removeNhanVien/{id}")
    public String remove(@PathVariable("id") Integer id , Model model){
        service.delete(id);
        return "redirect:/showNhanVien/page"+currentPage;
    }

    @RequestMapping("/editNhanVien/{id}")
    public String edit(@PathVariable("id") Integer id , Model model){
        NhanVienDTO dto = service.getById(id);
        if(isSearching){
            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , dto);

        model.addAttribute("cuaHangList" ,cuaHangService.findAll());
        model.addAttribute("chucVuList" ,chucVuService.findAll());
        return "crudnhanvien";
    }

    @RequestMapping("/searchNhanVien")
    public String search(@RequestParam("keySearch") String keySearch, Model model ){
        isSearching=true;
        currentPage =0;
        this.keySearch = keySearch;
        model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new NhanVienDTO());

        model.addAttribute("cuaHangList" ,cuaHangService.findAll());
        model.addAttribute("chucVuList" ,chucVuService.findAll());
        return "crudnhanvien";
    }
}



