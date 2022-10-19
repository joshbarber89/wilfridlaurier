package ec.repository;

import javax.ejb.Local;

@Local
public interface RepositoryLocal {
	public String getAllUsers();
}
