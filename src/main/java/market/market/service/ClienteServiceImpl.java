package market.market.service;

import market.market.model.Cliente;
import market.market.repository.ClienteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepo clienteRepo;

    @Autowired
    public ClienteServiceImpl(ClienteRepo clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    @Override
    public List<Cliente> getClienti() {
        return clienteRepo.findAll();
    }

    @Override
    public Cliente getClienteByEmail(String email) {
        return clienteRepo.findClienteByEmail(email);
    }

    @Override
    public void deleteCliente(String idCliente) {
        clienteRepo.deleteById(idCliente);

    }

    @Override
    public void saveCliente(Cliente cliente) {
        clienteRepo.save(cliente);

    }

    @Override
    public Cliente getClienteById(String idCliente) {
        return this.clienteRepo.findById(idCliente).get();
    }
}
