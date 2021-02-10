package Driver;

import java.util.List;

import DAO.UserDAO;
import objects.User;

public class Driver {
	public static void main(String[] args) {
//		Session s = HibernateUtil.getSession();
//		Transaction tx = s.beginTransaction();
//		User u = new User("blood", "borne");
//		s.save(u);
//		tx.commit();
		

//		User user = new User("Tim","huehuehue","asdff");
//		try {
//			//UserDAO.append(user);
//			//System.out.println("works");
//			List<User> list = UserDAO.connect();
//			System.out.println(list.toString());
//		}
//		catch(Exception e) {
//			System.out.println(e);
//		}
		
		System.out.println(UserDAO.getInstance().findAll());
		
	}
}
