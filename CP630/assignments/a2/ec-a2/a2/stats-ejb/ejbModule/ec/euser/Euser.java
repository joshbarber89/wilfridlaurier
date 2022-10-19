package ec.euser;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="Euser")
@NamedQueries({
    @NamedQuery(name = "Euser.login", query = "SELECT a FROM Euser a WHERE a.name = :name AND a.password = :password"),
    @NamedQuery(name = "Euser.allUsers", query = "SELECT a FROM Euser a")
})
public class Euser {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String password;
    private Integer role;
    public Euser() { }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) {  this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) {  this.password = password; }
    public Integer getRole() { return role; }
    public void setRole(Integer role) { this.role = role; }
    @Override
    public String toString() {
        return "User {" + "id=" + id + ", name='" + name + ", password=" + password + ", role=" + role + '\'' + '}';
    }
}
