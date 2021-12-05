package com.project.easy_tag.controllers;

import com.project.easy_tag.domains.Request;
import com.project.easy_tag.domains.TypeOfRequest;
import com.project.easy_tag.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    RequestService requestService;

    @GetMapping("/types")
    public List<TypeOfRequest> typesOfRequest() {
        return requestService.findAllTypes();
    }

    @PostMapping("/{id}")
    public Request save(@PathVariable String id, @RequestParam String type, @RequestBody Request request) {
        return requestService.save(id, type, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Request request) {
        requestService.delete(request);
    }
}
