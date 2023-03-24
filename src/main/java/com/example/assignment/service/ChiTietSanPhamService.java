package com.example.assignment.service;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.GioHangChiTietDTO;
import com.example.assignment.dto.NsxDTO;
import com.example.assignment.entity.ChiTietSanPham;
import com.example.assignment.entity.Nsx;
import com.example.assignment.repository.ChiTietSanPhamRepository;
import com.example.assignment.vo.ChiTietSanPhamQueryVO;
import com.example.assignment.vo.ChiTietSanPhamUpdateVO;
import com.example.assignment.vo.ChiTietSanPhamVO;
import com.example.assignment.vo.GioHangChiTietVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ChiTietSanPhamService {

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;
    private int totalPage =0;

    public List<ChiTietSanPhamDTO> findAll(){
        List<ChiTietSanPham> chiTietSanPhamList = chiTietSanPhamRepository.getAll();
        List<ChiTietSanPhamDTO> chiTietSanPhamDTOList = new ArrayList<>();
        for (int i = 0; i < chiTietSanPhamList.size(); i++) {
            ChiTietSanPhamDTO chiTietSanPhamDTO = new ChiTietSanPhamDTO();
            BeanUtils.copyProperties(chiTietSanPhamList.get(i) , chiTietSanPhamDTO);
            chiTietSanPhamDTOList.add(chiTietSanPhamDTO);
        }
        return chiTietSanPhamDTOList;
    }

    public List<ChiTietSanPhamDTO> findAllByPage(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<ChiTietSanPham> chiTietSanPhamPage = chiTietSanPhamRepository.getAllByPage(pageable);
        this.totalPage= chiTietSanPhamPage.getTotalPages()-1;
        List<ChiTietSanPham> chiTietSanPhamList = chiTietSanPhamPage.getContent();
        List<ChiTietSanPhamDTO> chiTietSanPhamDTOList = new ArrayList<>();
        for (int i = 0; i < chiTietSanPhamList.size(); i++) {
            ChiTietSanPhamDTO chiTietSanPhamDTO = new ChiTietSanPhamDTO();
            BeanUtils.copyProperties(chiTietSanPhamList.get(i) , chiTietSanPhamDTO);
            chiTietSanPhamDTOList.add(chiTietSanPhamDTO);
        }
        return chiTietSanPhamDTOList;
    }

    public List<ChiTietSanPhamDTO> findAllByKeySearch(String keySearch , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<ChiTietSanPham> chiTietSanPhamPage = chiTietSanPhamRepository.findAllBySanPhamBySanphamIdTenLike(keySearch,pageable);
        this.totalPage= chiTietSanPhamPage.getTotalPages()-1;
        List<ChiTietSanPham> chiTietSanPhamList = chiTietSanPhamPage.getContent();
        List<ChiTietSanPhamDTO> chiTietSanPhamDTOList = new ArrayList<>();
        for (int i = 0; i < chiTietSanPhamList.size(); i++) {
            ChiTietSanPhamDTO chiTietSanPhamDTO = new ChiTietSanPhamDTO();
            BeanUtils.copyProperties(chiTietSanPhamList.get(i) , chiTietSanPhamDTO);
            chiTietSanPhamDTOList.add(chiTietSanPhamDTO);
        }
        return chiTietSanPhamDTOList;
    }
    public Integer save(ChiTietSanPhamVO vO) {
        ChiTietSanPham bean = new ChiTietSanPham();
        BeanUtils.copyProperties(vO, bean);
        bean = chiTietSanPhamRepository.save(bean);
        return bean.getId();
    }

    public List<ChiTietSanPhamDTO> findAllByPage(int currentPage){
        Pageable pageable = PageRequest.of(currentPage , 12);
        List<ChiTietSanPham> list = chiTietSanPhamRepository.getAllByPage(pageable).getContent();
        List<ChiTietSanPhamDTO> dtoList = new ArrayList<>();
        int i;
        for(i=0 ; i<list.size(); i++){
            ChiTietSanPhamDTO chiTietSanPhamDTO = new ChiTietSanPhamDTO();
            BeanUtils.copyProperties(list.get(i) , chiTietSanPhamDTO);
            dtoList.add(chiTietSanPhamDTO);
        }
        return dtoList;
    }


    //

    //
    public List<ChiTietSanPhamDTO> findAllBySanPhamID(Integer id){
        List<ChiTietSanPham> list = chiTietSanPhamRepository.getAllBySanPhamID(id);
        List<ChiTietSanPhamDTO> dtoList = new ArrayList<>();
        int i;
        for(i=0 ; i<list.size(); i++){
            ChiTietSanPhamDTO chiTietSanPhamDTO = new ChiTietSanPhamDTO();
            BeanUtils.copyProperties(list.get(i) , chiTietSanPhamDTO);
            dtoList.add(chiTietSanPhamDTO);
        }
        return dtoList;
    }

    public List<ChiTietSanPhamDTO> findAllByDongSanPhamID(Integer id){
        List<ChiTietSanPham> list = chiTietSanPhamRepository.findAllByDongsanphamId(id);
        List<ChiTietSanPhamDTO> dtoList = new ArrayList<>();
        int i;
        for(i=0 ; i<list.size(); i++){
            ChiTietSanPhamDTO chiTietSanPhamDTO = new ChiTietSanPhamDTO();
            BeanUtils.copyProperties(list.get(i) , chiTietSanPhamDTO);
            dtoList.add(chiTietSanPhamDTO);
        }
        return dtoList;
    }

    public List<ChiTietSanPhamDTO> findAllByDongSanPhamID(Integer id , Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage , 5);
        Page<ChiTietSanPham> chiTietSanPhamPage = chiTietSanPhamRepository.findAllByDongsanphamId(id,pageable);
        this.totalPage= chiTietSanPhamPage.getTotalPages()-1;
        List<ChiTietSanPham> chiTietSanPhamList = chiTietSanPhamPage.getContent();
        List<ChiTietSanPhamDTO> chiTietSanPhamDTOList = new ArrayList<>();
        for (int i = 0; i < chiTietSanPhamList.size(); i++) {
            ChiTietSanPhamDTO chiTietSanPhamDTO = new ChiTietSanPhamDTO();
            BeanUtils.copyProperties(chiTietSanPhamList.get(i) , chiTietSanPhamDTO);
            chiTietSanPhamDTOList.add(chiTietSanPhamDTO);
        }
        return chiTietSanPhamDTOList;
    }

    public boolean checkTopSeller(Integer sanPhamID){
        List<ChiTietSanPham> list = chiTietSanPhamRepository.checkTopSeller(sanPhamID);
        if(list.isEmpty() || list == null){
            return false;
        }
        return true;
    }

    public boolean checkTopNew(Integer sanPhamID){
        List<ChiTietSanPham> list = chiTietSanPhamRepository.checkTopNew(sanPhamID);
        if(list.isEmpty() || list == null){
            return false;
        }
        return true;
    }

    public void delete(Integer id) {
        chiTietSanPhamRepository.deleteById(id);
    }

    public void update(Integer id, ChiTietSanPhamUpdateVO vO) {
        ChiTietSanPham bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        chiTietSanPhamRepository.save(bean);
    }

    public ChiTietSanPhamDTO getById(Integer id) {
        ChiTietSanPham original = requireOne(id);
        return toDTO(original);
    }

    public Page<ChiTietSanPhamDTO> query(ChiTietSanPhamQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private ChiTietSanPhamDTO toDTO(ChiTietSanPham original) {
        ChiTietSanPhamDTO bean = new ChiTietSanPhamDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private ChiTietSanPham requireOne(Integer id) {
        return chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public int getTotalPage(){
        return this.totalPage;
    }
}
