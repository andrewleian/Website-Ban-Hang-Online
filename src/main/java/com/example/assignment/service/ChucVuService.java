package com.example.assignment.service;

import com.example.assignment.dto.ChucVuDTO;
import com.example.assignment.dto.NsxDTO;
import com.example.assignment.entity.ChucVu;
import com.example.assignment.entity.Nsx;
import com.example.assignment.repository.ChucVuRepository;
import com.example.assignment.vo.ChucVuQueryVO;
import com.example.assignment.vo.ChucVuUpdateVO;
import com.example.assignment.vo.ChucVuVO;
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
public class ChucVuService {

    @Autowired
    private ChucVuRepository chucVuRepository;
    private int totalPage =0;
    public List<ChucVuDTO> findAll(){
        List<ChucVu> chucVuList = chucVuRepository.findAll();
        List<ChucVuDTO> chucVuDTOList = new ArrayList<>();
        for (int i = 0; i < chucVuList.size(); i++) {
            ChucVuDTO chucVuDTO = new ChucVuDTO();
            BeanUtils.copyProperties(chucVuList.get(i) , chucVuDTO);
            chucVuDTOList.add(chucVuDTO);
        }
        return chucVuDTOList;
    }

    public List<ChucVuDTO> findAllByPage(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<ChucVu> chucVuPage = chucVuRepository.findAll(pageable);
        this.totalPage= chucVuPage.getTotalPages()-1;
        List<ChucVu> chucVuList = chucVuPage.getContent();
        List<ChucVuDTO> chucVuDTOList = new ArrayList<>();
        for (int i = 0; i < chucVuList.size(); i++) {
            ChucVuDTO chucVuDTO = new ChucVuDTO();
            BeanUtils.copyProperties(chucVuList.get(i) , chucVuDTO);
            chucVuDTOList.add(chucVuDTO);
        }
        return chucVuDTOList;
    }

    public List<ChucVuDTO> findAllByKeySearch(String keySearch , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<ChucVu> chucVuPage = chucVuRepository.findAllByTenLike(keySearch, pageable);
        this.totalPage= chucVuPage.getTotalPages()-1;
        List<ChucVu> chucVuList = chucVuPage.getContent();
        List<ChucVuDTO> chucVuDTOList = new ArrayList<>();
        for (int i = 0; i < chucVuList.size(); i++) {
            ChucVuDTO chucVuDTO = new ChucVuDTO();
            BeanUtils.copyProperties(chucVuList.get(i) , chucVuDTO);
            chucVuDTOList.add(chucVuDTO);
        }
        return chucVuDTOList;
    }

    public Integer save(ChucVuVO vO) {
        ChucVu bean = new ChucVu();
        BeanUtils.copyProperties(vO, bean);
        bean = chucVuRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        chucVuRepository.deleteById(id);
    }

    public void update(Integer id, ChucVuUpdateVO vO) {
        ChucVu bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        chucVuRepository.save(bean);
    }

    public ChucVuDTO getById(Integer id) {
        ChucVu original = requireOne(id);
        return toDTO(original);
    }

    public Page<ChucVuDTO> query(ChucVuQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private ChucVuDTO toDTO(ChucVu original) {
        ChucVuDTO bean = new ChucVuDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private ChucVu requireOne(Integer id) {
        return chucVuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public int getTotalPage(){
        return this.totalPage;
    }
}
