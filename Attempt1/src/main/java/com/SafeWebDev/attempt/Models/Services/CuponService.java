package com.SafeWebDev.attempt.Models.Services;

import com.SafeWebDev.attempt.Models.Cupon;
import com.SafeWebDev.attempt.Models.Respositories.CuponRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuponService {

    @Autowired
    CuponRespository cuponRespository;

    public Cupon findById(long id){
        return cuponRespository.getById(id);
    }
}
