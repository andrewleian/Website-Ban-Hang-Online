package com.example.assignment.repository;

import com.example.assignment.entity.ChiTietSanPham;
import com.example.assignment.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer>, JpaSpecificationExecutor<SanPham> {
    Page<SanPham> findAllByTenLike (String keySearch , Pageable pageable);
    Page<SanPham> findAllByChiTietSanPhamsByIdNotNull(Pageable pageable);
    Page<SanPham> findAllByChiTietSanPhamsByIdNotNullAndChiTietSanPhamsByIdDongsanphamId (Integer dspID , Pageable pageable);

//    @Query(value = "select sp from SanPham  sp where sp.chiTietSanPhamsById.size is not null " +
//            "and (select sum (ctsp.soLuongTon) from ChiTietSanPham ctsp where ctsp.sanphamId = sp.id)>0 " +
//            " ")
//    Page<SanPham> findAllByDSP (int idDSP , Pageable pageable);
//    List<ChiTietSanPham> getAll ();
//    @Query(value = "select sp from SanPham  sp where sp.chiTietSanPhamsById != 7 and ctsp.dongsanphamId != 8")
//    Page<SanPham> getAllByPage (Pageable pageable);
}