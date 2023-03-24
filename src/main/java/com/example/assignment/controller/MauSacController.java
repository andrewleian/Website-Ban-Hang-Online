package com.example.assignment.controller;

import com.example.assignment.dto.MauSacDTO;
import com.example.assignment.dto.NsxDTO;
import com.example.assignment.service.MauSacService;
import com.example.assignment.service.NsxService;
import com.example.assignment.vo.MauSacVO;
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
public class MauSacController {
    @Autowired
    private MauSacService mauSacService;

    private Integer currentPage=0;
    private boolean isSearching = false;

    private String keySearch;

    public void utilities(Model model){

    }

    public void followIsSearching(Model model){

    }
    @RequestMapping("/showMauSac")
    public String show(Model model){
        isSearching= false;
        currentPage=0;
        model.addAttribute("listItem", mauSacService.findAllByPage(currentPage));
        model.addAttribute("totalPage" , mauSacService.getTotalPage());
        model.addAttribute("item" , new MauSacDTO());
        return "crudmausac";
    }

    @RequestMapping("/showMauSac/page{p}")
    public String showPage(@PathVariable(value = "p") Integer p , Model model){

        if(p== -1){
            if(currentPage > 0) {
                p = currentPage - 1;
            }else {
                p=0;
            }
        } else if (p== -2) {
            if(currentPage < mauSacService.getTotalPage()){
                p= currentPage+1;
            }else{
                p= mauSacService.getTotalPage();
            }
        }
        currentPage= p;

        model.addAttribute("item" , new MauSacDTO());

        if(isSearching){
            model.addAttribute("listItem", mauSacService.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("listItem", mauSacService.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , mauSacService.getTotalPage());

        return "crudmausac";
    }

    @PostMapping("/saveMauSac")
    public String save(@Valid @ModelAttribute MauSacVO mauSacVO , BindingResult result , Model model){
        if(result.hasErrors()){
            for (FieldError err: result.getFieldErrors()) {
                model.addAttribute(err.getField(), err.getDefaultMessage());
            }
            if(isSearching){
                model.addAttribute("listItem", mauSacService.findAllByKeySearch(keySearch,currentPage));
            }else {
                model.addAttribute("listItem", mauSacService.findAllByPage(currentPage));
            }
            model.addAttribute("totalPage" , mauSacService.getTotalPage());
            model.addAttribute("item" , mauSacVO);

            return "crudmausac";
        }
        mauSacService.save(mauSacVO);
        return "redirect:/showMauSac/page"+currentPage;
    }

    @RequestMapping("/removeMauSac/{id}")
    public String remove(@PathVariable("id") Integer id , Model model){
        mauSacService.delete(id);
        return "redirect:/showMauSac/page"+currentPage;
    }

    @RequestMapping("/editMauSac/{id}")
    public String edit(@PathVariable("id") Integer id , Model model){
        MauSacDTO dto = mauSacService.getById(id);
        if(isSearching){
            model.addAttribute("listItem", mauSacService.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("listItem", mauSacService.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , mauSacService.getTotalPage());
        model.addAttribute("item" , dto);
        return "crudmausac";
    }

    @RequestMapping("/searchMauSac")
    public String search(@RequestParam("keySearch") String keySearch, Model model ){
        isSearching=true;
        currentPage =0;
        this.keySearch = keySearch;
        model.addAttribute("listItem", mauSacService.findAllByKeySearch(keySearch,currentPage));
        model.addAttribute("totalPage" , mauSacService.getTotalPage());
        model.addAttribute("item" , new MauSacDTO());
        return "crudmausac";
    }
}
