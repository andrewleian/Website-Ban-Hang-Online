package com.example.assignment.controller;

import com.example.assignment.dto.DongSanPhamDTO;
import com.example.assignment.dto.NsxDTO;
import com.example.assignment.service.DongSanPhamService;
import com.example.assignment.service.NsxService;
import com.example.assignment.vo.DongSanPhamVO;
import com.example.assignment.vo.NsxVO;
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
public class DongSanPhamController {
    @Autowired
    private DongSanPhamService dongSanPhamService;

    private Integer currentPage=0;
    private boolean isSearching = false;

    private String keySearch;

    public void utilities(Model model){

    }

    public void followIsSearching(Model model){

    }
    @RequestMapping("/showDongSanPham")
    public String show(Model model){
        isSearching= false;
        currentPage=0;
        model.addAttribute("listItem", dongSanPhamService.findAllByPage(currentPage));
        model.addAttribute("totalPage" , dongSanPhamService.getTotalPage());
        model.addAttribute("item" , new DongSanPhamDTO());
        return "cruddongsanpham";
    }

    @RequestMapping("/showDongSanPham/page{p}")
    public String showPage(@PathVariable(value = "p") Integer p , Model model){

        if(p== -1){
            if(currentPage > 0) {
                p = currentPage - 1;
            }else {
                p=0;
            }
        } else if (p== -2) {
            if(currentPage < dongSanPhamService.getTotalPage()){
                p= currentPage+1;
            }else{
                p= dongSanPhamService.getTotalPage();
            }
        }
        currentPage= p;

        model.addAttribute("item" , new DongSanPhamDTO());

        if(isSearching){
            model.addAttribute("listItem", dongSanPhamService.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("listItem", dongSanPhamService.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , dongSanPhamService.getTotalPage());

        return "cruddongsanpham";
    }

    @PostMapping("/saveDongSanPham")
    public String save(@Valid @ModelAttribute DongSanPhamVO dongSanPhamVO , BindingResult result , Model model){
        if(result.hasErrors()){
            for (FieldError err: result.getFieldErrors()) {
                model.addAttribute(err.getField(), err.getDefaultMessage());
            }
            if(isSearching){
                model.addAttribute("listItem", dongSanPhamService.findAllByKeySearch(keySearch,currentPage));
            }else {
                model.addAttribute("listItem", dongSanPhamService.findAllByPage(currentPage));
            }
            model.addAttribute("totalPage" , dongSanPhamService.getTotalPage());
            model.addAttribute("item" , dongSanPhamVO);

            return "cruddongsanpham";
        }
        dongSanPhamService.save(dongSanPhamVO);
        return "redirect:/showDongSanPham/page"+currentPage;
    }

    @RequestMapping("/removeDongSanPham/{id}")
    public String remove(@PathVariable("id") Integer id , Model model){
        dongSanPhamService.delete(id);
        return "redirect:/showDongSanPham/page"+currentPage;
    }

    @RequestMapping("/editDongSanPham/{id}")
    public String edit(@PathVariable("id") Integer id , Model model){
        DongSanPhamDTO dto = dongSanPhamService.getById(id);
        if(isSearching){
            model.addAttribute("listItem", dongSanPhamService.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("listItem", dongSanPhamService.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , dongSanPhamService.getTotalPage());
        model.addAttribute("item" , dto);
        return "cruddongsanpham";
    }

    @RequestMapping("/searchDongSanPham")
    public String search(@RequestParam("keySearch") String keySearch, Model model ){
        isSearching=true;
        currentPage =0;
        this.keySearch = keySearch;
        model.addAttribute("listItem", dongSanPhamService.findAllByKeySearch(keySearch,currentPage));
        model.addAttribute("totalPage" , dongSanPhamService.getTotalPage());
        model.addAttribute("item" , new DongSanPhamDTO());
        return "cruddongsanpham";
    }
}
