package com.example.assignment.service;

import com.example.assignment.dto.KhachHangDTO;
import com.example.assignment.dto.NhanVienDTO;
import com.example.assignment.entity.KhachHang;
import com.example.assignment.entity.NhanVien;
import com.example.assignment.repository.KhachHangRepository;
import com.example.assignment.repository.NhanVienRepository;
import com.example.assignment.vo.NhanVienQueryVO;
import com.example.assignment.vo.NhanVienUpdateVO;
import com.example.assignment.vo.NhanVienVO;
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
public class NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;
    private int totalPage =0;
    public List<NhanVienDTO> findAll(){
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        List<NhanVienDTO> nhanVienDTOList = new ArrayList<>();
        for (int i = 0; i < nhanVienList.size(); i++) {
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            BeanUtils.copyProperties(nhanVienList.get(i) , nhanVienDTO);
            nhanVienDTOList.add(nhanVienDTO);
        }
        return nhanVienDTOList;
    }

    public List<NhanVienDTO> findAllByPage(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<NhanVien> nhanVienPage = nhanVienRepository.findAll(pageable);
        this.totalPage= nhanVienPage.getTotalPages()-1;
        List<NhanVien> nhanVienList = nhanVienPage.getContent();
        List<NhanVienDTO> nhanVienDTOList = new ArrayList<>();
        for (int i = 0; i < nhanVienList.size(); i++) {
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            BeanUtils.copyProperties(nhanVienList.get(i) , nhanVienDTO);
            nhanVienDTOList.add(nhanVienDTO);
        }
        return nhanVienDTOList;
    }

    public List<NhanVienDTO> findAllByKeySearch(String keySearch , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<NhanVien> nhanVienPage = nhanVienRepository.findAllByHoTenLike(keySearch, pageable);
        this.totalPage= nhanVienPage.getTotalPages()-1;
        List<NhanVien> nhanVienList = nhanVienPage.getContent();
        List<NhanVienDTO> nhanVienDTOList = new ArrayList<>();
        for (int i = 0; i < nhanVienList.size(); i++) {
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            BeanUtils.copyProperties(nhanVienList.get(i) , nhanVienDTO);
            nhanVienDTOList.add(nhanVienDTO);
        }
        return nhanVienDTOList;
    }
    public Integer save(NhanVienVO vO) {
        NhanVien bean = new NhanVien();
        BeanUtils.copyProperties(vO, bean);
        bean = nhanVienRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        nhanVienRepository.deleteById(id);
    }

    public void update(Integer id, NhanVienUpdateVO vO) {
        NhanVien bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        nhanVienRepository.save(bean);
    }

    public NhanVienDTO getById(Integer id) {
        NhanVien original = requireOne(id);
        return toDTO(original);
    }

    public NhanVienDTO findByUsernameAndPassword(String username , String password) {
        NhanVien original = nhanVienRepository.findByMaAndAndMatKhau(username,password);
        if(original == null){
            return null;
        }
        return toDTO(original);
    }

    public Page<NhanVienDTO> query(NhanVienQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private NhanVienDTO toDTO(NhanVien original) {
        NhanVienDTO bean = new NhanVienDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private NhanVien requireOne(Integer id) {
        return nhanVienRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public int getTotalPage(){
        return this.totalPage;
    }
}
