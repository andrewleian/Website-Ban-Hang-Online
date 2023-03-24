package com.example.assignment.controller;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.SanPhamDTO;
import com.example.assignment.service.ChiTietSanPhamService;
import com.example.assignment.service.GioHangChiTietService;
import com.example.assignment.service.SanPhamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SingleProductController {
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    GioHangChiTietService gioHangChiTietService;
    @Autowired
    SanPhamService sanPhamService;


    @GetMapping("/showSingleProduct/{id}/{indexColor}")
    public String show(@PathVariable("id") Integer idSanPham,@PathVariable("indexColor") Integer indexColor, Model model , HttpSession session){

        List<ChiTietSanPhamDTO> dtoList = chiTietSanPhamService.findAllBySanPhamID(idSanPham);

        model.addAttribute("item" , dtoList.get(indexColor));
        model.addAttribute("list" , dtoList);

        Object idGioHang = session.getAttribute("idGioHang");

        List<SanPhamDTO> listRelatedProduct = sanPhamService.findAllByCTSPAndDSPID(dtoList.get(indexColor).getDongsanphamId() ,0);
        List<SanPhamDTO> listRelatedProduct1 = new ArrayList<>();
        // lọc sản phẩm còn hàng
        for (int i = listRelatedProduct.size()-1; i >= 0 ; i--) {
            int totalQuantity =0;
            for (int j = 0; j < listRelatedProduct.get(i).getChiTietSanPhamsById().size(); j++) {
                totalQuantity += listRelatedProduct.get(i).getChiTietSanPhamsById().get(j).getSoLuongTon();

            }
            if(totalQuantity >0){
                listRelatedProduct1.add(listRelatedProduct.get(i));
            }
            if(listRelatedProduct1.size()>=3){
                break;
            }
        }

        model.addAttribute("listRelatedProduct" ,listRelatedProduct1);


        session.setAttribute("totalCartProduct" ,
                gioHangChiTietService.getTotalCartProduct(Integer.parseInt(idGioHang.toString())));
        session.setAttribute("totalPriceProduct" ,
                gioHangChiTietService.getTotalPriceProduct(Integer.parseInt(idGioHang.toString())));
        return "single-product";
    }

    @GetMapping("/showSingleProduct/{idCTSP}")
    public String show(@PathVariable("idCTSP") Integer idCTSP, Model model , HttpSession session){
        ChiTietSanPhamDTO chiTietSanPhamDTO = chiTietSanPhamService.getById(idCTSP);
        List<ChiTietSanPhamDTO> dtoList = chiTietSanPhamService.findAllBySanPhamID(chiTietSanPhamDTO.getSanphamId());

        Object idGioHang = session.getAttribute("idGioHang");

        List<SanPhamDTO> listRelatedProduct = sanPhamService.findAllByCTSPAndDSPID(chiTietSanPhamDTO.getDongsanphamId() ,0);
        List<SanPhamDTO> listRelatedProduct1 = new ArrayList<>();
        // lọc sản phẩm còn hàng
        for (int i = listRelatedProduct.size()-1; i >= 0 ; i--) {
            int totalQuantity =0;
            for (int j = 0; j < listRelatedProduct.get(i).getChiTietSanPhamsById().size(); j++) {
                totalQuantity += listRelatedProduct.get(i).getChiTietSanPhamsById().get(j).getSoLuongTon();

            }
            if(totalQuantity >0){
                listRelatedProduct1.add(listRelatedProduct.get(i));
            }
            if(listRelatedProduct1.size()>=3){
                break;
            }
        }

        model.addAttribute("listRelatedProduct" ,listRelatedProduct1);
        model.addAttribute("item" , chiTietSanPhamDTO);
        model.addAttribute("list" , dtoList);

        session.setAttribute("totalCartProduct" ,
                gioHangChiTietService.getTotalCartProduct(Integer.parseInt(idGioHang.toString())));
        session.setAttribute("totalPriceProduct" ,
                gioHangChiTietService.getTotalPriceProduct(Integer.parseInt(idGioHang.toString())));
        return "single-product";
    }
}
