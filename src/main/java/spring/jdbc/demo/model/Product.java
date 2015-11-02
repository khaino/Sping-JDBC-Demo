package spring.jdbc.demo.model;

public class Product {
	
	private int productId;
	private String productName;
	private String productBrand;
	
	public Product(){
		
	}
	
	public Product(int productId, String productName, String productBrand) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productBrand = productBrand;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productBrand=" + productBrand
				+ "]\n";
	}

}
