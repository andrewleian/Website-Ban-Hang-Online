package com.example.assignment.controller;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.GioHangChiTietDTO;
import com.example.assignment.service.ChiTietSanPhamService;
import com.example.assignment.service.GioHangChiTietService;
import com.example.assignment.service.SanPhamService;
import com.example.assignment.vo.ChiTietSanPhamVO;
import com.example.assignment.vo.GioHangChiTietVO;
import jakarta.security.auth.message.callback.SecretKeyCallback;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.connector.Request;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    GioHangChiTietService gioHangChiTietService;

    @GetMapping("/showCart")
    public String show(Model model , HttpSession session){
        Object idGioHang = session.getAttribute("idGioHang");
        session.setAttribute("totalCartProduct" ,
                gioHangChiTietService.getTotalCartProduct(Integer.parseInt(idGioHang.toString())));
        session.setAttribute("totalPriceProduct" ,
                gioHangChiTietService.getTotalPriceProduct(Integer.parseInt(idGioHang.toString())));
        model.addAttribute("list" ,
                gioHangChiTietService.findByGioHang(Integer.parseInt(idGioHang.toString())));
        return "cart";
    }

    @RequestMapping("/removeProduct/{id}")
    public String removeProduct(Model model , @PathVariable("id") Integer id){
        GioHangChiTietDTO gioHangChiTietDTO = gioHangChiTietService.getById(id);
        Integer soLuong = gioHangChiTietDTO.getSoLuong();
        ChiTietSanPhamDTO chiTietSanPhamDTO = chiTietSanPhamService.getById(gioHangChiTietDTO.getGiohangchitietChitietsanpham());
        chiTietSanPhamDTO.setSoLuongTon(chiTietSanPhamDTO.getSoLuongTon()+soLuong);
        ChiTietSanPhamVO chiTietSanPhamVO = new ChiTietSanPhamVO();
        BeanUtils.copyProperties(chiTietSanPhamDTO,chiTietSanPhamVO);
        chiTietSanPhamService.save(chiTietSanPhamVO);
        gioHangChiTietService.delete(id);
        return "redirect:/showCart";
    }

    @RequestMapping({"/indexAddToCart/{id}","/shopAddToCart/{id}","/orderNow/{id}"})
    public String addToCart(@PathVariable("id") Integer id , Model model , HttpSession session , HttpServletRequest request){
        Object idGioHang = session.getAttribute("idGioHang");

        List<ChiTietSanPhamDTO> chiTietSanPhamDTOList = chiTietSanPhamService.findAllBySanPhamID(id);
        for (int i = 0; i < chiTietSanPhamDTOList.size(); i++) {
            if (chiTietSanPhamDTOList.get(i).getSoLuongTon() > 0) {

                ChiTietSanPhamDTO chiTietSanPhamDTO = chiTietSanPhamDTOList.get(i);
                GioHangChiTietDTO gioHangChiTietDTO = gioHangChiTietService.findByGioHangAndChiTietSanPham(Integer.parseInt(idGioHang.toString()) ,chiTietSanPhamDTO.getId());
                if(gioHangChiTietDTO  == null){
                    GioHangChiTietVO gioHangChiTietVO = new GioHangChiTietVO();
                    gioHangChiTietVO.setGiohangchitietGiohang(Integer.parseInt(idGioHang.toString()));
                    gioHangChiTietVO.setGiohangchitietChitietsanpham(chiTietSanPhamDTO.getId());
                    gioHangChiTietVO.setSoLuong(1);
                    gioHangChiTietVO.setDonGia(chiTietSanPhamDTO.getGiaBan());
                    gioHangChiTietVO.setDonGiaKhiGia(chiTietSanPhamDTO.getGiaBan());
                    gioHangChiTietService.save(gioHangChiTietVO);
                }else {
                    GioHangChiTietVO gioHangChiTietVO = new GioHangChiTietVO();
                    BeanUtils.copyProperties(gioHangChiTietDTO , gioHangChiTietVO);
                    Integer soLuong = gioHangChiTietVO.getSoLuong()+1;
                    Integer donGia = Integer.parseInt(gioHangChiTietVO.getDonGia().toString());
                    Integer donGiaKhiGiao = soLuong * donGia;
                    gioHangChiTietVO.setSoLuong(soLuong);
                    gioHangChiTietVO.setDonGiaKhiGia(new BigDecimal(donGiaKhiGiao));
                    gioHangChiTietService.save(gioHangChiTietVO);
                }

                ChiTietSanPhamVO chiTietSanPhamVO = new ChiTietSanPhamVO();
                BeanUtils.copyProperties(chiTietSanPhamDTO,chiTietSanPhamVO);
                chiTietSanPhamVO.setSoLuongTon(chiTietSanPhamVO.getSoLuongTon()-1);
                chiTietSanPhamService.save(chiTietSanPhamVO);
                break;
            }
        }




        String path = request.getRequestURI();
        if(path.indexOf("indexAddToCart")>=0){
            return "redirect:/showIndex";
        } else if (path.indexOf("orderNow")>=0) {
            return "redirect:/showCart";
        } else {
            return "redirect:/showShop/-2";
        }


    }

    @RequestMapping("/singleProductAddToCart/{idCTSP}")
    public String singleProductAddToCart(@PathVariable("idCTSP") Integer idCTSP , @RequestParam("quantity") Integer quantity, Model model , HttpSession session){
        Object idGioHang = session.getAttribute("idGioHang");

        ChiTietSanPhamDTO chiTietSanPhamDTO = chiTietSanPhamService.getById(idCTSP);

        GioHangChiTietDTO gioHangChiTietDTO = gioHangChiTietService.findByGioHangAndChiTietSanPham(Integer.parseInt(idGioHang.toString()) ,chiTietSanPhamDTO.getId());
        if(gioHangChiTietDTO  == null){
            GioHangChiTietVO gioHangChiTietVO = new GioHangChiTietVO();
            gioHangChiTietVO.setGiohangchitietGiohang(Integer.parseInt(idGioHang.toString()));
            gioHangChiTietVO.setGiohangchitietChitietsanpham(chiTietSanPhamDTO.getId());
            gioHangChiTietVO.setSoLuong(quantity);
            gioHangChiTietVO.setDonGia(chiTietSanPhamDTO.getGiaBan());
            Integer donGiaKhiGiao = quantity * Integer.parseInt(chiTietSanPhamDTO.getGiaBan().toString());
            gioHangChiTietVO.setDonGiaKhiGia(new BigDecimal(donGiaKhiGiao));
            gioHangChiTietService.save(gioHangChiTietVO);
        }else {
            GioHangChiTietVO gioHangChiTietVO = new GioHangChiTietVO();
            BeanUtils.copyProperties(gioHangChiTietDTO , gioHangChiTietVO);
            Integer soLuong = gioHangChiTietVO.getSoLuong()+quantity;
            Integer donGia = Integer.parseInt(gioHangChiTietVO.getDonGia().toString());
            Integer donGiaKhiGiao = soLuong * donGia;
            gioHangChiTietVO.setSoLuong(soLuong);
            gioHangChiTietVO.setDonGiaKhiGia(new BigDecimal(donGiaKhiGiao));
            gioHangChiTietService.save(gioHangChiTietVO);
        }


        ChiTietSanPhamVO chiTietSanPhamVO = new ChiTietSanPhamVO();
        BeanUtils.copyProperties(chiTietSanPhamDTO,chiTietSanPhamVO);
        chiTietSanPhamVO.setSoLuongTon(chiTietSanPhamVO.getSoLuongTon()-quantity);
        chiTietSanPhamService.save(chiTietSanPhamVO);
        return "redirect:/showSingleProduct/"+idCTSP;
    }

}
