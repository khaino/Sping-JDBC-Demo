package spring.jdbc.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import spring.jdbc.demo.constant.Constant;
import spring.jdbc.demo.model.Product;

public class ProductDaoSimpleJDBC implements ProductDao {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insertProduct(final Product product) {

		// SQL Statement
		String query = "INSERT INTO product(product_id, product_name, brand_name) VALUES (?, ?, ?)";

		// Define the connection and preparedStatement parameters
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {

			// Open the connection
			connection = dataSource.getConnection();

			// Prepare the statement
			preparedStatement = connection.prepareStatement(query);

			// Bind the parameters to the PreparedStatement
			preparedStatement.setInt(1, product.getProductId());
			preparedStatement.setString(2, product.getProductName());
			preparedStatement.setString(3, product.getProductBrand());

			// Execute the statement
			preparedStatement.execute();
		} catch (SQLException e) {

			// Handle any exception
			e.printStackTrace();
		} finally {
			try {

				// Close the preparedStatement
				if (preparedStatement != null) {
					preparedStatement.close();
				}

				// Close the connection
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

				// Handle any exception
				e.printStackTrace();
			}
		}

	}

	@Override
	public int[] batchInsertProduct(final List<Product> productList) {
		// SQL Statement
		String query = "INSERT INTO product(product_id, product_name, brand_name) VALUES (?, ?, ?)";

		// Define the connection and preparedStatement parameters
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		// initialize update count to null
		int[] updateCount = null;
		try {

			// Open the connection
			connection = dataSource.getConnection();

			// Set auto commit to false so that it can be rolled back if error
			// occurs
			connection.setAutoCommit(false);

			// Prepare the statement
			preparedStatement = connection.prepareStatement(query);

			for (Product product : productList) {

				// Bind the parameters to the PreparedStatement
				preparedStatement.setInt(1, product.getProductId());
				preparedStatement.setString(2, product.getProductName());
				preparedStatement.setString(3, product.getProductBrand());
				// Add current product to batch
				preparedStatement.addBatch();
			}

			// Execute the statement
			updateCount = preparedStatement.executeBatch();

			System.out.println("Simple Batch Update : " + updateCount.length);

			// Commit if batch execution successes
			connection.commit();

		} catch (SQLException e) {

			try {
				// Rollback if batch insertion fails
				connection.rollback();

			} catch (SQLException e1) {

				// Handle any exception
				e1.printStackTrace();
			}

			// Handle any exception
			e.printStackTrace();
		} finally {
			try {

				// Close the preparedStatement
				if (preparedStatement != null) {
					preparedStatement.close();
				}

				// Close the connection
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

				// Handle any exception
				e.printStackTrace();
			}
		}

		return updateCount;
	}

	@Override
	public Product retriveProduct(final int productId) {
		// SQL Statement
		String query = "SELECT product_id, product_name, brand_name FROM product WHERE product_id=?";

		// Define the connection, preparedStatement and resultSet parameters
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			// Open the connection
			connection = dataSource.getConnection();

			// Prepare the statement
			preparedStatement = connection.prepareStatement(query);

			// Bind the parameters to the PreparedStatement
			preparedStatement.setInt(1, productId);

			// Execute statement
			resultSet = preparedStatement.executeQuery();

			Product product = null;

			// Extract data from the result set
			if (resultSet.next()) {
				product = new Product(resultSet.getInt(Constant.PRODUCT_ID), 
										resultSet.getString(Constant.PRODUCT_NAME),
										resultSet.getString(Constant.BRAND_NAME));
			}
			return product;
		} catch (SQLException e) {

			// Handle any exception
			e.printStackTrace();
		} finally {
			try {

				// Close the preparedStatement
				if (preparedStatement != null) {
					preparedStatement.close();
				}

				// Close the connection
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

				// Handle any exception
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void updateProduct(final Product product) {
		// SQL Statement
		String query = "UPDATE product SET product_name=?, brand_name=? WHERE product_id=?";

		// Define the connection and preparedStatement parameters
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {

			// Open the connection
			connection = dataSource.getConnection();

			// Prepare the statement
			preparedStatement = connection.prepareStatement(query);

			// Bind the parameters to the PreparedStatement
			preparedStatement.setString(1, product.getProductName());
			preparedStatement.setString(2, product.getProductBrand());
			preparedStatement.setInt(3, product.getProductId());

			// Execute the statement
			preparedStatement.executeUpdate();
		} catch (SQLException e) {

			// Handle any exception
			e.printStackTrace();
		} finally {
			try {

				// Close the preparedStatement
				if (preparedStatement != null) {
					preparedStatement.close();
				}

				// Close the connection
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

				// Handle any exception
				e.printStackTrace();
			}
		}
	}

	@Override
	public int deleteProduct(final int productId) {

		// SQL Statement
		String query = "DELETE FROM product WHERE product_id=?";

		// Define the connection and preparedStatement parameters
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int row = 0;
		try {

			// Open the connection
			connection = dataSource.getConnection();

			// Prepare the statement
			preparedStatement = connection.prepareStatement(query);

			// Bind the parameters to the PreparedStatement
			preparedStatement.setInt(1, productId);
			
			// Execute the statement
			row = preparedStatement.executeUpdate();
		} catch (SQLException e) {

			// Handle any exception
			e.printStackTrace();
		} finally {
			try {

				// Close the preparedStatement
				if (preparedStatement != null) {
					preparedStatement.close();
				}

				// Close the connection
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {

				// Handle any exception
				e.printStackTrace();
			}
		}
		
		return row;
	}

}
