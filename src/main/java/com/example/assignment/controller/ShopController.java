package com.example.assignment.controller;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.DongSanPhamDTO;
import com.example.assignment.dto.SanPhamDTO;
import com.example.assignment.entity.DongSanPham;
import com.example.assignment.service.ChiTietSanPhamService;
import com.example.assignment.service.DongSanPhamService;
import com.example.assignment.service.GioHangChiTietService;
import com.example.assignment.service.SanPhamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ShopController {
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    SanPhamService sanPhamService;
    @Autowired
    GioHangChiTietService gioHangChiTietService;
    @Autowired
    DongSanPhamService dongSanPhamService;

    private Integer dspID =-1;

    @GetMapping("/showShop/{dspID}")
    public String show(Model model, HttpSession session , @PathVariable("dspID") Integer dspID){
        if(dspID != -2){
            this.dspID=dspID;
        }
        showShop(model,session,0 , dspID);
        return "shop";
    }

    @GetMapping("/showShop/{page}/{dspID}")
    public String show1(Model model, HttpSession session ,@PathVariable("page") Integer page , @PathVariable("dspID") Integer dspID){
        if(dspID != -2){
            this.dspID=dspID;
        }
        System.out.println(this.dspID);
        showShop(model,session,page , dspID);
        return "shop";
    }

    public void showShop(Model model , HttpSession session , Integer page , Integer dspID){
        if(dspID == -2){
            dspID = this.dspID;
        }

        model.addAttribute("totalPage", sanPhamService.getTotalPage());

        List<DongSanPhamDTO> listDSP = dongSanPhamService.findAll();

        // lọc sản phẩm hết hàng
        List<SanPhamDTO> list = sanPhamService.findAllByCTSP(page);
        Object idGioHang = session.getAttribute("idGioHang");
        if(dspID==-1){
            for (int i = list.size()-1; i >=0 ; i--) {
                int totalQuantity =0;
                for (int j = 0; j < list.get(i).getChiTietSanPhamsById().size(); j++) {
                    totalQuantity += list.get(i).getChiTietSanPhamsById().get(j).getSoLuongTon();
                }
                if(totalQuantity==0){
                    list.remove(i);
                }
            }
        } else {
            for (int i = list.size()-1; i >=0 ; i--) {
                if(list.get(i).getChiTietSanPhamsById().get(0).getDongsanphamId() != dspID){
                    list.remove(i);
                    continue;
                }

                int totalQuantity =0;
                for (int j = 0; j < list.get(i).getChiTietSanPhamsById().size(); j++) {
                    totalQuantity += list.get(i).getChiTietSanPhamsById().get(j).getSoLuongTon();
                }
                if(totalQuantity==0){
                    list.remove(i);
                }
            }
        }

        model.addAttribute("list" , list);
        model.addAttribute("listDSP" , listDSP);
        session.setAttribute("totalCartProduct" ,
                gioHangChiTietService.getTotalCartProduct(Integer.parseInt(idGioHang.toString())));
        session.setAttribute("totalPriceProduct" ,
                gioHangChiTietService.getTotalPriceProduct(Integer.parseInt(idGioHang.toString())));
    }


}
