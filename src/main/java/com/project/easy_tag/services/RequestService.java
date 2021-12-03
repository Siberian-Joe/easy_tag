package com.project.easy_tag.services;


import com.project.easy_tag.domains.Request;
import com.project.easy_tag.domains.TypeOfRequest;
import com.project.easy_tag.domains.TypesOfRequest;
import com.project.easy_tag.domains.User;
import com.project.easy_tag.repositories.RequestRepository;
import com.project.easy_tag.repositories.TypeOfRequestRepository;
import com.project.easy_tag.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private TypeOfRequestRepository typeOfRequestRepository;

    @PostConstruct
    private void generateTypeOfRequest() {
        for (TypesOfRequest type : TypesOfRequest.values())
            if(typeOfRequestRepository.findByType(type) == null)
                typeOfRequestRepository.save(new TypeOfRequest(type));
    }

    public List<User> findAll() {
        List<User> users = userRepository.findAllByRequestNotNull();

        for(User user : users)
            user.setPassword(null);

        return users;
    }

    public Request save(String id,String type, Request request) {
        User user = userRepository.findById(id).orElseThrow();
        request.setType(typeOfRequestRepository.findByType(TypesOfRequest.valueOf(type)));
        user.setRequest(requestRepository.save(request));

        return userRepository.save(user).getRequest();
    }

    public void delete(Request request) {
        List<User> users = userRepository.findAllByRequestNotNull();
        User rightUser;

        for(User user : users)
            if (user.getRequest().equals(request)) {
                rightUser = userRepository.findById(user.getId()).orElseThrow();
                rightUser.setRequest(null);
                userRepository.save(rightUser);
                break;
            }

        requestRepository.delete(request);
    }
}
