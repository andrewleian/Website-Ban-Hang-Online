package com.example.assignment.repository;

import com.example.assignment.entity.HoaDon;
import com.example.assignment.entity.HoaDonChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer>, JpaSpecificationExecutor<HoaDonChiTiet> {
    Page<HoaDonChiTiet> findAllBySoLuong(Integer keySearch , Pageable pageable);

    List<HoaDonChiTiet> findAllByHoadonchitietHoadon (Integer hoaDonID);
}