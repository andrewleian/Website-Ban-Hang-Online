package com.example.assignment.repository;

import com.example.assignment.entity.DongSanPham;
import com.example.assignment.entity.Nsx;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DongSanPhamRepository extends JpaRepository<DongSanPham, Integer>, JpaSpecificationExecutor<DongSanPham> {
    Page<DongSanPham> findAllByTenLike (String keySearch , Pageable pageable);


    @Query(value = "select dsp from DongSanPham  dsp where dsp.id != 7 and dsp.id !=8 ")
    List<DongSanPham> getAll ();

    @Query(value = "select dsp from DongSanPham  dsp where dsp.id != 7 and dsp.id !=8 ")
    Page<DongSanPham> getAll (Pageable pageable);
}