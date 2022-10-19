package ec.repository;

import javax.ejb.Remote;

@Remote
public interface RepositoryRemote {
	public String getAllUsers();
}
