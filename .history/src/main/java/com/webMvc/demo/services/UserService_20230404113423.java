package com.webMvc.demo.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webMvc.demo.models.User;
import com.webMvc.demo.repositories.UserRepository;
import com.webMvc.demo.services.exceptions.DataBindViolationException;
import com.webMvc.demo.services.exceptions.ObjectNotFoundException;


@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    

    public User findById(Long id) {
        Optional <User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
            "Usuário não encontrado! id: " + id + ", Tipo: " + User.class.getName()
        ));
    }

    @Transactional
    public User create (User obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);
        return obj;


    }

    @Transactional
    public User update(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);

    }

    public void delete(Long id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindViolationException("Não é possível excluir pois existem entidades relacionadas!");
        }
    }

  

    
    
}
