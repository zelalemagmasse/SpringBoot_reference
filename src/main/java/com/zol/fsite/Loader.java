package com.zol.fsite;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.zol.fsite.model.Role;
import com.zol.fsite.repository.RoleRepository;

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
