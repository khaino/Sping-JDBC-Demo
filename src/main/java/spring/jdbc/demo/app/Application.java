package spring.jdbc.demo.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.jdbc.demo.dao.ProductDao;
import spring.jdbc.demo.model.Product;

public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		
		ProductDao simpleJdbc = (ProductDao) context.getBean("simpleJdbc");
		ProductDao jdbcTemplate = (ProductDao) context.getBean("jdbcTemplate");
		
		//For Simple JDBC
		Product product1 = new Product(1, "Simple Insert Product", "Simple JDBC");
		//Insert
		simpleJdbc.insertProduct(product1);
		
		List<Product> productList = new ArrayList<Product>();
		
		Product product2 = new Product(2, "Simple Batch Insert Product001", "Simple JDBC");
		productList.add(product2);
		
		Product product3 = new Product(3, "Simple Batch Insert Product002", "Simple JDBC");;
		productList.add(product3);
		//Batch insert
		simpleJdbc.batchInsertProduct(productList);
		//Retrive
		Product updateProduct = simpleJdbc.retriveProduct(1);
		System.out.println("Before update : " + updateProduct);
		
		updateProduct.setProductName("Simple Insert Product Updated");
		//Update
		simpleJdbc.updateProduct(updateProduct);		
		updateProduct = simpleJdbc.retriveProduct(1);
		System.out.println("Afterupdate update : " + updateProduct);
		//delete
		simpleJdbc.deleteProduct(2);
		
		
		//For Spring JDBC Template
		Product product4 = new Product(4, "JDBC Template  Insert Product", "JDBC Template  JDBC");;
		//Insert
		jdbcTemplate.insertProduct(product4);
		
		List<Product> productList2 = new ArrayList<Product>();
		
		Product product5 = new Product(5, "JDBC Template  Batch Insert Product001", "JDBC Template  JDBC");
		productList2.add(product5);
		
		Product product6 = new Product(6, "JDBC Template  Batch Insert Product002", "JDBC Template  JDBC");
		productList2.add(product6);
		//Batch insert
		jdbcTemplate.batchInsertProduct(productList2);
		//Retrive
		Product updateProduct2 = simpleJdbc.retriveProduct(4);
		System.out.println("Before update : " + updateProduct2);
		
		updateProduct2.setProductName("JDBC Template Insert Product Updated");
		//Update
		simpleJdbc.updateProduct(updateProduct2);		
		updateProduct2 = simpleJdbc.retriveProduct(4);
		System.out.println("Afterupdate update : " + updateProduct2);
		//delete
		simpleJdbc.deleteProduct(5);
		
		context.close();
	}

}
