package com.example.assignment.repository;

import com.example.assignment.entity.ChiTietSanPham;
import com.example.assignment.entity.KhachHang;
import com.example.assignment.entity.SanPham;
import com.example.assignment.vo.KhachHangUpdateVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer>, JpaSpecificationExecutor<KhachHang> {
    Page<KhachHang> findAllByHoTenLike (String keySearch , Pageable pageable);
    KhachHang findByMaAndAndMatKhau (String username , String password);

    @Query(value = "select kh from KhachHang  kh where kh.ma =?1 and kh.hoTen = ?2 and kh.ngaySinh = ?3 and kh.sdt = ?4 and kh.diaChi = ?5 ")
    List<KhachHang> checkForgotPass (String ma , String hoTen , Date ngaySinh , String sdt , String diaChi);
}