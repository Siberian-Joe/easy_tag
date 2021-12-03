package com.project.easy_tag.services;


import com.project.easy_tag.domains.*;
import com.project.easy_tag.repositories.RequestRepository;
import com.project.easy_tag.repositories.ResponseRepository;
import com.project.easy_tag.repositories.TypeOfResponseRepository;
import com.project.easy_tag.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ResponseService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private TypeOfResponseRepository typeOfResponseRepository;

    @PostConstruct
    private void generateTypeOfRequest() {
        for (TypesOfResponse type : TypesOfResponse.values())
            if(typeOfResponseRepository.findByType(type) == null)
                typeOfResponseRepository.save(new TypeOfResponse(type));
    }

    public Response save(Response response, String type,Request request) {
        List<User> users = userRepository.findAllByRequestNotNull();

        for(User user : users)
            if (user.getRequest().equals(request)) {
                User rightUser = userRepository.findById(user.getId()).orElseThrow();
                response.setType(typeOfResponseRepository.findByType(TypesOfResponse.valueOf(type)));
                Response newResponse = responseRepository.save(response);

                if(rightUser.getResponse() != null)
                    responseRepository.delete(rightUser.getResponse());

                rightUser.setRequest(null);
                rightUser.setResponse(newResponse);

                userRepository.save(rightUser);

                requestRepository.delete(request);
                return newResponse;
            }

        return null;
    }
}
