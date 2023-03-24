package com.example.assignment.repository;

import com.example.assignment.entity.KhachHang;
import com.example.assignment.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer>, JpaSpecificationExecutor<NhanVien> {
    Page<NhanVien> findAllByHoTenLike (String keySearch , Pageable pageable);
    NhanVien findByMaAndAndMatKhau (String username , String password);
}