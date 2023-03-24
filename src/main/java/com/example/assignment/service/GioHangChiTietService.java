package com.example.assignment.service;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.GioHangChiTietDTO;
import com.example.assignment.entity.ChiTietSanPham;
import com.example.assignment.entity.GioHangChiTiet;
import com.example.assignment.repository.GioHangChiTietRepository;
import com.example.assignment.vo.GioHangChiTietQueryVO;
import com.example.assignment.vo.GioHangChiTietUpdateVO;
import com.example.assignment.vo.GioHangChiTietVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GioHangChiTietService {

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;
    private int totalPage =0;

    public List<GioHangChiTietDTO> findAll(){
        List<GioHangChiTiet> gioHangChiTietList = gioHangChiTietRepository.findAll();
        List<GioHangChiTietDTO> gioHangChiTietDTOList = new ArrayList<>();
        for (int i = 0; i < gioHangChiTietList.size(); i++) {
            GioHangChiTietDTO gioHangChiTietDTO = new GioHangChiTietDTO();
            BeanUtils.copyProperties(gioHangChiTietList.get(i) , gioHangChiTietDTO);
            gioHangChiTietDTOList.add(gioHangChiTietDTO);
        }
        return gioHangChiTietDTOList;
    }

    public List<GioHangChiTietDTO> findAllByPage(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<GioHangChiTiet> gioHangChiTietPage = gioHangChiTietRepository.findAll(pageable);
        this.totalPage= gioHangChiTietPage.getTotalPages()-1;
        List<GioHangChiTiet> gioHangChiTietList = gioHangChiTietPage.getContent();
        List<GioHangChiTietDTO> gioHangChiTietDTOList = new ArrayList<>();
        for (int i = 0; i < gioHangChiTietList.size(); i++) {
            GioHangChiTietDTO gioHangChiTietDTO = new GioHangChiTietDTO();
            BeanUtils.copyProperties(gioHangChiTietList.get(i) , gioHangChiTietDTO);
            gioHangChiTietDTOList.add(gioHangChiTietDTO);
        }
        return gioHangChiTietDTOList;
    }

    public List<GioHangChiTietDTO> findAllByKeySearch(Integer keySearch , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<GioHangChiTiet> gioHangChiTietPage = gioHangChiTietRepository.findAllBySoLuong(keySearch, pageable);
        this.totalPage= gioHangChiTietPage.getTotalPages()-1;
        List<GioHangChiTiet> gioHangChiTietList = gioHangChiTietPage.getContent();
        List<GioHangChiTietDTO> gioHangChiTietDTOList = new ArrayList<>();
        for (int i = 0; i < gioHangChiTietList.size(); i++) {
            GioHangChiTietDTO gioHangChiTietDTO = new GioHangChiTietDTO();
            BeanUtils.copyProperties(gioHangChiTietList.get(i) , gioHangChiTietDTO);
            gioHangChiTietDTOList.add(gioHangChiTietDTO);
        }
        return gioHangChiTietDTOList;
    }

    public GioHangChiTietDTO findByGioHangAndChiTietSanPham(Integer gioHang , Integer chiTietSanPham){
        GioHangChiTiet original =  gioHangChiTietRepository.findByGiohangchitietGiohangAndGiohangchitietChitietsanpham(gioHang, chiTietSanPham);
        if(original == null){
            return null;
        }
        return toDTO(original);
    }
    public List<GioHangChiTietDTO> findByGioHang(Integer gioHang){
        List<GioHangChiTiet> gioHangChiTietList =  gioHangChiTietRepository.findByGiohangchitietGiohang(gioHang);
        List<GioHangChiTietDTO> gioHangChiTietDTOList = new ArrayList<>();
        for (int i = 0; i < gioHangChiTietList.size(); i++) {
            gioHangChiTietDTOList.add(toDTO(gioHangChiTietList.get(i)));
        }
        return gioHangChiTietDTOList;
    }

    public int getTotalCartProduct(Integer gioHangID){
        List<GioHangChiTiet> gioHangChiTietList =  gioHangChiTietRepository.findByGiohangchitietGiohang(gioHangID);
        if(gioHangChiTietList.isEmpty()){
            return 0;
        }
        return gioHangChiTietRepository.getTotalCartProduct(gioHangID);
    }
    public int getTotalPriceProduct(Integer gioHangID){
        List<GioHangChiTiet> gioHangChiTietList =  gioHangChiTietRepository.findByGiohangchitietGiohang(gioHangID);
        if(gioHangChiTietList.isEmpty()){
            return 0;
        }
        return gioHangChiTietRepository.getTotalPriceProduct(gioHangID);
    }

    public Integer save(GioHangChiTietVO vO) {
        GioHangChiTiet bean = new GioHangChiTiet();
        BeanUtils.copyProperties(vO, bean);
        bean = gioHangChiTietRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        gioHangChiTietRepository.deleteById(id);
    }

    public void update(Integer id, GioHangChiTietUpdateVO vO) {
        GioHangChiTiet bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        gioHangChiTietRepository.save(bean);
    }

    public GioHangChiTietDTO getById(Integer id) {
        GioHangChiTiet original = requireOne(id);
        return toDTO(original);
    }

    public Page<GioHangChiTietDTO> query(GioHangChiTietQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private GioHangChiTietDTO toDTO(GioHangChiTiet original) {
        GioHangChiTietDTO bean = new GioHangChiTietDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private GioHangChiTiet requireOne(Integer id) {
        return gioHangChiTietRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public int getTotalPage(){
        return this.totalPage;
    }
}
