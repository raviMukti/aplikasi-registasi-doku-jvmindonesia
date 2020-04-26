package com.ravi.mukti.repository;

import com.ravi.mukti.entity.VerifikasiEmail;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VerifikasiEmailRepository extends PagingAndSortingRepository<VerifikasiEmail, String> {
    VerifikasiEmail findByToken(String token);
}
