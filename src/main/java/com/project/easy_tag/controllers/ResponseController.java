package com.project.easy_tag.controllers;

import com.project.easy_tag.domains.Request;
import com.project.easy_tag.domains.Response;
import com.project.easy_tag.repositories.UserRepository;
import com.project.easy_tag.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/response")
public class ResponseController {

    @Autowired
    ResponseService responseService;

    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}")
    public Response save(@PathVariable("id") Request request, @PathParam("type") String type, @RequestBody Response response) {
        return responseService.save(response, type ,request);
    }
}
