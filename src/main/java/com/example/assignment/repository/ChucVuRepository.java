package com.example.assignment.repository;

import com.example.assignment.entity.ChucVu;
import com.example.assignment.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChucVuRepository extends JpaRepository<ChucVu, Integer>, JpaSpecificationExecutor<ChucVu> {
    Page<ChucVu> findAllByTenLike (String keySearch , Pageable pageable);
}