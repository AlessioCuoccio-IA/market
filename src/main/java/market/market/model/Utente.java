package market.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;

@Document(collection = "utenti")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utente {
    @Id
    private String id;
    @Indexed
    private String username;
    private String password;

    private Collection<Role> roles = new ArrayList<Role>();

}
