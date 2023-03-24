package com.example.assignment.repository;

import com.example.assignment.entity.MauSac;
import com.example.assignment.entity.Nsx;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Integer>, JpaSpecificationExecutor<MauSac> {
    Page<MauSac> findAllByTenLike (String keySearch , Pageable pageable);
}