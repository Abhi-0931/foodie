package com.foodie.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="carts")
public class Cart {
	
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	 @ManyToOne
	 @JoinColumn(name = "user_id")
	    private User user;

	 @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	 private List<CartItem> items = new ArrayList<>();


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

		public List<CartItem> getItems() {
			return items;
		}

		public void setItems(List<CartItem> items) {
			this.items = items;
		}
		
		public Cart() {}
		
		   public Cart(User user) {
		        this.user = user;
		    }
		public Cart(Long id, User user, List<CartItem> items) {
			super();
			this.id = id;
			this.user = user;
			this.items = items;
		}

		
	
	
	

}
