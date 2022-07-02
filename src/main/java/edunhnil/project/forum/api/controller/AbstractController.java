package edunhnil.project.forum.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edunhnil.project.forum.api.exception.ResourceNotFoundException;

public abstract class AbstractController<s> {
    @Autowired
    protected s service;

    protected <T> ResponseEntity<T> response(Optional<T> response) {
        return new ResponseEntity<T>(response.orElseThrow(() -> {
            throw new ResourceNotFoundException("Resource not found");
        }), HttpStatus.OK);
    }
}
