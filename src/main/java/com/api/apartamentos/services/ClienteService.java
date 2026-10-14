package com.api.apartamentos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.apartamentos.repositories.IClienteRepository;
import com.api.apartamentos.models.ClienteModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class ClienteService extends BaseCrudService<ClienteModel> {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ClienteService(IClienteRepository clienteRepository) {
        super(clienteRepository);
    }

    public List<ClienteModel> getAllClientes() {
        return findAll();
    }

    public Optional<ClienteModel>  getClienteById(Long id) {
        return findById(id);
    }

    public ClienteModel saveCliente(ClienteModel cliente) {
        String password = cliente.getPassword();
        ClienteModel anterior = cliente.getId() == null ? null : repository.findById(cliente.getId()).orElse(null);
        boolean conservada = anterior != null && password != null && password.equals(anterior.getPassword());
        if (!conservada) {
            if (password == null || password.isBlank()) throw new IllegalArgumentException("La contraseña es obligatoria");
            cliente.setPassword(passwordEncoder.encode(password));
        }
        return save(cliente);
    }

    public void deleteCliente(Long id) {
        deleteById(id);
    }

    
}
