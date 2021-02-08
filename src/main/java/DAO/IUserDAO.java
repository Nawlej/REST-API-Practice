package DAO;

import java.util.List;

import objects.User;

public interface IUserDAO {

	//public User findUser(String user);
	public List<User> findall();
}
