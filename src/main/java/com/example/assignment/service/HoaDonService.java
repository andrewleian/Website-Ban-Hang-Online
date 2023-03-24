package com.example.assignment.service;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.HoaDonDTO;
import com.example.assignment.entity.ChiTietSanPham;
import com.example.assignment.entity.HoaDon;
import com.example.assignment.repository.HoaDonRepository;
import com.example.assignment.vo.HoaDonQueryVO;
import com.example.assignment.vo.HoaDonUpdateVO;
import com.example.assignment.vo.HoaDonVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;
    private int totalPage =0;

    public List<HoaDonDTO> findAll(){
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        List<HoaDonDTO> hoaDonDTOList = new ArrayList<>();
        for (int i = 0; i < hoaDonList.size(); i++) {
            HoaDonDTO hoaDonDTO = new HoaDonDTO();
            BeanUtils.copyProperties(hoaDonList.get(i) , hoaDonDTO);
            hoaDonDTOList.add(hoaDonDTO);
        }
        return hoaDonDTOList;
    }

    public List<HoaDonDTO> findAllByPage(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<HoaDon> hoaDonPage = hoaDonRepository.findAll(pageable);
        this.totalPage= hoaDonPage.getTotalPages()-1;
        List<HoaDon> hoaDonList = hoaDonPage.getContent();
        List<HoaDonDTO> hoaDonDTOList = new ArrayList<>();
        for (int i = 0; i < hoaDonList.size(); i++) {
            HoaDonDTO hoaDonDTO = new HoaDonDTO();
            BeanUtils.copyProperties(hoaDonList.get(i) , hoaDonDTO);
            hoaDonDTOList.add(hoaDonDTO);
        }
        return hoaDonDTOList;
    }

    public List<HoaDonDTO> findAllByKeySearch(String keySearch , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<HoaDon> hoaDonPage = hoaDonRepository.findAllByNhanVienByNhanvienIdHoTenLike(keySearch, pageable);
        this.totalPage= hoaDonPage.getTotalPages()-1;
        List<HoaDon> hoaDonList = hoaDonPage.getContent();
        List<HoaDonDTO> hoaDonDTOList = new ArrayList<>();
        for (int i = 0; i < hoaDonList.size(); i++) {
            HoaDonDTO hoaDonDTO = new HoaDonDTO();
            BeanUtils.copyProperties(hoaDonList.get(i) , hoaDonDTO);
            hoaDonDTOList.add(hoaDonDTO);
        }
        return hoaDonDTOList;
    }

    public List<HoaDonDTO> findAllByKhachHangID(Integer khachHangID , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<HoaDon> hoaDonPage = hoaDonRepository.findAllByKhachhangId(khachHangID, pageable);
        this.totalPage= hoaDonPage.getTotalPages()-1;
        List<HoaDon> hoaDonList = hoaDonPage.getContent();
        List<HoaDonDTO> hoaDonDTOList = new ArrayList<>();
        for (int i = 0; i < hoaDonList.size(); i++) {
            HoaDonDTO hoaDonDTO = new HoaDonDTO();
            BeanUtils.copyProperties(hoaDonList.get(i) , hoaDonDTO);
            hoaDonDTOList.add(hoaDonDTO);
        }
        return hoaDonDTOList;
    }

    public List<HoaDonDTO> findAllByNhanVienID(Integer nhanVienID , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<HoaDon> hoaDonPage = hoaDonRepository.findAllByNhanVienByNhanvienId(nhanVienID, pageable);
        this.totalPage= hoaDonPage.getTotalPages()-1;
        List<HoaDon> hoaDonList = hoaDonPage.getContent();
        List<HoaDonDTO> hoaDonDTOList = new ArrayList<>();
        for (int i = 0; i < hoaDonList.size(); i++) {
            HoaDonDTO hoaDonDTO = new HoaDonDTO();
            BeanUtils.copyProperties(hoaDonList.get(i) , hoaDonDTO);
            hoaDonDTOList.add(hoaDonDTO);
        }
        return hoaDonDTOList;
    }
// 0 == đã nhan duoc hang
    // 1 = dang cho xac nhan
    // 2 = dang dong goi
    // 3 = dang giao hang
    // 4 = da huy
    public Integer save(HoaDonVO vO) {
        HoaDon bean = new HoaDon();
        BeanUtils.copyProperties(vO, bean);

        if(bean.getId()>0){
            HoaDon original = requireOne(bean.getId());
            bean.setNgayNhan(original.getNgayNhan());
            bean.setNgayTao(original.getNgayTao());
            bean.setNgayThanhToan(original.getNgayThanhToan());
            bean.setNgayShip(original.getNgayShip());
        }

        if(bean.getTinhTrang() == 1){
            bean.setNgayTao(new Date());
        }
        if(bean.getTinhTrang() == 2){
            bean.setNgayThanhToan(new Date());
        }
        if(bean.getTinhTrang() == 3){
            bean.setNgayShip(new Date());
        }
        if(bean.getTinhTrang() == 0){
            bean.setNgayNhan(new Date());
        }
        bean = hoaDonRepository.save(bean);
        bean.setMa("HD_00"+bean.getId());
        bean = hoaDonRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        hoaDonRepository.deleteById(id);
    }

    public void update(Integer id, HoaDonUpdateVO vO) {
        HoaDon bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        hoaDonRepository.save(bean);
    }

    public HoaDonDTO getById(Integer id) {
        HoaDon original = requireOne(id);
        return toDTO(original);
    }

    public Page<HoaDonDTO> query(HoaDonQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private HoaDonDTO toDTO(HoaDon original) {
        HoaDonDTO bean = new HoaDonDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private HoaDon requireOne(Integer id) {
        return hoaDonRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public int getTotalPage(){
        return this.totalPage;
    }
}
