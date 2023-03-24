package com.example.assignment.controller;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.HoaDonChiTietDTO;
import com.example.assignment.dto.HoaDonDTO;
import com.example.assignment.entity.HoaDonChiTiet;
import com.example.assignment.service.ChiTietSanPhamService;
import com.example.assignment.service.HoaDonChiTietService;
import com.example.assignment.service.HoaDonService;
import com.example.assignment.vo.ChiTietSanPhamVO;
import com.example.assignment.vo.HoaDonVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class OrderDetailController {
    @Autowired
    HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    HoaDonService hoaDonService;
    @Autowired
    ChiTietSanPhamService  chiTietSanPhamService;

    @RequestMapping("/detailOrder/{id}")
    public String showOrderDetail(Model model , @PathVariable("id") Integer id){
        List<HoaDonChiTietDTO> list = hoaDonChiTietService.findAllByHoaDonID(id);
        Integer totalProductPrice = 0;
        for (int i = 0; i < list.size(); i++) {
            totalProductPrice+= Integer.parseInt(list.get(i).getDonGia().toString()) * list.get(i).getSoLuong();
        }

        model.addAttribute("list" , list);
        model.addAttribute("totalPrice" , totalProductPrice);
        return "orderdetail";
    }

    @RequestMapping("/cancelOrder/{id}")
    public String cancelOrder(Model model , @PathVariable("id") Integer id){
        HoaDonVO hoaDonVO = new HoaDonVO();
        HoaDonDTO hoaDonDTO = hoaDonService.getById(id);
        BeanUtils.copyProperties(hoaDonDTO,hoaDonVO);
        hoaDonVO.setTinhTrang(4);
        hoaDonService.save(hoaDonVO);

        List<HoaDonChiTietDTO> list = hoaDonChiTietService.findAllByHoaDonID(id);
        for (int i = 0; i < list.size(); i++) {
            ChiTietSanPhamDTO chiTietSanPhamDTO = chiTietSanPhamService.getById(list.get(i).getHoadonchitietChitietsanpham());
            chiTietSanPhamDTO.setSoLuongTon(chiTietSanPhamDTO.getSoLuongTon()+list.get(i).getSoLuong());
            ChiTietSanPhamVO chiTietSanPhamVO = new ChiTietSanPhamVO();
            BeanUtils.copyProperties(chiTietSanPhamDTO,chiTietSanPhamVO);
            chiTietSanPhamService.save(chiTietSanPhamVO);
        }
        return "redirect:/detailOrder/"+id;
    }
}
