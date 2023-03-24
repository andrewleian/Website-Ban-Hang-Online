package com.example.assignment.service;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.GioHangDTO;
import com.example.assignment.entity.ChiTietSanPham;
import com.example.assignment.entity.GioHang;
import com.example.assignment.repository.GioHangRepository;
import com.example.assignment.vo.GioHangQueryVO;
import com.example.assignment.vo.GioHangUpdateVO;
import com.example.assignment.vo.GioHangVO;
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
public class GioHangService {

    @Autowired
    private GioHangRepository gioHangRepository;
    private int totalPage =0;

    public List<GioHangDTO> findAll(){
        List<GioHang> gioHangList = gioHangRepository.findAll();
        List<GioHangDTO> gioHangDTOList = new ArrayList<>();
        for (int i = 0; i < gioHangList.size(); i++) {
            GioHangDTO gioHangDTO = new GioHangDTO();
            BeanUtils.copyProperties(gioHangList.get(i) , gioHangDTO);
            gioHangDTOList.add(gioHangDTO);
        }
        return gioHangDTOList;
    }

    public List<GioHangDTO> findAllByPage(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<GioHang> gioHangPage = gioHangRepository.findAll(pageable);
        this.totalPage= gioHangPage.getTotalPages()-1;
        List<GioHang> gioHangList = gioHangPage.getContent();
        List<GioHangDTO> gioHangDTOList = new ArrayList<>();
        for (int i = 0; i < gioHangList.size(); i++) {
            GioHangDTO gioHangDTO = new GioHangDTO();
            BeanUtils.copyProperties(gioHangList.get(i) , gioHangDTO);
            gioHangDTOList.add(gioHangDTO);
        }
        return gioHangDTOList;
    }



    public List<GioHangDTO> findAllByKeySearch(String keySearch , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<GioHang> gioHangPage = gioHangRepository.findAllByKhachHangByKhachhangIdHoTenLike(keySearch, pageable);
        this.totalPage= gioHangPage.getTotalPages()-1;
        List<GioHang> gioHangList = gioHangPage.getContent();
        List<GioHangDTO> gioHangDTOList = new ArrayList<>();
        for (int i = 0; i < gioHangList.size(); i++) {
            GioHangDTO gioHangDTO = new GioHangDTO();
            BeanUtils.copyProperties(gioHangList.get(i) , gioHangDTO);
            gioHangDTOList.add(gioHangDTO);
        }
        return gioHangDTOList;
    }

    public GioHangDTO findByTinhTrangAndNhanVienID(Integer tinhTrang , Integer nhanVienID) {
        GioHang original = gioHangRepository.findByTinhTrangAndNhanvienId(tinhTrang , nhanVienID);
        if(original == null){
            return null;
        }
        return toDTO(original);
    }

    public GioHangDTO findByTinhTrangAndKhachHangID(Integer tinhTrang , Integer khachHangID) {
        GioHang original = gioHangRepository.findByTinhTrangAndKhachhangId(tinhTrang , khachHangID);
        if(original == null){
            return null;
        }
        return toDTO(original);
    }

    public Integer save(GioHangVO vO) {
        GioHang bean = new GioHang();
        BeanUtils.copyProperties(vO, bean);
        bean = gioHangRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        gioHangRepository.deleteById(id);
    }

    public void update(Integer id, GioHangUpdateVO vO) {
        GioHang bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        gioHangRepository.save(bean);
    }

    public GioHangDTO getById(Integer id) {
        GioHang original = requireOne(id);
        return toDTO(original);
    }

    public Page<GioHangDTO> query(GioHangQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private GioHangDTO toDTO(GioHang original) {
        GioHangDTO bean = new GioHangDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private GioHang requireOne(Integer id) {
        return gioHangRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public int getTotalPage(){
        return this.totalPage;
    }
}
