package br.com.fiap.epictaskapi.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.epictaskapi.model.Task;
import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.repository.TaskRepository;
import br.com.fiap.epictaskapi.repository.UserRepository;

@Configuration
@Profile("prod")
// Foi alterado o profile para prod, para que fosse salvo o usuário admin
public class TestConfiguration implements CommandLineRunner {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
        taskRepository.saveAll(List.of(
            new Task("BD", "modelar o banco"), 
            new Task("Prototipo", "prototipar as telas"),
            new Task("Bug", "prototipar as telas"),
            new Task("Nova funcionalidade", "prototipar as telas"),
            new Task("Testes", "prototipar as telas"),
            new Task("Deploy", "prototipar as telas"),
            new Task("Login", "prototipar as telas"),
            new Task("Logout", "prototipar as telas"),
            new Task("Outro bug", "prototipar as telas")
        ));        

        userRepository.save(
            new User(
                "João", 
                "admin@fiap.com.br", 
                passwordEncoder.encode("administrador"))
        );

        userRepository.saveAll(List.of(
            new User(
                "Gabriel", 
                "gabriel@fiap.com.br", 
                passwordEncoder.encode("gabriel")),
            new User(
                "Kleber", 
                "kleber@fiap.com.br", 
                passwordEncoder.encode("kleber"))
        ));
    }

}
