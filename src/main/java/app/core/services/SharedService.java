package app.core.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.repositories.CouponRepository;

@Service
@Transactional
public class SharedService {

    private CouponRepository couponRepository;

    @Autowired
    public SharedService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public List<Coupon> getCoupons(String sortBy) {
        List<Coupon> coupons = couponRepository.findAll(Sort.by(sortBy));
        return setCouponImages(coupons);
    }

    public List<Coupon> getCouponsByCategory(Category category, String sortBy) {
        List<Coupon> coupons = couponRepository.findByCategory(category, Sort.by(sortBy));
        return setCouponImages(coupons);
    }

    public Coupon getCoupon(int id) throws CouponSystemException {
        Coupon coupon = couponRepository.findFirstById(id);
        if (coupon == null) {
            throw new CouponSystemException("The coupon with id: " + id + " not exists in the database.");
        }
        List<Coupon> temp = new ArrayList<Coupon>();
        temp.add(coupon);
        return setCouponImages(temp).get(0);
    }

    public List<Coupon> setCouponImages(List<Coupon> coupons) {
        for (Coupon coupon : coupons) {
            String directoryPath = "C:\\Users\\User\\Documents\\GitHub\\Coupon-System_Server\\src\\main\\resources\\static\\pics\\"
                    + coupon.getCategory() + "\\" + coupon.getId();
            File directory = new File(directoryPath);
            File[] images = directory.listFiles();
            String[] imagesNames;
            try {
                imagesNames = new String[images.length];
            } catch (Exception e) {
                continue;
            }
            for (int i = 0; i < images.length; i++) {
                imagesNames[i] = images[i].getName();
            }
            coupon.setImagesNames(imagesNames);
        }
        return coupons;
    }
}
