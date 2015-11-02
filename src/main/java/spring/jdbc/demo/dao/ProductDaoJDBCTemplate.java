package spring.jdbc.demo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import spring.jdbc.demo.constant.Constant;
import spring.jdbc.demo.model.Product;

public class ProductDaoJDBCTemplate implements ProductDao {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void insertProduct(final Product product) {

		// SQL Statement
		String query = "INSERT INTO product(product_id, product_name, brand_name) VALUES (?, ?, ?)";

		// Map values with table and execute update
		jdbcTemplate.update(query, new Object[] { product.getProductId(), 
													product.getProductName(), 
													product.getProductBrand() });
	}

	@Override
	public int[] batchInsertProduct(final List<Product> productList) {

		// SQL Statement
		String query = "INSERT INTO product(product_id, product_name, brand_name) VALUES (?, ?, ?)";

		// Following lines map list of values with columns and execute batch
		// update
		int[] updateCounts = jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, productList.get(i).getProductId());
				ps.setString(2, productList.get(i).getProductName());
				ps.setString(3, productList.get(i).getProductBrand());
			}

			public int getBatchSize() {
				return productList.size();
			}
		});

		return updateCounts;
	}

	@Override
	public Product retriveProduct(final int productId) {
		// SQL Statement
		String query = "SELECT product_id, product_name, brand_name FROM product WHERE product_id=?";

		// Implement using RowMapper callback interface
		return (Product) jdbcTemplate.queryForObject(query, new Object[] { Integer.valueOf(productId) },
				new RowMapper() {
					public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
						return new Product(resultSet.getInt(Constant.PRODUCT_ID),
											resultSet.getString(Constant.PRODUCT_NAME), 
											resultSet.getString(Constant.BRAND_NAME));
					}
				});

	}

	@Override
	public void updateProduct(final Product product) {

		// SQL Statement
		String query = "UPDATE product SET product_name=?, brand_name=? WHERE product_id=?";

		// Map values with table and execute update
		jdbcTemplate.update(query,
				new Object[] { product.getProductName(), product.getProductBrand(), product.getProductId() });
	}

	@Override
	public int deleteProduct(final int productId) {

		// SQL Statement
		String query = "DELETE FROM product WHERE product_id=?";

		// Execute update for delete
		jdbcTemplate.update(query, new Object[] { productId });
		return 0;
	}

}
