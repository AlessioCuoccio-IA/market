package market.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
@Data
@NoArgsConstructor
public class Role {
    @Id
    private String idRole;

    private String nomeRole;

    public Role(String nomeRole) {
        this.nomeRole = nomeRole;
    }
}