package com.SafeWebDev.attempt.Models.Services;

import com.SafeWebDev.attempt.Models.*;
import com.SafeWebDev.attempt.Models.Respositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuponService {

    @Autowired
    CuponRespository cuponRespository;

    public Cupon findById(long id){
        return cuponRespository.getById(id);
    }
    public List<Cupon> getAll(){
        return cuponRespository.findAll();
    }
    public void addCupon(Cupon coupon){
        cuponRespository.save(coupon);
    }
    public boolean exists(long id){
        return cuponRespository.existsById(id);
    }
}
