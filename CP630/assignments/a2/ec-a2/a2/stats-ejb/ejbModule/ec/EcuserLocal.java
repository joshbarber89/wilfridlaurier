package ec;

import javax.ejb.Local;

import ec.euser.Euser;

@Local
public interface EcuserLocal {
    public Boolean validate(String user, String password);
    public Boolean valid();
    public Integer role();
    public Euser user();
}