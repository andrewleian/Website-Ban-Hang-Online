package com.example.assignment.repository;

import com.example.assignment.entity.CuaHang;
import com.example.assignment.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CuaHangRepository extends JpaRepository<CuaHang, Integer>, JpaSpecificationExecutor<CuaHang> {
    Page<CuaHang> findAllByTenLike (String keySearch , Pageable pageable);
}