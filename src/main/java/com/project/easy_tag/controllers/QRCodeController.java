package com.project.easy_tag.controllers;

import com.project.easy_tag.generators.QRCodeGenerator;
import com.project.easy_tag.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/company")
public class QRCodeController {

    @Autowired
    private CompanyService companyService;

    @Value("${qrcodes.path}")
    private String qr_code_path;

    private static final Integer WIDTH = 490;
    private static final Integer HEIGHT = 490;


    @GetMapping("/genrateqrcode/{code}")
    public void download(@PathVariable("code") String code, @PathParam("path") String path)
            throws Exception {
        QRCodeGenerator.generateQRCodeImage(path + code, WIDTH, HEIGHT, qr_code_path + code + ".png");
        companyService.setQR(code);
    }
}