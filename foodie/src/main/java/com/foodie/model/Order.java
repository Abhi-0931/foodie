package com.foodie.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;
	
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//	@JsonIgnore
	private List<OrderItem> items;
	
	

	
	private double subtotal;
    private double deliveryCharge;
    private double discount;
    private double finalTotal;

    private String status = "PLACED";
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private String deliveryAddress;
    
    @PrePersist
    protected void onCreate() {
        if (this.status == null) {
            this.status = "PLACED";
        }
        this.createdAt = LocalDateTime.now();
    }
    
	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Order(Long id, User user, List<OrderItem> items, double subtotal, double deliveryCharge, double discount,
			double finalTotal, String status, LocalDateTime createdAt, String deliveryAddress) {
		super();
		this.id = id;
		this.user = user;
		this.items = items;
		this.subtotal = subtotal;
		this.deliveryCharge = deliveryCharge;
		this.discount = discount;
		this.finalTotal = finalTotal;
		this.status = status;
		this.createdAt = createdAt;
		this.deliveryAddress = deliveryAddress;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public double getDeliveryCharge() {
		return deliveryCharge;
	}
	public void setDeliveryCharge(double deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getFinalTotal() {
		return finalTotal;
	}
	public void setFinalTotal(double finalTotal) {
		this.finalTotal = finalTotal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public Order() {}

	
	
//	public Order(Long id, User user, List<CartItem> items, double subtotal, double deliveryCharge, double discount,
//			double finalTotal, String status, LocalDateTime createdAt) {
//		super();
//		this.id = id;
//		this.user = user;
//		this.items = items;
//		this.subtotal = subtotal;
//		this.deliveryCharge = deliveryCharge;
//		this.discount = discount;
//		this.finalTotal = finalTotal;
//		this.status = status;
//		this.createdAt = createdAt;
//	}
    
    
	


}
