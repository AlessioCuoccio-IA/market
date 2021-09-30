package market.market.service;

import market.market.model.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> getClienti();

    Cliente getClienteByEmail(String email);

    void deleteCliente(String idCliente);

    void saveCliente(Cliente cliente);

    Cliente getClienteById(String idCliente);
}
