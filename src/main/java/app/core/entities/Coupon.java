package app.core.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	private int amount;
	@Transient
	private int idC;
	private Double price;
	@Transient
	private String[] imagesNames;
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name = "customers_vs_coupons", joinColumns = @JoinColumn(name = "coupon_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private List<Customer> customers;
	
	public enum Category {
		Spa, Electricity, Restaurant, Vacation, Sport, Furniture, Attractions;
	}
	
	public Coupon() {
	}
	
	public Coupon(Category category, String title, String description, LocalDate startDate, LocalDate endDate,
			int amount, Double price) {
		super();
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
	}

	public void addCustomer(Customer customer) {
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
		this.idC = company.getId();
	}
	
	public void setIdC(int companyId) {
		this.idC = companyId;
	}
	
	public int getIdC() {
		return idC;
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
	
	public String[] getImagesNames() {
		return this.imagesNames;
	}

	public void addImagesNames(String[] imagesNames) {
		this.imagesNames = Stream.concat(Arrays.stream(this.imagesNames), Arrays.stream(imagesNames))
                .toArray(String[]::new);
	}
	
	public void setImagesNames(String[] imagesNames) {
		if(imagesNames != null && imagesNames.length > 0) {
			this.imagesNames = Arrays.copyOf(imagesNames, imagesNames.length);
		}
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", category=" + category + ", title=" + title + ", description=" + description
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", amount=" + amount + ", price=" + price
				+ "]";
	}

}
