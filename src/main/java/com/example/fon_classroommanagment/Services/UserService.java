package com.example.fon_classroommanagment.Services;

import com.example.fon_classroommanagment.Configuration.UserProfileDetails;
import com.example.fon_classroommanagment.Models.User.UserProfile;
import com.example.fon_classroommanagment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
   @Autowired
   private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile user=findByEmail(username);
        if(user==null) throw  new UsernameNotFoundException("Please register,user does not exit");
        return new UserProfileDetails(user);
    }

    public UserProfile findByEmail(String email){
       return userRepository.findByEmail(email);
    }
    //save vraca uvek ne null ker radi update ili insert
    public boolean saveUser(UserProfile user){
        if(user==null) return false;
        userRepository.save(user);
        return true;

    }
}