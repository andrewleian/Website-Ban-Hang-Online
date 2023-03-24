package com.example.assignment.repository;

import com.example.assignment.entity.ChiTietSanPham;
import com.example.assignment.entity.GioHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Integer>, JpaSpecificationExecutor<GioHang> {
    Page<GioHang> findAllByKhachHangByKhachhangIdHoTenLike (String keySearch , Pageable pageable);

    GioHang findByTinhTrangAndNhanvienId (Integer tinhTrang , Integer nhanVienID);
    GioHang findByTinhTrangAndKhachhangId (Integer tinhTrang , Integer khachHangID);
}