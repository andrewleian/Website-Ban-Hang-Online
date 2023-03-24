package com.example.assignment.service;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.HoaDonChiTietDTO;
import com.example.assignment.entity.ChiTietSanPham;
import com.example.assignment.entity.HoaDonChiTiet;
import com.example.assignment.repository.HoaDonChiTietRepository;
import com.example.assignment.vo.HoaDonChiTietQueryVO;
import com.example.assignment.vo.HoaDonChiTietUpdateVO;
import com.example.assignment.vo.HoaDonChiTietVO;
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
public class HoaDonChiTietService {

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;
    private int totalPage =0;

    public List<HoaDonChiTietDTO> findAll(){
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.findAll();
        List<HoaDonChiTietDTO> hoaDonChiTietDTOList = new ArrayList<>();
        for (int i = 0; i < hoaDonChiTietList.size(); i++) {
            HoaDonChiTietDTO hoaDonChiTietDTO = new HoaDonChiTietDTO();
            BeanUtils.copyProperties(hoaDonChiTietList.get(i) , hoaDonChiTietDTO);
            hoaDonChiTietDTOList.add(hoaDonChiTietDTO);
        }
        return hoaDonChiTietDTOList;
    }

    public List<HoaDonChiTietDTO> findAllByPage(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<HoaDonChiTiet> hoaDonChiTietPage = hoaDonChiTietRepository.findAll(pageable);
        this.totalPage= hoaDonChiTietPage.getTotalPages()-1;
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietPage.getContent();
        List<HoaDonChiTietDTO> hoaDonChiTietDTOList = new ArrayList<>();
        for (int i = 0; i < hoaDonChiTietList.size(); i++) {
            HoaDonChiTietDTO hoaDonChiTietDTO = new HoaDonChiTietDTO();
            BeanUtils.copyProperties(hoaDonChiTietList.get(i) , hoaDonChiTietDTO);
            hoaDonChiTietDTOList.add(hoaDonChiTietDTO);
        }
        return hoaDonChiTietDTOList;
    }

    public List<HoaDonChiTietDTO> findAllByKeySearch(Integer keySearch , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<HoaDonChiTiet> hoaDonChiTietPage = hoaDonChiTietRepository.findAllBySoLuong(keySearch, pageable);
        this.totalPage= hoaDonChiTietPage.getTotalPages()-1;
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietPage.getContent();
        List<HoaDonChiTietDTO> hoaDonChiTietDTOList = new ArrayList<>();
        for (int i = 0; i < hoaDonChiTietList.size(); i++) {
            HoaDonChiTietDTO hoaDonChiTietDTO = new HoaDonChiTietDTO();
            BeanUtils.copyProperties(hoaDonChiTietList.get(i) , hoaDonChiTietDTO);
            hoaDonChiTietDTOList.add(hoaDonChiTietDTO);
        }
        return hoaDonChiTietDTOList;
    }

    public List<HoaDonChiTietDTO> findAllByHoaDonID(Integer hoaDonID){
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.findAllByHoadonchitietHoadon(hoaDonID);
        List<HoaDonChiTietDTO> hoaDonChiTietDTOList = new ArrayList<>();
        for (int i = 0; i < hoaDonChiTietList.size(); i++) {
            HoaDonChiTietDTO hoaDonChiTietDTO = new HoaDonChiTietDTO();
            BeanUtils.copyProperties(hoaDonChiTietList.get(i) , hoaDonChiTietDTO);
            hoaDonChiTietDTOList.add(hoaDonChiTietDTO);
        }
        return hoaDonChiTietDTOList;
    }

    public Integer save(HoaDonChiTietVO vO) {
        HoaDonChiTiet bean = new HoaDonChiTiet();
        BeanUtils.copyProperties(vO, bean);
        bean = hoaDonChiTietRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        hoaDonChiTietRepository.deleteById(id);
    }

    public void update(Integer id, HoaDonChiTietUpdateVO vO) {
        HoaDonChiTiet bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        hoaDonChiTietRepository.save(bean);
    }

    public HoaDonChiTietDTO getById(Integer id) {
        HoaDonChiTiet original = requireOne(id);
        return toDTO(original);
    }

    public Page<HoaDonChiTietDTO> query(HoaDonChiTietQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private HoaDonChiTietDTO toDTO(HoaDonChiTiet original) {
        HoaDonChiTietDTO bean = new HoaDonChiTietDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private HoaDonChiTiet requireOne(Integer id) {
        return hoaDonChiTietRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
    public int getTotalPage(){
        return totalPage;
    }
}
