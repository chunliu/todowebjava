package com.azurelabs.todowebjava;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TodoItem {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;
    private boolean isCompleted;

    public TodoItem() {

    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsCompleted() {
        return this.isCompleted;
    }
    public void setIsCompleted(boolean completed) {
        this.isCompleted = completed;
    }
}