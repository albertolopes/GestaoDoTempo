package com.allos.pomodoro.service;

import com.allos.pomodoro.dto.TarefasDTO;
import com.allos.pomodoro.entity.enums.Perfil;
import com.allos.pomodoro.exception.AuthorizationException;
import com.allos.pomodoro.exception.ObjectNotFoundException;
import com.allos.pomodoro.mapper.TarefasMapper;
import com.allos.pomodoro.repository.TarefasRepository;
import com.allos.pomodoro.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TarefasService {

    @Autowired
    private TarefasRepository repository;

    @Autowired
    private TarefasMapper mapper;

    public TarefasDTO salvar(final TarefasDTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public Optional<TarefasDTO> buscarTarefa(Long id) {

        UserSecurity userSecurity = UserSecurityService.authenticate();

        if(userSecurity == null || !userSecurity.hasRole(Perfil.ADMIN) && !id.equals(userSecurity.getId())){
            throw new AuthorizationException("Acesso negado");
        }

        if(!repository.findById(id).isPresent()){
            throw new ObjectNotFoundException("Tarefa não existe.");
        }

        return Optional.of(mapper.toDto(repository.findById(id).get()));
    }

    public TarefasDTO atualizarTarefa(TarefasDTO dto){

        UserSecurity userSecurity = UserSecurityService.authenticate();

        if(userSecurity == null || !userSecurity.hasRole(Perfil.ADMIN) && dto.getId().equals(userSecurity.getId())){
            throw new AuthorizationException("Acesso negado");
        }

        if(!repository.findById(dto.getId()).isPresent()){
            throw new ObjectNotFoundException("Tarefa não existe.");
        }

        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public void deletaTarefa(Long id) {

        UserSecurity userSecurity = UserSecurityService.authenticate();

        if(userSecurity == null || !userSecurity.hasRole(Perfil.ADMIN) && !id.equals(userSecurity.getId())){
            throw new AuthorizationException("Acesso negado");
        }

        if(!repository.findById(id).isPresent()){
            throw new ObjectNotFoundException("Usuario não existe.");
        }
        repository.deleteById(id);
    }

}
