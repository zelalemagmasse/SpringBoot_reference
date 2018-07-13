package com.zol.fsite.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zol.fsite.model.Role;
import com.zol.fsite.model.User;
import com.zol.fsite.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class SSUDS implements UserDetailsService {

    private UserRepository userRepository;

    public SSUDS(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            User user = userRepository.findUserClassByUserName(username);
            if(user==null)
            {
                throw new UsernameNotFoundException(username+" not found");
            }
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),getAuthorities(user));


        }catch (Exception e)
        {
            throw new UsernameNotFoundException("User not found");

        }

    }

    private Set<GrantedAuthority> getAuthorities(User user)
    {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for(Role role: user.getRoleOfUsers())
        {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
            System.out.println(grantedAuthority.toString());
        }

        return authorities;
    }

}