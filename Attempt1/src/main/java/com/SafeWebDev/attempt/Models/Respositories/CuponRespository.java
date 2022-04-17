package com.SafeWebDev.attempt.Models.Respositories;

import com.SafeWebDev.attempt.Models.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuponRespository extends JpaRepository<Cupon, Long> {
}
