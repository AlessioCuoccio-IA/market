package market.market.controller;

import lombok.extern.slf4j.Slf4j;
import market.market.model.Cliente;
import market.market.model.Ricarica;
import market.market.service.ClienteServiceImpl;
import market.market.service.UtenteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/clienti")
public class ClienteController {

    private final ClienteServiceImpl clienteServiceimpl;
    private final UtenteServiceImpl utenteServiceImpl;

    @Autowired
    public ClienteController(ClienteServiceImpl clienteServiceimpl, UtenteServiceImpl utenteServiceImpl) {
        this.clienteServiceimpl = clienteServiceimpl;
        this.utenteServiceImpl = utenteServiceImpl;
    }

    @PostMapping("/cliente")
    public ResponseEntity<Cliente> creaCliente(@RequestBody Cliente cliente) {
        clienteServiceimpl.saveCliente(cliente);
        utenteServiceImpl.addRoleToUtente(cliente.getEmail(), "ROLE_USER");
        return ResponseEntity.ok().body(cliente);
    }

    @PostMapping("/ricarica")
    public ResponseEntity<Cliente> effettuaRicarica(@RequestBody Ricarica ricarica) {
        Cliente cliente = this.clienteServiceimpl.getClienteByEmail(ricarica.getUsername());
        cliente.setBudget(cliente.getBudget() + ricarica.getImporto());
        return ResponseEntity.ok().body(cliente);
    }

    @GetMapping("/clienti")
    public ResponseEntity<List<Cliente>> mostraClienti() {
        return ResponseEntity.ok().body(this.clienteServiceimpl.getClienti());
    }
}
