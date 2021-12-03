package com.project.easy_tag.controllers;

import com.project.easy_tag.generators.QRCodeGenerator;
import com.project.easy_tag.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class QRCodeController {

    @Autowired
    private CompanyService companyService;

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/qrcodes/";
    private static final Integer WIDTH = 490;
    private static final Integer HEIGHT = 490;

    @GetMapping("/genrateQRCode/{code}")
    public void download(@PathVariable("code") String code, @PathParam("path") String path)
            throws Exception {
        QRCodeGenerator.generateQRCodeImage(path + code, WIDTH, HEIGHT, QR_CODE_IMAGE_PATH + code + ".png");
        companyService.setQR(code);
    }
}