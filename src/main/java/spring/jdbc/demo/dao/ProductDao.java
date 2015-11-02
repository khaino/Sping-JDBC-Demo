package spring.jdbc.demo.dao;

import java.util.List;

import spring.jdbc.demo.model.Product;

public interface ProductDao {
	
	public void insertProduct(final Product product);
	
	public int[] batchInsertProduct(final List<Product> productList);
	
	public Product retriveProduct(final int productId);
	
	public void updateProduct(final Product product);
	
	public int deleteProduct(final int productId);

}
