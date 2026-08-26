package com.study.zomato_clone.service;

import com.study.zomato_clone.dto.UserRequestDTO;
import com.study.zomato_clone.dto.UserResponseDTO;
import com.study.zomato_clone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<List<UserResponseDTO>> findAll() {

    }

    public ResponseEntity<List<UserResponseDTO>> findByName(String name) {
    }

    public ResponseEntity<String> addUser(UserRequestDTO userRequestDTO) {
    }

    public ResponseEntity<String> updateUser(UserRequestDTO userRequestDTO) {
    }

    public ResponseEntity<String> deleteUser(Long id) {
    }
}
