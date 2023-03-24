package com.example.assignment.service;

import com.example.assignment.dto.KhachHangDTO;
import com.example.assignment.dto.NsxDTO;
import com.example.assignment.entity.KhachHang;
import com.example.assignment.entity.Nsx;
import com.example.assignment.repository.KhachHangRepository;
import com.example.assignment.vo.KhachHangQueryVO;
import com.example.assignment.vo.KhachHangUpdateVO;
import com.example.assignment.vo.KhachHangVO;
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
public class KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;
    private int totalPage =0;
    public List<KhachHangDTO> findAll(){
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        List<KhachHangDTO> khachHangDTOList = new ArrayList<>();
        for (int i = 0; i < khachHangList.size(); i++) {
            KhachHangDTO khachHangDTO = new KhachHangDTO();
            BeanUtils.copyProperties(khachHangList.get(i) , khachHangDTO);
            khachHangDTOList.add(khachHangDTO);
        }
        return khachHangDTOList;
    }

    public KhachHangDTO checkForgotPass(KhachHangVO kh){
        List<KhachHang> list = khachHangRepository.checkForgotPass(kh.getMa(),kh.getHoTen(),kh.getNgaySinh(),kh.getSdt(),kh.getDiaChi());
        if(!list.isEmpty()){
            KhachHangDTO khachHangDTO = new KhachHangDTO();
            BeanUtils.copyProperties(list.get(0), khachHangDTO);
            return khachHangDTO;
        }
        return null;
    }

    public List<KhachHangDTO> findAllByPage(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<KhachHang> khachHangPage = khachHangRepository.findAll(pageable);
        this.totalPage= khachHangPage.getTotalPages()-1;
        List<KhachHang> khachHangList = khachHangPage.getContent();
        List<KhachHangDTO> khachHangDTOList = new ArrayList<>();
        for (int i = 0; i < khachHangList.size(); i++) {
            KhachHangDTO khachHangDTO = new KhachHangDTO();
            BeanUtils.copyProperties(khachHangList.get(i) , khachHangDTO);
            khachHangDTOList.add(khachHangDTO);
        }
        return khachHangDTOList;
    }

    public List<KhachHangDTO> findAllByKeySearch(String keySearch , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<KhachHang> khachHangPage = khachHangRepository.findAllByHoTenLike(keySearch, pageable);
        this.totalPage= khachHangPage.getTotalPages()-1;
        List<KhachHang> khachHangList = khachHangPage.getContent();
        List<KhachHangDTO> khachHangDTOList = new ArrayList<>();
        for (int i = 0; i < khachHangList.size(); i++) {
            KhachHangDTO khachHangDTO = new KhachHangDTO();
            BeanUtils.copyProperties(khachHangList.get(i) , khachHangDTO);
            khachHangDTOList.add(khachHangDTO);
        }
        return khachHangDTOList;
    }

    public Integer save(KhachHangVO vO) {
        KhachHang bean = new KhachHang();
        BeanUtils.copyProperties(vO, bean);
        bean = khachHangRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        khachHangRepository.deleteById(id);
    }

    public void update(Integer id, KhachHangUpdateVO vO) {
        KhachHang bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        khachHangRepository.save(bean);
    }

    public KhachHangDTO getById(Integer id) {
        KhachHang original = requireOne(id);
        return toDTO(original);
    }

    public KhachHangDTO findByUsernameAndPassword(String username , String password) {
        KhachHang original = khachHangRepository.findByMaAndAndMatKhau(username,password);
        if(original == null){
            return null;
        }
        return toDTO(original);
    }

    public Page<KhachHangDTO> query(KhachHangQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private KhachHangDTO toDTO(KhachHang original) {
        KhachHangDTO bean = new KhachHangDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private KhachHang requireOne(Integer id) {
        return khachHangRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public int getTotalPage(){
        return this.totalPage;
    }
}
