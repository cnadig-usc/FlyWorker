package models;

import com.avaje.ebean.annotation.CreatedTimestamp;

import javax.persistence.*;
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


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @CreatedTimestamp
    Timestamp created_timestamp;


}
