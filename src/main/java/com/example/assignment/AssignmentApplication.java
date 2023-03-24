package com.example.assignment;

import com.example.assignment.dto.ChiTietSanPhamDTO;
import com.example.assignment.dto.SanPhamDTO;
import com.example.assignment.entity.SanPham;
import com.example.assignment.service.ChiTietSanPhamService;
import com.example.assignment.service.DongSanPhamService;
import com.example.assignment.service.MauSacService;
import com.example.assignment.service.NsxService;
import com.example.assignment.service.SanPhamService;
import com.example.assignment.vo.ChiTietSanPhamVO;
import com.example.assignment.vo.DongSanPhamVO;
import com.example.assignment.vo.MauSacVO;
import com.example.assignment.vo.NsxVO;
import com.example.assignment.vo.SanPhamVO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class AssignmentApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(AssignmentApplication.class, args);

        SanPhamService sanPhamService = context.getBean(SanPhamService.class);
        List<SanPhamDTO> list = sanPhamService.findAllByCTSPAndDSPID(4 ,0);
        System.out.println(list.size());
//
//        SanPhamVO sanPham = context.getBean(SanPhamVO.class);
//        sanPham.setMa("MB01");
//        sanPham.setTen("MacBook Mini M1 2020");
//
//        sanPhamService.save(sanPham);

//        DongSanPhamService dongSanPhamService = context.getBean(DongSanPhamService.class);
//        DongSanPhamVO dongSanPhamVO = context.getBean(DongSanPhamVO.class);
//        dongSanPhamVO.setMa("DSP_Mac");
//        dongSanPhamVO.setTen("Mac");
//        dongSanPhamService.save(dongSanPhamVO);

//        MauSacService mauSacService = context.getBean(MauSacService.class);
//        MauSacVO mauSacVO = context.getBean(MauSacVO.class);
//        mauSacVO.setMa("MS_03");
//        mauSacVO.setTen("Xám");
//        mauSacService.save(mauSacVO);

//        NsxService nsxService = context.getBean(NsxService.class);
//        NsxVO nsxVO = context.getBean(NsxVO.class);
//        nsxVO.setMa("NSX_01");
//        nsxVO.setTen("2020");
//        nsxService.save(nsxVO);


          ChiTietSanPhamService chiTietSanPhamService = context.getBean(ChiTietSanPhamService.class);
//          chiTietSanPhamService.delete(11);
//          chiTietSanPhamService.delete(12);
//          chiTietSanPhamService.delete(13);
//          chiTietSanPhamService.delete(14);
//          chiTietSanPhamService.delete(15);
          //            ChiTietSanPhamVO chiTietSanPhamVO = context.getBean(ChiTietSanPhamVO.class);
//            chiTietSanPhamVO.setSanphamId(9);
//            chiTietSanPhamVO.setNsxId(1);
//            chiTietSanPhamVO.setMausacId(3);
//            chiTietSanPhamVO.setDongsanphamId(1);
//            chiTietSanPhamVO.setNamBaoHanh(1);
//            chiTietSanPhamVO.setMoTa("Máy tính xách tay mỏng và nhẹ nhất của Apple, nay siêu mạnh mẽ với chip Apple M1. Xử lý công việc giúp bạn với CPU 8 lõi nhanh như chớp. Đưa các ứng dụng và game có đồ họa khủng lên một tầm cao mới với GPU 8 lõi. Đồng thời, tăng tốc các tác vụ máy học với Neural Engine 16 lõi. Tất cả gói gọn trong một thiết kế không quạt, không tiếng ồn, thời lượng pin dài nhất từ trước đến nay lên đến 18 giờ.1 MacBook Air. Vẫn cực kỳ cơ động. Mà mạnh mẽ hơn nhiều.");
//            chiTietSanPhamVO.setSoLuongTon(10);
//            chiTietSanPhamVO.setGiaNhap(new BigDecimal("27490000"));
//            chiTietSanPhamVO.setGiaBan(new BigDecimal("19790000"));
//            chiTietSanPhamVO.setAnh("macbook-air-m1-2020-spacegray-thumb-650x650.webp");
//            chiTietSanPhamService.save(chiTietSanPhamVO);

//        List<ChiTietSanPhamDTO> list = chiTietSanPhamService.findAllByPage(0);
//        for (ChiTietSanPhamDTO sp: list) {
//            System.out.println(sp.getSanPhamTen());
//        }

//        System.out.println(new Date());
        long time = System.currentTimeMillis();

        System.out.println(new Date(time));
        System.out.println(new Date());

    }

}
