package com.ravi.mukti.controller;

import com.ravi.mukti.entity.Peserta;
import com.ravi.mukti.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/registration")
public class RegistrasiController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/form")
    public ModelMap tampilkanFormRegistrasi(){
        log.info("Menjalankan log tampilkanFormRegistrasi");
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("nama", "Ravi");
        modelMap.addAttribute("waktu", LocalDateTime.now());
        return modelMap;
    }


    @PostMapping("/form")
    public String prosesFormRegistrasi(@ModelAttribute @Valid Peserta peserta, BindingResult error, SessionStatus status){
        log.info("Menjalankan Command Insert");

        if (error.hasErrors()){
            return "form";
        }

        registrationService.registrasiPesertaBaru(peserta);
        status.setComplete();
        /**
         * Cara yang salah, jangan return html tapi pakai redirect
         * ModelAndView modelAndView = new ModelAndView("konfirmasi");
         * return  modelAndView;
         */
        return "redirect:confirmation";
    }

    @GetMapping("/confirmation")
    public void tampilkanHalamanKonfirmasi(){

    }

    @GetMapping("/verify")
    public String verifikasiEmail(@RequestParam String token) {
        registrationService.verifikasiToken(token);
        return "redirect:verified";
    }

    @GetMapping("/verified")
    public void emailTerverifikasi() {

    }


}
