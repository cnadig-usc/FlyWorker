package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.sql.Timestamp;

/**
 * Created by johntower on 2/11/14.
 */
@Entity
@Table(
        name="account",
        uniqueConstraints = @UniqueConstraint(columnNames={"username"})
)
public class Account {

    @Id
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String name;
    private String username;
    private String password;

    Timestamp created_timestamp;


}
