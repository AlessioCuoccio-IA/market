package market.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoceScontrino {
    String nome;
    Integer quantity;
    Double subtot = 0.00;
}
