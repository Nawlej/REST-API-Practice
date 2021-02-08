package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Transaction;

import util.HibernateUtil;
import objects.User;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.Session;

public class UserDAO implements IUserDAO{
	//currently needs to connect to an RDS to interact with DBeaver, running low on free tier hours for AWS RDS :(
	//so instead I will have it communicate with an Excel file as practice.

	private static HashMap<String, User> userMap = new HashMap<String, User>();
	private static File filepath = new File("D:\\Git\\demos\\test\\Book1.xlsx");
	
	private static UserDAO instance = new UserDAO();
	public static UserDAO getInstance() {
		return instance;
	}
	
	private UserDAO() {
		userMap.put("Jason", new User("Jason","Borne", "Cop"));
		userMap.put("Hot", new User("Hot","Wheels", "Very hot"));
		
		try {
			List<User> list = connect();
			Iterator<User> ite = list.iterator();
			while (ite.hasNext()) {
				userMap.put(ite.next().getName(), ite.next());
			}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static List<User> connect() throws IOException{
		List<User> list = new ArrayList<User>();
		
		Workbook wb = WorkbookFactory.create(filepath);
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> ite = sheet.iterator();
		
		while (ite.hasNext()) {
			Row nextRow = ite.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			User user = new User();
			
			while (cellIterator.hasNext()) {
				Cell nextCell = cellIterator.next();
				int columnIndex = nextCell.getColumnIndex();
				
				switch (columnIndex) {
				case 0:
					user.setName((String) getCellValue(nextCell));
					break;
				case 1:
					user.setUsername((String) getCellValue(nextCell));
					break;
				case 2:
					user.setPassword((String) getCellValue(nextCell));
					break;
				}
			}
			list.add(user);
		}
		wb.close();
		
		return list;
	}

	private static Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
			
		case NUMERIC:
			return cell.getNumericCellValue();
		default:
			break;
		}
		
		return null;
	}
	
	public static void append(User user) throws IOException{
		FileInputStream input = new FileInputStream(filepath);
		Workbook wb = WorkbookFactory.create(input);
		Sheet sheet = wb.getSheetAt(0);
		
		int rowCount = sheet.getLastRowNum();
		int colCount = 0;
		
		Row row = sheet.createRow(++rowCount);
		Cell cell = row.createCell(colCount);
		
		String[] field = {user.getName(), user.getUsername(), user.getPassword()};
		
		for (int i=0;i<field.length;i++) {
			if (field[i] instanceof String) {
				cell.setCellValue(field[i]);
			}
			cell = row.createCell(++colCount);
		}

		input.close();
		
		FileOutputStream output = new FileOutputStream(filepath);
		wb.write(output);
		wb.close();
		output.close();
	}
	
	public User findUser(String username) {
		
		return userMap.get(username );	
	}

	public User newUser(String name, String username, String pass) {
		User user = new User(name, username, pass);
		
		return user;
	}
	
	public List<User> findall() {
		Session s = HibernateUtil.getSession();
		Transaction t = s.beginTransaction();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<User> query = cb.createQuery(User.class);
		Root<User> root = query.from(User.class);
		
		query.select(root);
		List<User> users = s.createQuery(query).getResultList();
		t.commit();
		
		return users;
	}

}
