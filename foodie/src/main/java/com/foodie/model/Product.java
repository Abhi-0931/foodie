package com.foodie.model;

import java.util.Base64;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="products")
public class Product {
	
	  @Id 
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  private String name;    
	  private double price; 
	  
	  @Lob
	  @Basic(fetch = FetchType.LAZY)
	  @Column(columnDefinition = "LONGBLOB")
	  private byte[] img;
	  
	  public String getImgBase64() {
	        if (img != null) {
	            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(img);
	        }
	        return null;
	    }

	  
	  
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	
	
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	
	public Product() {}
	
	public Product(Long id, String name, double price, byte[] img) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.img = img;
	}
	  
	  

}
