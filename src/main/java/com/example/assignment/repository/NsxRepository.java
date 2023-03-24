package com.example.assignment.repository;

import com.example.assignment.entity.Nsx;
import com.example.assignment.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NsxRepository extends JpaRepository<Nsx, Integer>, JpaSpecificationExecutor<Nsx> {
    Page<Nsx> findAllByTenLike (String keySearch , Pageable pageable);
}