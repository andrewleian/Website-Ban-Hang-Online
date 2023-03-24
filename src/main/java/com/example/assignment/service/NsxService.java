package com.example.assignment.service;

import com.example.assignment.dto.NsxDTO;
import com.example.assignment.dto.SanPhamDTO;
import com.example.assignment.entity.Nsx;
import com.example.assignment.entity.SanPham;
import com.example.assignment.repository.NsxRepository;
import com.example.assignment.vo.NsxQueryVO;
import com.example.assignment.vo.NsxUpdateVO;
import com.example.assignment.vo.NsxVO;
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
public class NsxService {

    @Autowired
    private NsxRepository nsxRepository;
    private int totalPage =0;
    public List<NsxDTO> findAll(){
        List<Nsx> nsxList = nsxRepository.findAll();
        List<NsxDTO> nsxDTOList = new ArrayList<>();
        for (int i = 0; i < nsxList.size(); i++) {
            NsxDTO nsxDTO = new NsxDTO();
            BeanUtils.copyProperties(nsxList.get(i) , nsxDTO);
            nsxDTOList.add(nsxDTO);
        }
        return nsxDTOList;
    }

    public List<NsxDTO> findAllByPage(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<Nsx> nsxPage = nsxRepository.findAll(pageable);
        this.totalPage= nsxPage.getTotalPages()-1;
        List<Nsx> nsxList = nsxPage.getContent();
        List<NsxDTO> nsxDTOList = new ArrayList<>();
        for (int i = 0; i < nsxList.size(); i++) {
            NsxDTO nsxDTO = new NsxDTO();
            BeanUtils.copyProperties(nsxList.get(i) , nsxDTO);
            nsxDTOList.add(nsxDTO);
        }
        return nsxDTOList;
    }

    public List<NsxDTO> findAllByKeySearch(String keySearch , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<Nsx> nsxPage = nsxRepository.findAllByTenLike(keySearch,pageable);
        this.totalPage= nsxPage.getTotalPages()-1;
        List<Nsx> nsxList = nsxPage.getContent();
        List<NsxDTO> nsxDTOList = new ArrayList<>();
        for (int i = 0; i < nsxList.size(); i++) {
            NsxDTO nsxDTO = new NsxDTO();
            BeanUtils.copyProperties(nsxList.get(i) , nsxDTO);
            nsxDTOList.add(nsxDTO);
        }
        return nsxDTOList;
    }

    public Integer save(NsxVO vO) {
        Nsx bean = new Nsx();
        BeanUtils.copyProperties(vO, bean);
        bean = nsxRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        nsxRepository.deleteById(id);
    }

    public void update(Integer id, NsxUpdateVO vO) {
        Nsx bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        nsxRepository.save(bean);
    }

    public NsxDTO getById(Integer id) {
        Nsx original = requireOne(id);
        return toDTO(original);
    }

    public Page<NsxDTO> query(NsxQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private NsxDTO toDTO(Nsx original) {
        NsxDTO bean = new NsxDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Nsx requireOne(Integer id) {
        return nsxRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public int getTotalPage(){
        return this.totalPage;
    }
}
