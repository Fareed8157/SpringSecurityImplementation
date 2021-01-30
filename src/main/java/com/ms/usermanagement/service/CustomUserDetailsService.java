package com.ms.usermanagement.service;


import com.ms.usermanagement.exception.ResourceAlreadyExists;
import com.ms.usermanagement.exception.ResourceNotFoundException;
import com.ms.usermanagement.model.User;
import com.ms.usermanagement.repositry.UserRepository;
import com.ms.usermanagement.util.EncryptDecrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService extends BaseServiceImpl<User> implements UserDetailsService {

    final private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        user.orElseThrow(()->new UsernameNotFoundException("User not found with given credentials"));
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), new ArrayList<>());
    }

    public User saveUser(User user){
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        if(userOptional.isPresent())
            throw new ResourceAlreadyExists("User with this email already exist : "+user.getEmail());
        user.setPassword(EncryptDecrypt.encrypt(user.getPassword().trim()));
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUser(String email){
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if(!user.isPresent())
            throw new ResourceNotFoundException("User not found with given credentials");
        return user.get();
    }

    public Map<String,String> deleteUser(UUID id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        userRepository.deleteById(id);
        Map<String,String> response=new HashMap<>();
        response.put("message","deleted successfully");
        return response ;
    }

    public User updateUser(User user){
        Optional<User> userOptional = userRepository.findById(user.getId());
        if(!userOptional.isPresent())
            throw new ResourceAlreadyExists("User with this id does not exist : "+user.getId());
        user.setPassword(EncryptDecrypt.encrypt(user.getPassword().trim()));
        return userRepository.save(user);
    }

    public User findUserById(UUID id) {
        return userRepository.findById(id).get();
    }
}
