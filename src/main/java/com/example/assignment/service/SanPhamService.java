package com.example.assignment.service;

import com.example.assignment.dto.SanPhamDTO;
import com.example.assignment.entity.SanPham;
import com.example.assignment.repository.SanPhamRepository;
import com.example.assignment.vo.SanPhamQueryVO;
import com.example.assignment.vo.SanPhamUpdateVO;
import com.example.assignment.vo.SanPhamVO;
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
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;
    private int totalPage =0;
    public List<SanPhamDTO> findAll(){
        List<SanPham> sanPhamList = sanPhamRepository.findAll();
        List<SanPhamDTO> sanPhamDTOList = new ArrayList<>();
        for (int i = 0; i < sanPhamList.size(); i++) {
            SanPhamDTO sanPhamDTO = new SanPhamDTO();
            BeanUtils.copyProperties(sanPhamList.get(i) , sanPhamDTO);
            sanPhamDTOList.add(sanPhamDTO);
        }
        return sanPhamDTOList;
    }

    public List<SanPhamDTO> findAllByPage(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<SanPham> sanPhamPage = sanPhamRepository.findAll(pageable);
        this.totalPage= sanPhamPage.getTotalPages()-1;
        List<SanPham> sanPhamList = sanPhamPage.getContent();
        List<SanPhamDTO> sanPhamDTOList = new ArrayList<>();
        for (int i = 0; i < sanPhamList.size(); i++) {
            SanPhamDTO sanPhamDTO = new SanPhamDTO();
            BeanUtils.copyProperties(sanPhamList.get(i) , sanPhamDTO);
            sanPhamDTOList.add(sanPhamDTO);
        }
        return sanPhamDTOList;
    }

    public List<SanPhamDTO> findAllByCTSP(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 99);
        Page<SanPham> sanPhamPage = sanPhamRepository.findAllByChiTietSanPhamsByIdNotNull(pageable);
        this.totalPage= sanPhamPage.getTotalPages()-1;
        List<SanPham> sanPhamList = sanPhamPage.getContent();
        List<SanPhamDTO> sanPhamDTOList = new ArrayList<>();
        for (int i = 0; i < sanPhamList.size(); i++) {
            SanPhamDTO sanPhamDTO = new SanPhamDTO();
            BeanUtils.copyProperties(sanPhamList.get(i) , sanPhamDTO);
            sanPhamDTOList.add(sanPhamDTO);
        }
        return sanPhamDTOList;
    }

    public List<SanPhamDTO> findAllByCTSPAndDSPID(Integer dspID ,Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 99);
        Page<SanPham> sanPhamPage = sanPhamRepository.findAllByChiTietSanPhamsByIdNotNullAndChiTietSanPhamsByIdDongsanphamId(dspID,pageable);
        this.totalPage= sanPhamPage.getTotalPages()-1;
        List<SanPham> sanPhamList = sanPhamPage.getContent();
        List<SanPhamDTO> sanPhamDTOList = new ArrayList<>();
        for (int i = 0; i < sanPhamList.size(); i++) {
            SanPhamDTO sanPhamDTO = new SanPhamDTO();
            BeanUtils.copyProperties(sanPhamList.get(i) , sanPhamDTO);
            sanPhamDTOList.add(sanPhamDTO);
        }
        return sanPhamDTOList;
    }

    public List<SanPhamDTO> findAllByKeySearch(String keySearch , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<SanPham> sanPhamPage = sanPhamRepository.findAllByTenLike(keySearch , pageable);
        this.totalPage= sanPhamPage.getTotalPages()-1;
        List<SanPham> sanPhamList = sanPhamPage.getContent();
        List<SanPhamDTO> sanPhamDTOList = new ArrayList<>();
        for (int i = 0; i < sanPhamList.size(); i++) {
            SanPhamDTO sanPhamDTO = new SanPhamDTO();
            BeanUtils.copyProperties(sanPhamList.get(i) , sanPhamDTO);
            sanPhamDTOList.add(sanPhamDTO);
        }
        return sanPhamDTOList;
    }

    public Integer save(SanPhamVO vO) {
        SanPham bean = new SanPham();
        BeanUtils.copyProperties(vO, bean);
        bean = sanPhamRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        sanPhamRepository.deleteById(id);
    }

    public void update(Integer id, SanPhamUpdateVO vO) {
        SanPham bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        sanPhamRepository.save(bean);
    }

    public SanPhamDTO getById(Integer id) {
        SanPham original = requireOne(id);
        return toDTO(original);
    }

    public Page<SanPhamDTO> query(SanPhamQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private SanPhamDTO toDTO(SanPham original) {
        SanPhamDTO bean = new SanPhamDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private SanPham requireOne(Integer id) {
        return sanPhamRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public int getTotalPage(){
        return this.totalPage;
    }
}
