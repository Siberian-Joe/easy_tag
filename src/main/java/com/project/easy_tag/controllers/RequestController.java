package com.project.easy_tag.controllers;

import com.project.easy_tag.domains.Request;
import com.project.easy_tag.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    RequestService requestService;

    @PostMapping("/{id}")
    public Request save(@PathVariable String id, @RequestParam String type, @RequestBody Request request) {
        return requestService.save(id, type, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Request request) {
        requestService.delete(request);
    }
}
