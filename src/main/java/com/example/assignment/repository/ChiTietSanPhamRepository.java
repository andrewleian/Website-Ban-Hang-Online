package com.example.assignment.repository;

import com.example.assignment.entity.ChiTietSanPham;
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
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer>, JpaSpecificationExecutor<ChiTietSanPham> {
    Page<ChiTietSanPham> findAllBySanPhamBySanphamIdTenLike (String keySearch , Pageable pageable);


    @Query(value = "select ctsp from ChiTietSanPham  ctsp where ctsp.dongsanphamId != 7 and ctsp.dongsanphamId != 8 and ctsp.sanphamId = ?1")
    List<ChiTietSanPham> getAllBySanPhamID (Integer id);

    @Query(value = "select ctsp from ChiTietSanPham  ctsp where ctsp.dongsanphamId != 7 and ctsp.dongsanphamId != 8")
    List<ChiTietSanPham> getAll ();

    @Query(value = "select ctsp from ChiTietSanPham  ctsp where ctsp.dongsanphamId != 7 and ctsp.dongsanphamId != 8 and ctsp.sanphamId = ?1")
    Page<ChiTietSanPham> getAllBySanPhamIDAndPage (Integer id , Pageable pageable);

    @Query(value = "select ctsp from ChiTietSanPham  ctsp where ctsp.dongsanphamId != 7 and ctsp.dongsanphamId != 8")
    Page<ChiTietSanPham> getAllByPage (Pageable pageable);

    List<ChiTietSanPham> findAllBySanphamId (Integer id);

    @Query(value = "select ctsp from ChiTietSanPham  ctsp where ctsp.sanphamId = ?1 and ctsp.dongsanphamId = 7 ")
    List<ChiTietSanPham> checkTopSeller (Integer sanPhamID);

    @Query(value = "select ctsp from ChiTietSanPham  ctsp where ctsp.sanphamId = ?1 and ctsp.dongsanphamId = 8 ")
    List<ChiTietSanPham> checkTopNew (Integer sanPhamID);

    List<ChiTietSanPham> findAllByDongsanphamId (Integer dongSanPhamID);
    Page<ChiTietSanPham> findAllByDongsanphamId (Integer dongSanPhamID , Pageable pageable);
}