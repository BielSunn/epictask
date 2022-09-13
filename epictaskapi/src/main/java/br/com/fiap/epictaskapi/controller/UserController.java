package br.com.fiap.epictaskapi.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.epictaskapi.DTO.UserDto;
import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    //@Cacheable("users")
    public Page<Object> index(@PageableDefault(size = 5, sort = "name") Pageable paginacao) {
        var users = service.listAll(paginacao);
        return users.map(user -> {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            return userDto;
        });
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        service.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> show(@PathVariable Long id) {
        //User usua = ResponseEntity.of(service.get(id)).getBody();

        // UserDto usuarioDTO = new UserDto();
        // usuarioDTO.setId(usua.getId());
        // usuarioDTO.setName(usua.getName());
        // usuarioDTO.setEmail(usua.getEmail());
        //Long idDto = usuarioDTO.getId();

        return ResponseEntity.of(service.get(id));
    }

    @DeleteMapping("{id}")
    //@CacheEvict(value = "tasks", allEntries = true)
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        Optional<User> optional = service.get(id);

        if (optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserDto newUser) {
        // carregar a tarefa do banco
        Optional<User> optional = service.get(id);
        
        // verificar se existe a tarefa com esse id
        if (optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        // atualizar os dados
        User user = optional.get();
        BeanUtils.copyProperties(newUser, user);
        user.setId(id);

        // salvar a tarefa
        service.save(user);
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        return ResponseEntity.ok(userDto);
    }
}
