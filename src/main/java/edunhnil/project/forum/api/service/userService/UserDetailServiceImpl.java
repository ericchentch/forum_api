package edunhnil.project.forum.api.service.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edunhnil.project.forum.api.dao.userRepository.CustomUserDetail;
import edunhnil.project.forum.api.dao.userRepository.User;
import edunhnil.project.forum.api.dao.userRepository.UserRepository;
import edunhnil.project.forum.api.exception.ResourceNotFoundException;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Username not found"));
        return new CustomUserDetail(user);
    }

    @Transactional
    public UserDetails loadUserById(int id) {
        User user = userRepository.getUserById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id));

        return new CustomUserDetail(user);
    }
}
