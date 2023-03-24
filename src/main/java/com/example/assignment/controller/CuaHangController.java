package com.example.assignment.controller;

import com.example.assignment.dto.ChucVuDTO;
import com.example.assignment.dto.CuaHangDTO;
import com.example.assignment.dto.SanPhamDTO;
import com.example.assignment.service.ChucVuService;
import com.example.assignment.service.CuaHangService;
import com.example.assignment.service.SanPhamService;
import com.example.assignment.vo.ChucVuVO;
import com.example.assignment.vo.CuaHangVO;
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

@Controller
public class CuaHangController {
    @Autowired
    private CuaHangService service;

    private Integer currentPage=0;
    private boolean isSearching = false;

    private String keySearch;

    public void utilities(Model model){

    }

    public void followIsSearching(Model model){

    }
    @RequestMapping("/showCuaHang")
    public String show(Model model){
        isSearching= false;
        currentPage=0;
        model.addAttribute("itemList", service.findAllByPage(currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new CuaHangDTO());
        return "crudcuahang";
    }

    @RequestMapping("/showCuaHang/page{p}")
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

        model.addAttribute("item" , new CuaHangDTO());

        if(isSearching){
            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , service.getTotalPage());

        return "crudcuahang";
    }

    @PostMapping("/saveCuaHang")
    public String save(@Valid @ModelAttribute CuaHangVO cuaHangVO , BindingResult result , Model model){
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
            model.addAttribute("item" , cuaHangVO);

            return "crudcuahang";
        }
        service.save(cuaHangVO);
        return "redirect:/showCuaHang/page"+currentPage;
    }

    @RequestMapping("/removeCuaHang/{id}")
    public String remove(@PathVariable("id") Integer id , Model model){
        service.delete(id);
        return "redirect:/showCuaHang/page"+currentPage;
    }

    @RequestMapping("/editCuaHang/{id}")
    public String edit(@PathVariable("id") Integer id , Model model){
        CuaHangDTO dto = service.getById(id);
        if(isSearching){
            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , dto);
        return "crudcuahang";
    }

    @RequestMapping("/searchCuaHang")
    public String search(@RequestParam("keySearch") String keySearch, Model model ){
        isSearching=true;
        currentPage =0;
        this.keySearch = keySearch;
        model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new CuaHangDTO());
        return "crudCuaHang";
    }
}
