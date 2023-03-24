package com.example.assignment.controller;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.DongSanPhamDTO;
import com.example.assignment.service.ChiTietSanPhamService;
import com.example.assignment.service.DongSanPhamService;
import com.example.assignment.service.MauSacService;
import com.example.assignment.service.NsxService;
import com.example.assignment.service.SanPhamService;
import com.example.assignment.vo.ChiTietSanPhamVO;
import com.example.assignment.vo.DongSanPhamVO;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ChiTietSanPhamController {
    @Autowired
    private ChiTietSanPhamService service;
    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private NsxService nsxService;
    @Autowired
    private MauSacService mauSacService;
    @Autowired
    private DongSanPhamService dongSanPhamService;

    private Integer currentPage=0;
    private boolean isSearching = false;

    private Integer fillter = -1;

    private String keySearch;

    public void utilities(Model model){

    }

    public void followIsSearching(Model model){

    }
    @RequestMapping("/showChiTietSanPham")
    public String show(Model model){
        isSearching= false;
        currentPage=0;
        fillter = -1;


        model.addAttribute("itemList", service.findAllByPage(currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new ChiTietSanPhamDTO());
        model.addAttribute("sanPhamList" ,sanPhamService.findAll());
        model.addAttribute("nsxList" ,nsxService.findAll());
        model.addAttribute("mauSacList" ,mauSacService.findAll());
        model.addAttribute("dongSanPhamList" ,dongSanPhamService.findAll());
        model.addAttribute("fillter" ,fillter);

//        List<ChiTietSanPhamDTO> list = service.
//        for (int i = 0; i < s; i++) {
//
//        }
        return "crudchitietsanpham";
    }

    @RequestMapping("/showChiTietSanPham/page{p}")
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

        model.addAttribute("item" , new ChiTietSanPhamDTO());
//
//        if(isSearching){
//            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
//        }else {
//            model.addAttribute("itemList", service.findAllByPage(currentPage));
//        }

        if(fillter>0){
            model.addAttribute("itemList", service.findAllByDongSanPhamID(fillter,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        System.out.println(service.findAllByPage(0).get(0).getAnh());

        model.addAttribute("totalPage" , service.getTotalPage());

        model.addAttribute("sanPhamList" ,sanPhamService.findAll());
        model.addAttribute("nsxList" ,nsxService.findAll());
        model.addAttribute("mauSacList" ,mauSacService.findAll());
        model.addAttribute("dongSanPhamList" ,dongSanPhamService.findAll());
        model.addAttribute("fillter" ,fillter);
        return "crudchitietsanpham";
    }

    @PostMapping("/saveChiTietSanPham")
    public String save(@Valid @ModelAttribute ChiTietSanPhamVO chiTietSanPhamVO ,
                       BindingResult result , Model model,@RequestParam("file") MultipartFile file) throws IOException {

        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get("src/main/resources/static/img/", file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());

        chiTietSanPhamVO.setAnh(file.getOriginalFilename());


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
            model.addAttribute("item" , chiTietSanPhamVO);

            model.addAttribute("sanPhamList" ,sanPhamService.findAll());
            model.addAttribute("nsxList" ,nsxService.findAll());
            model.addAttribute("mauSacList" ,mauSacService.findAll());
            model.addAttribute("dongSanPhamList" ,dongSanPhamService.findAll());
            model.addAttribute("fillter" ,fillter);
            return "crudchitietsanpham";
        }
        service.save(chiTietSanPhamVO);
        return "redirect:/showChiTietSanPham/page"+currentPage;
    }

    @RequestMapping("/removeChiTietSanPham/{id}")
    public String remove(@PathVariable("id") Integer id , Model model){
        service.delete(id);
        return "redirect:/showChiTietSanPham/page"+currentPage;
    }

    @RequestMapping("/editChiTietSanPham/{id}")
    public String edit(@PathVariable("id") Integer id , Model model){
        ChiTietSanPhamDTO dto = service.getById(id);
        if(isSearching){
            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , dto);

        model.addAttribute("sanPhamList" ,sanPhamService.findAll());
        model.addAttribute("nsxList" ,nsxService.findAll());
        model.addAttribute("mauSacList" ,mauSacService.findAll());
        model.addAttribute("dongSanPhamList" ,dongSanPhamService.findAll());
        model.addAttribute("fillter" ,fillter);
        return "crudchitietsanpham";
    }

    @RequestMapping("/addToTopSeller/{id}")
    public String addToTopSeller(@PathVariable("id") Integer id , Model model){
        ChiTietSanPhamDTO original = service.getById(id);

        if(!service.checkTopSeller(original.getSanphamId())){
            original.setId(0);
            original.setDongsanphamId(7);
            ChiTietSanPhamVO chiTietSanPhamVO = new ChiTietSanPhamVO();
            BeanUtils.copyProperties(original, chiTietSanPhamVO);
            service.save(chiTietSanPhamVO);
        }


//        if(isSearching){
//            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
//        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
//        }
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new ChiTietSanPhamDTO());
        model.addAttribute("sanPhamList" ,sanPhamService.findAll());
        model.addAttribute("nsxList" ,nsxService.findAll());
        model.addAttribute("mauSacList" ,mauSacService.findAll());
        model.addAttribute("dongSanPhamList" ,dongSanPhamService.findAll());
        model.addAttribute("fillter" ,fillter);
        return "crudchitietsanpham";
    }

    @RequestMapping("/addToTopNew/{id}")
    public String addToTopNew(@PathVariable("id") Integer id , Model model){
        ChiTietSanPhamDTO original = service.getById(id);

        if(!service.checkTopNew(original.getSanphamId())){
            original.setId(0);
            original.setDongsanphamId(8);
            ChiTietSanPhamVO chiTietSanPhamVO = new ChiTietSanPhamVO();
            BeanUtils.copyProperties(original, chiTietSanPhamVO);
            service.save(chiTietSanPhamVO);
        }


//        if(isSearching){
//            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
//        }else {
        model.addAttribute("itemList", service.findAllByPage(currentPage));
//        }
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new ChiTietSanPhamDTO());
        model.addAttribute("sanPhamList" ,sanPhamService.findAll());
        model.addAttribute("nsxList" ,nsxService.findAll());
        model.addAttribute("mauSacList" ,mauSacService.findAll());
        model.addAttribute("dongSanPhamList" ,dongSanPhamService.findAll());
        model.addAttribute("fillter" ,fillter);
        return "crudchitietsanpham";
    }

    @RequestMapping("/searchChiTietSanPham")
    public String search(@RequestParam("keySearch") String keySearch, Model model ){
        isSearching=true;
        currentPage =0;
        this.keySearch = keySearch;
        model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new ChiTietSanPhamDTO());

        model.addAttribute("sanPhamList" ,sanPhamService.findAll());
        model.addAttribute("nsxList" ,nsxService.findAll());
        model.addAttribute("mauSacList" ,mauSacService.findAll());
        model.addAttribute("dongSanPhamList" ,dongSanPhamService.findAll());
        model.addAttribute("fillter" ,fillter);
        return "crudchitietsanpham";
    }

    @RequestMapping("/showFilter/{id}")
    public String showTopSeller( Model model , @PathVariable("id") Integer id ){
        currentPage =0;
        fillter = id;
        model.addAttribute("itemList", service.findAllByDongSanPhamID(id,currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new ChiTietSanPhamDTO());

        model.addAttribute("sanPhamList" ,sanPhamService.findAll());
        model.addAttribute("nsxList" ,nsxService.findAll());
        model.addAttribute("mauSacList" ,mauSacService.findAll());
        model.addAttribute("dongSanPhamList" ,dongSanPhamService.findAll());
        model.addAttribute("fillter" ,fillter);
        return "crudchitietsanpham";
    }

}
