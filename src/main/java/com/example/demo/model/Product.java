package com.example.demo.model;

public class Product {
	private String id;
	private String name;
    private String branch;
    private String price;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}

    public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
