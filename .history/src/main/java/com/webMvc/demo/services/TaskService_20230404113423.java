package com.webMvc.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webMvc.demo.models.Task;
import com.webMvc.demo.models.User;
import com.webMvc.demo.repositories.TaskRepository;
import com.webMvc.demo.services.exceptions.DataBindViolationException;
import com.webMvc.demo.services.exceptions.ObjectNotFoundException;


@Service
public class TaskService {


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;


    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException(
            "Tarefa não encontrada! Id: " + id + ", Tipo: " + Task.class.getName()
        ));
    }

    public List<Task> findAllByUserId(Long userId){
        List<Task> tasks = this.taskRepository.findByUser_Id(userId);
        return tasks;
    }


    @Transactional
    public Task create(Task obj) {
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj){
        Task newobj = findById(obj.getId());
        newobj.setDescription(obj.getDescription());
        return this.taskRepository.save(newobj);
    }

    public void delete(Long id){
        findById(id);
        try {
            this.taskRepository.deleteAllById(null);
        } catch (Exception e) {
           throw new DataBindViolationException("Não é possível excluir pois existem entidades relacionadas!");
        }
    }


    

}
