package com.example.assignment.controller;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.GioHangChiTietDTO;
import com.example.assignment.dto.GioHangDTO;
import com.example.assignment.dto.KhachHangDTO;
import com.example.assignment.dto.NhanVienDTO;
import com.example.assignment.dto.SanPhamDTO;
import com.example.assignment.service.ChiTietSanPhamService;
import com.example.assignment.service.GioHangChiTietService;
import com.example.assignment.service.GioHangService;
import com.example.assignment.service.SanPhamService;
import com.example.assignment.vo.GioHangVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    GioHangService gioHangService;
    @Autowired
    GioHangChiTietService gioHangChiTietService;
    @Autowired
    SanPhamService sanPhamService;

    @GetMapping("/showIndex")
    public String show(Model model , HttpSession session){
        Object orderSuccess = session.getAttribute("orderSuccess");
        session.removeAttribute("orderSuccess");
        if(orderSuccess != null){
            model.addAttribute("orderSuccess1" , orderSuccess.toString());
        }

        Object vaiTro = session.getAttribute("vaiTro");
        Object idGioHang = session.getAttribute("idGioHang");




        if(vaiTro == null && idGioHang == null){
            GioHangVO gioHangVO = new GioHangVO();
            gioHangVO.setTinhTrang(0);
            int id = gioHangService.save(gioHangVO);
            session.setAttribute("idGioHang" , id);
        }else if(vaiTro == null && idGioHang != null){
            int id = Integer.parseInt(idGioHang.toString());
            GioHangDTO gioHangDTO = gioHangService.getById(id);
            if(gioHangDTO.getTinhTrang()==1){
                GioHangVO gioHangVO = new GioHangVO();
                gioHangVO.setTinhTrang(0);
                id = gioHangService.save(gioHangVO);
                session.setAttribute("idGioHang" , id);
            }else {
                session.setAttribute("idGioHang" , id);
            }

        }else if(vaiTro.toString().equals("KH")){
            Object user = session.getAttribute("user");
            KhachHangDTO khachHangDTO = new KhachHangDTO();
            BeanUtils.copyProperties(user ,khachHangDTO);
            GioHangDTO gioHangDTO = gioHangService.findByTinhTrangAndKhachHangID(0, khachHangDTO.getId());
            if(gioHangDTO == null){
                GioHangVO gioHangVO = new GioHangVO();
                gioHangVO.setTinhTrang(0);
                gioHangVO.setKhachhangId(khachHangDTO.getId());
                int id = gioHangService.save(gioHangVO);
                session.setAttribute("idGioHang" , id);
            }else {
                session.setAttribute("idGioHang" , gioHangDTO.getId());
            }
        }
        else if(vaiTro.toString().equals("NV")){
            Object user = session.getAttribute("user");
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            BeanUtils.copyProperties(user ,nhanVienDTO);
            GioHangDTO gioHangDTO = gioHangService.findByTinhTrangAndNhanVienID(0, nhanVienDTO.getId());
            if(gioHangDTO == null){
                GioHangVO gioHangVO = new GioHangVO();
                gioHangVO.setTinhTrang(0);
                gioHangVO.setNhanvienId(nhanVienDTO.getId());
                int id = gioHangService.save(gioHangVO);
                session.setAttribute("idGioHang" , id);
            }else {
                session.setAttribute("idGioHang" , gioHangDTO.getId());
            }
        }

        // lọc sản phẩm hết hàng
        List<SanPhamDTO> list = sanPhamService.findAllByCTSP(0);
        List<SanPhamDTO> list1 = new ArrayList<>();
        idGioHang = session.getAttribute("idGioHang");
        for (int i = 0; i < list.size() ; i++) {
            int totalQuantity =0;
            for (int j = 0; j < list.get(i).getChiTietSanPhamsById().size(); j++) {
                totalQuantity += list.get(i).getChiTietSanPhamsById().get(j).getSoLuongTon();

            }
            if(totalQuantity >0){
//                list.remove(i);
                list1.add(list.get(i));
            }
        }

        session.setAttribute("totalCartProduct" ,
                gioHangChiTietService.getTotalCartProduct(Integer.parseInt(idGioHang.toString())));
        session.setAttribute("totalPriceProduct" ,
                gioHangChiTietService.getTotalPriceProduct(Integer.parseInt(idGioHang.toString())));
        model.addAttribute("list" , list1);

        return "index";
    }
}
