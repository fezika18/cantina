package br.com.cantina.service;

import br.com.cantina.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Map<String, Object>> listarTodos() {
        return clienteRepository.listarTodos();
    }

    public Map<String, Object> buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "O ID do cliente deve ser maior que zero."
            );
        }

        return clienteRepository.buscarPorId(id);
    }
}