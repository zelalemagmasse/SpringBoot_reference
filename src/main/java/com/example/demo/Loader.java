package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Loader  implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public  void run(String ... Strings)throws Exception{
        Role r = new Role();
        r.setRole("USER");
        roleRepository.save(r);

    }
}
