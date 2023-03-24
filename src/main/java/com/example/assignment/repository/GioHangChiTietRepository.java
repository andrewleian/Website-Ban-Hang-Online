package com.example.assignment.repository;

import com.example.assignment.dto.GioHangChiTietDTO;
import com.example.assignment.entity.ChiTietSanPham;
import com.example.assignment.entity.GioHangChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Integer>, JpaSpecificationExecutor<GioHangChiTiet> {
    Page<GioHangChiTiet> findAllBySoLuong (Integer keySearch , Pageable pageable);
    GioHangChiTiet findByGiohangchitietGiohangAndGiohangchitietChitietsanpham (Integer gioHang , Integer chiTietSanPham);
    List<GioHangChiTiet> findByGiohangchitietGiohang (Integer gioHang);

    @Query(value = "select sum (soLuong) from GioHangChiTiet where giohangchitietGiohang = ?1")
    int getTotalCartProduct (Integer gioHangId);
    @Query(value = "select sum (donGiaKhiGia) from GioHangChiTiet where giohangchitietGiohang = ?1")
    int getTotalPriceProduct (Integer gioHangId);
}