package com.example.assignment.controller;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.GioHangChiTietDTO;
import com.example.assignment.dto.HoaDonChiTietDTO;
import com.example.assignment.service.ChiTietSanPhamService;
import com.example.assignment.service.DongSanPhamService;
import com.example.assignment.service.GioHangChiTietService;
import com.example.assignment.service.HoaDonChiTietService;
import com.example.assignment.service.HoaDonService;
import com.example.assignment.service.MauSacService;
import com.example.assignment.service.NsxService;
import com.example.assignment.service.SanPhamService;
import com.example.assignment.vo.ChiTietSanPhamVO;
import com.example.assignment.vo.HoaDonChiTietVO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HoaDonChiTietController {
    @Autowired
    private HoaDonChiTietService service;
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    private GioHangChiTietService gioHangChiTietService;
    private Integer currentPage=0;
    private boolean isSearching = false;

    private Integer keySearch;

    public void utilities(Model model){

    }

    public void followIsSearching(Model model){

    }
    @RequestMapping("/showHoaDonChiTiet")
    public String show(Model model){
        isSearching= false;
        currentPage=0;
        model.addAttribute("itemList", service.findAllByPage(currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new HoaDonChiTietDTO());
        model.addAttribute("hoaDonList" ,hoaDonService.findAll());
        model.addAttribute("chiTietSanPhamList" ,chiTietSanPhamService.findAll());
        return "crudhoadonchitiet";
    }

    @RequestMapping("/showHoaDonChiTiet/page{p}")
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

        model.addAttribute("item" , new HoaDonChiTietDTO());

        if(isSearching){
            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , service.getTotalPage());

        model.addAttribute("hoaDonList" ,hoaDonService.findAll());
        model.addAttribute("chiTietSanPhamList" ,chiTietSanPhamService.findAll());

        return "crudhoadonchitiet";
    }

    @PostMapping("/saveHoaDonChiTiet")
    public String save(@Valid @ModelAttribute HoaDonChiTietVO hoaDonChiTietVO , BindingResult result , Model model){
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
            model.addAttribute("item" , hoaDonChiTietVO);

            model.addAttribute("hoaDonList" ,hoaDonService.findAll());
            model.addAttribute("chiTietSanPhamList" ,chiTietSanPhamService.findAll());
            return "crudhoadonchitiet";
        }
        service.save(hoaDonChiTietVO);
        return "redirect:/showHoaDonChiTiet/page"+currentPage;
    }

    @RequestMapping("/placeOrder1/{id}")
    public String placeOder(Model model ,@PathVariable("id")  Integer idHoaDon , HttpSession session){
        Object idCart = session.getAttribute("idGioHang");
        Integer idGioHang = Integer.parseInt(idCart.toString());
        List<GioHangChiTietDTO> gioHangChiTietDTOList = gioHangChiTietService.findByGioHang(idGioHang);
        for (int i = 0; i < gioHangChiTietDTOList.size(); i++) {
            Integer idChiTietSanPham = gioHangChiTietDTOList.get(i).getChiTietSanPhamByGiohangchitietChitietsanpham().getId();
            HoaDonChiTietVO hoaDonChiTietVO = new HoaDonChiTietVO();
            hoaDonChiTietVO.setHoadonchitietHoadon(idHoaDon);
            hoaDonChiTietVO.setHoadonchitietChitietsanpham(idChiTietSanPham);
            hoaDonChiTietVO.setSoLuong(gioHangChiTietDTOList.get(i).getSoLuong());
            hoaDonChiTietVO.setDonGia(gioHangChiTietDTOList.get(i).getDonGia());
            service.save(hoaDonChiTietVO);
        }

        return "redirect:/clearGioHang";
    }

    @RequestMapping("/removeHoaDonChiTiet/{id}")
    public String remove(@PathVariable("id") Integer id , Model model){
        service.delete(id);
        return "redirect:/showHoaDonChiTiet/page"+currentPage;
    }

    @RequestMapping("/editHoaDonChiTiet/{id}")
    public String edit(@PathVariable("id") Integer id , Model model){
        HoaDonChiTietDTO dto = service.getById(id);
        if(isSearching){
            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , dto);

        model.addAttribute("hoaDonList" ,hoaDonService.findAll());
        model.addAttribute("chiTietSanPhamList" ,chiTietSanPhamService.findAll());
        return "crudhoadonchitiet";
    }

    @RequestMapping("/searchHoaDonChiTiet")
    public String search(@RequestParam("keySearch") Integer keySearch, Model model ){
        isSearching=true;
        currentPage =0;
        this.keySearch = keySearch;
        model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new HoaDonChiTietDTO());

        model.addAttribute("hoaDonList" ,hoaDonService.findAll());
        model.addAttribute("chiTietSanPhamList" ,chiTietSanPhamService.findAll());
        return "crudhoadonchitiet";
    }
}

