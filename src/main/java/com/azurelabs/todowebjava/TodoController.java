package com.azurelabs.todowebjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private final TodoItemRepository todoRepo;

    public TodoController(TodoItemRepository repository) {
        this.todoRepo = repository;
    }

    @GetMapping("/todo")
    public ResponseEntity<Iterable<TodoItem>> GetAllItems() {
        Iterable<TodoItem> items = todoRepo.findAll();

        return new ResponseEntity<Iterable<TodoItem>>(items, HttpStatus.OK);
    }

    @PostMapping("/todo")
    public ResponseEntity<TodoItem> CreateTodoItem(@RequestBody TodoItem item) {
        TodoItem newItem = new TodoItem();
        newItem.setName(item.getName());
        newItem.setIsCompleted(item.getIsCompleted());

        return new ResponseEntity<TodoItem>(todoRepo.save(newItem), HttpStatus.CREATED);
    }

    @PutMapping("/todo/{id}")
    public ResponseEntity<TodoItem> UpdateTodoItem(@RequestBody TodoItem newItem, @PathVariable Long id) {
        TodoItem retItem = todoRepo.findById(id)
            .map(item -> {
                item.setName(newItem.getName());
                item.setIsCompleted(newItem.getIsCompleted());
                return todoRepo.save(item);
            })
            .orElseGet(() -> {
                newItem.setId(id);
                return todoRepo.save(newItem);
            });
        
        return new ResponseEntity<TodoItem>(retItem, HttpStatus.NO_CONTENT);
    }
}