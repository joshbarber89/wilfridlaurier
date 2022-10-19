package ec.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="Model")
@NamedQueries({
    @NamedQuery(name = "Model.getLatest", query = "SELECT a FROM Model a ORDER BY date DESC"),
    @NamedQuery(name = "Model.getRegression", query = "SELECT a FROM Model a WHERE name = 'weka-lr' ORDER BY date DESC")
})
public class Model {
    
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String classname;
    private byte[] object;
    private String date;
    public Model() { }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) {  this.name = name; }
    public String getClassname() { return classname; }
    public void setClassname(String classname) {  this.classname = classname; }
    public byte[] getObject() { return this.object; }
    public void setObject(byte[] object) { this.object = object; }
    public String getDate() { return date; }
    public void setDate(String date) {  this.date = date; }
    
    
    @Override
    public String toString() {
        return "Model {" + "id=" + id + ", name='" + name + ", classname=" + classname + ", object=" + object + ", date=" + date + '\'' + '}';
    }
}
