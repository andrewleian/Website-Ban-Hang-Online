package com.example.assignment.controller;

import com.example.assignment.dto.SanPhamDTO;
import com.example.assignment.service.SanPhamService;
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
public class SanPhamController {
        @Autowired
        private SanPhamService sanPhamService;

        private Integer currentPage=0;
        private boolean isSearching = false;

        private String keySearch;

        public void utilities(Model model){

        }

        public void followIsSearching(Model model){

        }
        @RequestMapping("/showSanPham")
        public String show(Model model){
            isSearching= false;
            currentPage=0;
            model.addAttribute("listItem", sanPhamService.findAllByPage(currentPage));
            model.addAttribute("totalPage" , sanPhamService.getTotalPage());
            model.addAttribute("item" , new SanPhamDTO());
            return "crudsanpham";
        }

        @RequestMapping("/showSanPham/page{p}")
        public String showPage(@PathVariable(value = "p") Integer p , Model model){

            if(p== -1){
                if(currentPage > 0) {
                    p = currentPage - 1;
                }else {
                    p=0;
                }
            } else if (p== -2) {
                if(currentPage < sanPhamService.getTotalPage()){
                    p= currentPage+1;
                }else{
                    p= sanPhamService.getTotalPage();
                }
            }
            currentPage= p;

            model.addAttribute("item" , new SanPhamDTO());

            if(isSearching){
                model.addAttribute("listItem", sanPhamService.findAllByKeySearch(keySearch,currentPage));
            }else {
                model.addAttribute("listItem", sanPhamService.findAllByPage(currentPage));
            }
            model.addAttribute("totalPage" , sanPhamService.getTotalPage());

            return "crudsanpham";
        }

        @PostMapping("/saveSanPham")
        public String save(@Valid @ModelAttribute SanPhamVO sanPhamVO , BindingResult result , Model model){
            if(result.hasErrors()){
                for (FieldError err: result.getFieldErrors()) {
                    model.addAttribute(err.getField(), err.getDefaultMessage());
                }
                if(isSearching){
                    model.addAttribute("listItem", sanPhamService.findAllByKeySearch(keySearch,currentPage));
                }else {
                    model.addAttribute("listItem", sanPhamService.findAllByPage(currentPage));
                }
                model.addAttribute("totalPage" , sanPhamService.getTotalPage());
                model.addAttribute("item" , sanPhamVO);

                return "crudsanpham";
            }
            sanPhamService.save(sanPhamVO);
            return "redirect:/showSanPham/page"+currentPage;
        }

        @RequestMapping("/removeSanPham/{id}")
        public String remove(@PathVariable("id") Integer id , Model model){
            sanPhamService.delete(id);
            return "redirect:/showSanPham/page"+currentPage;
        }

        @RequestMapping("/editSanPham/{id}")
        public String edit(@PathVariable("id") Integer id , Model model){
            SanPhamDTO dto = sanPhamService.getById(id);
            if(isSearching){
                model.addAttribute("listItem", sanPhamService.findAllByKeySearch(keySearch,currentPage));
            }else {
                model.addAttribute("listItem", sanPhamService.findAllByPage(currentPage));
            }
            model.addAttribute("totalPage" , sanPhamService.getTotalPage());
            model.addAttribute("item" , dto);
            return "crudsanpham";
        }

        @RequestMapping("/searchSanPham")
        public String search(@RequestParam("keySearch") String keySearch, Model model ){
            isSearching=true;
            currentPage =0;
            this.keySearch = keySearch;
            model.addAttribute("listItem", sanPhamService.findAllByKeySearch(keySearch,currentPage));
            model.addAttribute("totalPage" , sanPhamService.getTotalPage());
            model.addAttribute("item" , new SanPhamDTO());
            return "crudsanpham";
        }
}

