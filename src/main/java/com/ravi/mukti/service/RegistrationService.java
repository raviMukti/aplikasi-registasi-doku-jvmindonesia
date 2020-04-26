package com.ravi.mukti.service;

import com.ravi.mukti.entity.Peserta;
import com.ravi.mukti.entity.VerifikasiEmail;
import com.ravi.mukti.repository.PesertaRepository;
import com.ravi.mukti.repository.VerifikasiEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationService {

    @Value("${token.expiry.days}")
    private Integer tokenExpiryDays;

    @Autowired
    private PesertaRepository pesertaRepository;

    @Autowired
    private VerifikasiEmailRepository verifikasiEmailRepository;

    @Transactional
    public void registrasiPesertaBaru(Peserta p){
        VerifikasiEmail verifikasiEmail = new VerifikasiEmail();
        verifikasiEmail.setPeserta(p);
        verifikasiEmail.setToken(UUID.randomUUID().toString());
        verifikasiEmail.setExpire(LocalDateTime.now().plusDays(tokenExpiryDays));
        pesertaRepository.save(p);
        verifikasiEmailRepository.save(verifikasiEmail);
    }

    @Transactional
    public void verifikasiToken(String token) {
        VerifikasiEmail verifikasiEmail = verifikasiEmailRepository.findByToken(token);
        if (verifikasiEmail != null){
            Peserta p = verifikasiEmail.getPeserta();
            p.setEmailTerverifikasi(true);
            verifikasiEmailRepository.delete(verifikasiEmail);
        }
    }
}
