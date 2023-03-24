package com.example.assignment.service;

import com.example.assignment.dto.CuaHangDTO;
import com.example.assignment.dto.NsxDTO;
import com.example.assignment.entity.CuaHang;
import com.example.assignment.entity.Nsx;
import com.example.assignment.repository.CuaHangRepository;
import com.example.assignment.vo.CuaHangQueryVO;
import com.example.assignment.vo.CuaHangUpdateVO;
import com.example.assignment.vo.CuaHangVO;
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
public class CuaHangService {

    @Autowired
    private CuaHangRepository cuaHangRepository;
    private int totalPage =0;
    public List<CuaHangDTO> findAll(){
        List<CuaHang> cuaHangList = cuaHangRepository.findAll();
        List<CuaHangDTO> cuaHangDTOList = new ArrayList<>();
        for (int i = 0; i < cuaHangList.size(); i++) {
            CuaHangDTO cuaHangDTO = new CuaHangDTO();
            BeanUtils.copyProperties(cuaHangList.get(i) , cuaHangDTO);
            cuaHangDTOList.add(cuaHangDTO);
        }
        return cuaHangDTOList;
    }

    public List<CuaHangDTO> findAllByPage(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<CuaHang> cuaHangPage = cuaHangRepository.findAll(pageable);
        this.totalPage= cuaHangPage.getTotalPages()-1;
        List<CuaHang> cuaHangList = cuaHangPage.getContent();
        List<CuaHangDTO> cuaHangDTOList = new ArrayList<>();
        for (int i = 0; i < cuaHangList.size(); i++) {
            CuaHangDTO cuaHangDTO = new CuaHangDTO();
            BeanUtils.copyProperties(cuaHangList.get(i) , cuaHangDTO);
            cuaHangDTOList.add(cuaHangDTO);
        }
        return cuaHangDTOList;
    }

    public List<CuaHangDTO> findAllByKeySearch(String keySearch , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<CuaHang> cuaHangPage = cuaHangRepository.findAllByTenLike(keySearch, pageable);
        this.totalPage= cuaHangPage.getTotalPages()-1;
        List<CuaHang> cuaHangList = cuaHangPage.getContent();
        List<CuaHangDTO> cuaHangDTOList = new ArrayList<>();
        for (int i = 0; i < cuaHangList.size(); i++) {
            CuaHangDTO cuaHangDTO = new CuaHangDTO();
            BeanUtils.copyProperties(cuaHangList.get(i) , cuaHangDTO);
            cuaHangDTOList.add(cuaHangDTO);
        }
        return cuaHangDTOList;
    }

    public Integer save(CuaHangVO vO) {
        CuaHang bean = new CuaHang();
        BeanUtils.copyProperties(vO, bean);
        bean = cuaHangRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        cuaHangRepository.deleteById(id);
    }

    public void update(Integer id, CuaHangUpdateVO vO) {
        CuaHang bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        cuaHangRepository.save(bean);
    }

    public CuaHangDTO getById(Integer id) {
        CuaHang original = requireOne(id);
        return toDTO(original);
    }

    public Page<CuaHangDTO> query(CuaHangQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private CuaHangDTO toDTO(CuaHang original) {
        CuaHangDTO bean = new CuaHangDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private CuaHang requireOne(Integer id) {
        return cuaHangRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public int getTotalPage(){
        return this.totalPage;
    }
}
