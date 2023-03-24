package com.example.assignment.service;

import com.example.assignment.dto.MauSacDTO;
import com.example.assignment.dto.NsxDTO;
import com.example.assignment.entity.MauSac;
import com.example.assignment.entity.Nsx;
import com.example.assignment.repository.MauSacRepository;
import com.example.assignment.vo.MauSacQueryVO;
import com.example.assignment.vo.MauSacUpdateVO;
import com.example.assignment.vo.MauSacVO;
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
public class MauSacService {

    @Autowired
    private MauSacRepository mauSacRepository;
    private int totalPage =0;

    public List<MauSacDTO> findAll(){
        List<MauSac> mauSacList = mauSacRepository.findAll();
        List<MauSacDTO> mauSacDTOList = new ArrayList<>();
        for (int i = 0; i < mauSacList.size(); i++) {
            MauSacDTO mauSacDTO = new MauSacDTO();
            BeanUtils.copyProperties(mauSacList.get(i) , mauSacDTO);
            mauSacDTOList.add(mauSacDTO);
        }
        return mauSacDTOList;
    }

    public List<MauSacDTO> findAllByPage(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<MauSac> mauSacPage = mauSacRepository.findAll(pageable);
        this.totalPage= mauSacPage.getTotalPages()-1;
        List<MauSac> mauSacList = mauSacPage.getContent();
        List<MauSacDTO> mauSacDTOList = new ArrayList<>();
        for (int i = 0; i < mauSacList.size(); i++) {
            MauSacDTO mauSacDTO = new MauSacDTO();
            BeanUtils.copyProperties(mauSacList.get(i) , mauSacDTO);
            mauSacDTOList.add(mauSacDTO);
        }
        return mauSacDTOList;
    }

    public List<MauSacDTO> findAllByKeySearch(String keySearch , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<MauSac> mauSacPage = mauSacRepository.findAllByTenLike(keySearch ,pageable);
        this.totalPage= mauSacPage.getTotalPages()-1;
        List<MauSac> mauSacList = mauSacPage.getContent();
        List<MauSacDTO> mauSacDTOList = new ArrayList<>();
        for (int i = 0; i < mauSacList.size(); i++) {
            MauSacDTO mauSacDTO = new MauSacDTO();
            BeanUtils.copyProperties(mauSacList.get(i) , mauSacDTO);
            mauSacDTOList.add(mauSacDTO);
        }
        return mauSacDTOList;
    }
    public Integer save(MauSacVO vO) {
        MauSac bean = new MauSac();
        BeanUtils.copyProperties(vO, bean);
        bean = mauSacRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        mauSacRepository.deleteById(id);
    }

    public void update(Integer id, MauSacUpdateVO vO) {
        MauSac bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        mauSacRepository.save(bean);
    }

    public MauSacDTO getById(Integer id) {
        MauSac original = requireOne(id);
        return toDTO(original);
    }

    public Page<MauSacDTO> query(MauSacQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private MauSacDTO toDTO(MauSac original) {
        MauSacDTO bean = new MauSacDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private MauSac requireOne(Integer id) {
        return mauSacRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public int getTotalPage(){
        return totalPage;
    }
}
