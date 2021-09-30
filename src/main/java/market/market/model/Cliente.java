package market.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "clienti")
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    private String idCliente;
    private String nome;
    private String cognome;
    private String email;
    private Double budget;
}
