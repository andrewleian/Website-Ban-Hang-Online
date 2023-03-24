package com.example.assignment.controller;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.HoaDonDTO;
import com.example.assignment.dto.KhachHangDTO;
import com.example.assignment.dto.NhanVienDTO;
import com.example.assignment.service.ChiTietSanPhamService;
import com.example.assignment.service.DongSanPhamService;
import com.example.assignment.service.HoaDonService;
import com.example.assignment.service.KhachHangService;
import com.example.assignment.service.MauSacService;
import com.example.assignment.service.NhanVienService;
import com.example.assignment.service.NsxService;
import com.example.assignment.service.SanPhamService;
import com.example.assignment.vo.ChiTietSanPhamVO;
import com.example.assignment.vo.HoaDonVO;
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

import java.util.Date;

@Controller
public class HoaDonController {
    @Autowired
    private HoaDonService service;
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private KhachHangService khachHangService;

    private Integer currentPage=0;
    private boolean isSearching = false;

    private String keySearch;

    @RequestMapping("/showHoaDon")
    public String show(Model model){
        isSearching= false;
        currentPage=0;
        model.addAttribute("itemList", service.findAllByPage(currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        HoaDonDTO hoaDonDTO = new HoaDonDTO();
        hoaDonDTO.setId(0);
        model.addAttribute("item" , hoaDonDTO);
        model.addAttribute("nhanVienList" ,nhanVienService.findAll());
        model.addAttribute("khachHangList" ,khachHangService.findAll());
        return "crudhoadon";
    }

    @RequestMapping("/showHoaDon/page{p}")
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

        HoaDonDTO hoaDonDTO = new HoaDonDTO();
        hoaDonDTO.setId(0);
        model.addAttribute("item" , hoaDonDTO);

        if(isSearching){
            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , service.getTotalPage());

        model.addAttribute("nhanVienList" ,nhanVienService.findAll());
        model.addAttribute("khachHangList" ,khachHangService.findAll());

        return "crudhoadon";
    }

    @PostMapping("/saveHoaDon")
    public String save(@Valid @ModelAttribute HoaDonVO hoaDonVO , BindingResult result , Model model){
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
//            model.addAttribute("item" , hoaDonVO);
//
//            model.addAttribute("nhanVienList" ,nhanVienService.findAll());
//            model.addAttribute("khachHangList" ,khachHangService.findAll());
//
//            return "crudHoaDon";
//        }
        service.save(hoaDonVO);
        return "redirect:/showHoaDon/page"+currentPage;
    }

    @PostMapping("/placeOrder")
    public String placeOder(Model model, HttpSession session,
                            @RequestParam("tenNguoiNhan") String tenNguoiNhan,
                            @RequestParam("diaChi") String diaChi,
                            @RequestParam("sdt") String sdt){

        HoaDonVO hoaDonVO = new HoaDonVO();

        Object vaiTro = session.getAttribute("vaiTro");
        if(vaiTro == null){

        }
        else if(vaiTro.toString().equals("KH")){
            Object object= session.getAttribute("user");
            KhachHangDTO khachHangDTO = new KhachHangDTO();
            BeanUtils.copyProperties(object,khachHangDTO);
            hoaDonVO.setKhachhangId(khachHangDTO.getId());
        }
        else if(vaiTro.toString().equals("NV")){
            Object object= session.getAttribute("user");
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            BeanUtils.copyProperties(object,nhanVienDTO);
            hoaDonVO.setNhanvienId(nhanVienDTO.getId());
        }

        hoaDonVO.setTenNguoiNhan(tenNguoiNhan);
        hoaDonVO.setDiaChi(diaChi);
        hoaDonVO.setSdt(sdt);
        hoaDonVO.setTinhTrang(1);

        hoaDonVO.setId(0);
        int id =service.save(hoaDonVO);
        return "redirect:/placeOrder1/"+id;
    }

    @RequestMapping("/removeHoaDon/{id}")
    public String remove(@PathVariable("id") Integer id , Model model){
        service.delete(id);
        return "redirect:/showHoaDon/page"+currentPage;
    }

    @RequestMapping("/editHoaDon/{id}")
    public String edit(@PathVariable("id") Integer id , Model model){
        HoaDonDTO dto = service.getById(id);
        if(isSearching){
            model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        }else {
            model.addAttribute("itemList", service.findAllByPage(currentPage));
        }
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , dto);

        model.addAttribute("nhanVienList" ,nhanVienService.findAll());
        model.addAttribute("khachHangList" ,khachHangService.findAll());
        return "crudhoadon";
    }

    @RequestMapping("/searchHoaDon")
    public String search(@RequestParam("keySearch") String keySearch, Model model ){
        isSearching=true;
        currentPage =0;
        this.keySearch = keySearch;
        model.addAttribute("itemList", service.findAllByKeySearch(keySearch,currentPage));
        model.addAttribute("totalPage" , service.getTotalPage());
        model.addAttribute("item" , new HoaDonDTO());

        model.addAttribute("nhanVienList" ,nhanVienService.findAll());
        model.addAttribute("khachHangList" ,khachHangService.findAll());
        return "crudhoadon";
    }
}

