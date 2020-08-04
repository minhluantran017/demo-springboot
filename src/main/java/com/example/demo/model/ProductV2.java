package com.example.demo.model;

import org.springframework.data.annotation.Id;

public class ProductV2 {
	@Id
    private String _id;
	private String name;
    private String branch;
    private Integer price;
	private String unit;

	public ProductV2(String _id, String name, Integer price, String unit) {
        this._id = _id;
        this.name = name;
		this.branch = branch;
        this.price = price;
        this.unit = unit;
    }

	public String getId() {
		return _id;
	}
	public void setId(String _id) {
		this._id = _id;
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

    public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}

	 @Override
    public String toString() {
        return "{" +
                "\"name\":" + "\"" + name + "\"" +
                ", \"branch\":" + "\"" + branch + "\"" +
                ", \"price\":" + price +
				", \"unit\":" + "\"" + unit + "\"" +
                "}";
    }
}
