package com.example.assignment.repository;

import com.example.assignment.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer>, JpaSpecificationExecutor<HoaDon> {
    Page<HoaDon> findAllByNhanVienByNhanvienIdHoTenLike(String keySearch , Pageable pageable);
    Page<HoaDon> findAllByKhachhangId(Integer keySearch , Pageable pageable);
    Page<HoaDon> findAllByNhanVienByNhanvienId(Integer keySearch , Pageable pageable);
}