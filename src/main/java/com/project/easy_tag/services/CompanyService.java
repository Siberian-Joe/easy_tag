package com.project.easy_tag.services;

import com.project.easy_tag.domains.Company;
import com.project.easy_tag.domains.QRCode;
import com.project.easy_tag.domains.User;
import com.project.easy_tag.repositories.CompanyRepository;
import com.project.easy_tag.repositories.QRCodeRepository;
import com.project.easy_tag.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    QRCodeRepository qrCodeRepository;

    @Autowired
    UserRepository userRepository;

    @Value("${qrcodes.path}")
    private String qr_code_path;

    @Value("${logos.path}")
    private String logos_path;

    public Company findById(String id) {
        return companyRepository.findById(id).orElse(null);
    }

    public Company create(User user, Company company) {
        if(user.getCompany() == null) {
            user.setCompany(companyRepository.save(company));
            return userRepository.save(user).getCompany();
        }
        return null;
    }

    public void setQR(String id) {
        Company company = companyRepository.findById(id).orElseThrow();

        if(company.getQrCode() != null)
            qrCodeRepository.delete(qrCodeRepository.findById(company.getQrCode().getId()).orElseThrow());

        company.setQrCode(qrCodeRepository.save(new QRCode(company.getId() + ".png")));

        companyRepository.save(company);
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Company saveLogo(Company company, MultipartFile file) {
        File uploadDir = new File(logos_path);

        if(!uploadDir.exists())
            uploadDir.mkdir();
        else if(company.getLogo() != null)
            (new File(logos_path + company.getLogo())).delete();

        String fileName = company.getId() + "." + file.getOriginalFilename();

        try {
            file.transferTo(new File(logos_path + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        company.setLogo(fileName);

        return companyRepository.save(company);
    }

    public void delete(User user) {
        if(user.getCompany().getQrCode() != null) {
            (new File(qr_code_path + user.getCompany().getQrCode().getName())).delete();
            qrCodeRepository.delete(user.getCompany().getQrCode());
        }
        companyRepository.delete(user.getCompany());
        user.setCompany(null);
        userRepository.save(user);
    }
}
