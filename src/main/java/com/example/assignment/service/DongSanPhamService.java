package com.example.assignment.service;

import com.example.assignment.dto.DongSanPhamDTO;
import com.example.assignment.dto.NsxDTO;
import com.example.assignment.entity.DongSanPham;
import com.example.assignment.entity.Nsx;
import com.example.assignment.repository.DongSanPhamRepository;
import com.example.assignment.vo.DongSanPhamQueryVO;
import com.example.assignment.vo.DongSanPhamUpdateVO;
import com.example.assignment.vo.DongSanPhamVO;
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
public class DongSanPhamService {

    @Autowired
    private DongSanPhamRepository dongSanPhamRepository;
    private int totalPage =0;
    public List<DongSanPhamDTO> findAll(){
        List<DongSanPham> dongSanPhamList = dongSanPhamRepository.getAll();
        List<DongSanPhamDTO> dongSanPhamDTOList = new ArrayList<>();
        for (int i = 0; i < dongSanPhamList.size(); i++) {
            DongSanPhamDTO dongSanPhamDTO = new DongSanPhamDTO();
            BeanUtils.copyProperties(dongSanPhamList.get(i) , dongSanPhamDTO);
            dongSanPhamDTOList.add(dongSanPhamDTO);
        }
        return dongSanPhamDTOList;
    }

    public List<DongSanPhamDTO> findAllByPage(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<DongSanPham> dongSanPhamPage = dongSanPhamRepository.getAll(pageable);
        this.totalPage= dongSanPhamPage.getTotalPages()-1;
        List<DongSanPham> dongSanPhamList = dongSanPhamPage.getContent();
        List<DongSanPhamDTO> dongSanPhamDTOList = new ArrayList<>();
        for (int i = 0; i < dongSanPhamList.size(); i++) {
            DongSanPhamDTO dongSanPhamDTO = new DongSanPhamDTO();
            BeanUtils.copyProperties(dongSanPhamList.get(i) , dongSanPhamDTO);
            dongSanPhamDTOList.add(dongSanPhamDTO);
        }
        return dongSanPhamDTOList;
    }

    public List<DongSanPhamDTO> findAllByKeySearch(String keySearch , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<DongSanPham> dongSanPhamPage = dongSanPhamRepository.findAllByTenLike(keySearch , pageable);
        this.totalPage= dongSanPhamPage.getTotalPages()-1;
        List<DongSanPham> dongSanPhamList = dongSanPhamPage.getContent();
        List<DongSanPhamDTO> dongSanPhamDTOList = new ArrayList<>();
        for (int i = 0; i < dongSanPhamList.size(); i++) {
            DongSanPhamDTO dongSanPhamDTO = new DongSanPhamDTO();
            BeanUtils.copyProperties(dongSanPhamList.get(i) , dongSanPhamDTO);
            dongSanPhamDTOList.add(dongSanPhamDTO);
        }
        return dongSanPhamDTOList;
    }



    public Integer save(DongSanPhamVO vO) {
        DongSanPham bean = new DongSanPham();
        BeanUtils.copyProperties(vO, bean);
        bean = dongSanPhamRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        dongSanPhamRepository.deleteById(id);
    }

    public void update(Integer id, DongSanPhamUpdateVO vO) {
        DongSanPham bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        dongSanPhamRepository.save(bean);
    }

    public DongSanPhamDTO getById(Integer id) {
        DongSanPham original = requireOne(id);
        return toDTO(original);
    }

    public Page<DongSanPhamDTO> query(DongSanPhamQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private DongSanPhamDTO toDTO(DongSanPham original) {
        DongSanPhamDTO bean = new DongSanPhamDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private DongSanPham requireOne(Integer id) {
        return dongSanPhamRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public int getTotalPage(){
        return this.totalPage;
    }
}
