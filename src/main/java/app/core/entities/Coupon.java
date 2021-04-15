package app.core.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "coupons")
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Company company;
	@Enumerated(EnumType.STRING)
	private Category category;
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private int amount;
	private Double price;
	private String image;
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name = "customers_vs_coupons", joinColumns = @JoinColumn(name = "coupon_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private List<Customer> customers;
	
	public enum Category {
		Food, Electricity, Restaurant, Vacation, Sport, Furnitures, Clothing, Attractions;
	}
	
	public Coupon() {
	}
	
	public Coupon(Category category, String title, String description, LocalDate startDate, LocalDate endDate,
			int amount, Double price, String image) {
		super();
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public void addCustomer(Customer customer) {// TODO: check if needed
		if (this.customers == null) {
			this.customers = new ArrayList<Customer>();
		}
		this.customers.add(customer);
	}
	
	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		if(category != null) {
			this.category = category;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if(title != null && title != "") {
			this.title = title;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if(description != null && description != "") {
			this.description = description;
		}
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		if(startDate != null) {
			this.startDate = startDate;
		}
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		if(endDate != null) {
			this.endDate = endDate;
		}
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		if(amount > 0) {
			this.amount = amount;
		}
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		if(price > 0) {
			this.price = price;
		}
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		if(image != null && image != "") {
			this.image = image;
		}
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", category=" + category + ", title=" + title + ", description=" + description
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", amount=" + amount + ", price=" + price
				+ ", image=" + image + "]";
	}

}
