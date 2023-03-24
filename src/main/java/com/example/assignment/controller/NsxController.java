package com.example.assignment.controller;

import com.example.assignment.dto.NsxDTO;
import com.example.assignment.dto.SanPhamDTO;
import com.example.assignment.service.NsxService;
import com.example.assignment.service.SanPhamService;
import com.example.assignment.vo.NsxVO;
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
public class NsxController {
    @Autowired
    private NsxService nsxService;

    private Integer currentPage=0;
    private boolean isSearching = false;

    private String keySearch;

    public void utilities(Model model){

    }

    public void followIsSearching(Model model){

    }
    @RequestMapping("/showNSX")
    public String show(Model model){
        isSearching= false;
        currentPage=0;
        model.addAttribute("listItem", nsxService.findAllByPage(currentPage));
        model.addAttribute("totalPage" , nsxService.getTotalPage());
        model.addAttribute("item" , new NsxDTO());
        return "crudnsx";
    }

    @RequestMapping("/showNSX/page{p}")
    public String showPage(@PathVariable(value = "p") Integer p , Model model){

        if(p== -1){
            if(currentPage > 0) {
                p = currentPage - 1;
            }else {
                p=0;
            }
        } else if (p== -2) {
            if(currentPage < nsxService.getTotalPage()){
                p= currentPage+1;
            }else{
                p= nsxService.getTotalPage();
            }
        }
        currentPage= p;

        model.addAttribute("item" , new NsxDTO());

        if(isSearching){
            model.addAttribute("listItem", nsxService.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("listItem", nsxService.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , nsxService.getTotalPage());

        return "crudnsx";
    }

    @PostMapping("/saveNSX")
    public String save(@Valid @ModelAttribute NsxVO nsxVO , BindingResult result , Model model){
        if(result.hasErrors()){
            for (FieldError err: result.getFieldErrors()) {
                model.addAttribute(err.getField(), err.getDefaultMessage());
            }
            if(isSearching){
                model.addAttribute("listItem", nsxService.findAllByKeySearch(keySearch,currentPage));
            }else {
                model.addAttribute("listItem", nsxService.findAllByPage(currentPage));
            }
            model.addAttribute("totalPage" , nsxService.getTotalPage());
            model.addAttribute("item" , nsxVO);

            return "crudnsx";
        }
        nsxService.save(nsxVO);
        return "redirect:/showNSX/page"+currentPage;
    }

    @RequestMapping("/removeNSX/{id}")
    public String remove(@PathVariable("id") Integer id , Model model){
        nsxService.delete(id);
        return "redirect:/showNSX/page"+currentPage;
    }

    @RequestMapping("/editNSX/{id}")
    public String edit(@PathVariable("id") Integer id , Model model){
        NsxDTO dto = nsxService.getById(id);
        if(isSearching){
            model.addAttribute("listItem", nsxService.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("listItem", nsxService.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , nsxService.getTotalPage());
        model.addAttribute("item" , dto);
        return "crudnsx";
    }

    @RequestMapping("/searchNSX")
    public String search(@RequestParam("keySearch") String keySearch, Model model ){
        isSearching=true;
        currentPage =0;
        this.keySearch = keySearch;
        model.addAttribute("listItem", nsxService.findAllByKeySearch(keySearch,currentPage));
        model.addAttribute("totalPage" , nsxService.getTotalPage());
        model.addAttribute("item" , new NsxDTO());
        return "crudnsx";
    }
}

