package app.core.tests;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.services.CompanyService;

@Component
public class TestCompanyService {
	
	public TestCompanyService() {
	}
	
	/**
	 * Adds company coupons to database, using company1
	 * testing the method addCoupon(coupon) of CompanyFacade
	 * @param companyFacade
	 */
	public void initializeCoupons1(CompanyService companyService) {
		try {	
			Coupon coupon1 = createCoupon(Category.Electricity, "Smart TV 40 Inches", "40-inch smart Sonab TV with built-in WiFi, 3 HDMI connections and 2 USB multimedia inputs. Includes one year warranty", LocalDate.parse("2022-02-03"), LocalDate.parse("2024-02-03"), 30, 699);
			companyService.addCoupon(coupon1);
			Coupon coupon2 = createCoupon(Category.Spa, "spa at Kibbutz Gaash", "Hamei Gaash invites you to a fun day with massage, thermal mineral water pools, swimming pool, saunas and more. Also on weekends.", LocalDate.parse("2022-04-05"), LocalDate.parse("2024-04-05"), 35, 77.3);
			companyService.addCoupon(coupon2);
			Coupon coupon3 = createCoupon(Category.Restaurant, "A meal for two at Patrick's", "A meal for two at the Patrix Lake Complex Including an opening, first course, main course for each diner and drink, also on Saturday", LocalDate.parse("2021-01-27"), LocalDate.parse("2021-08-10"), 40, 199);
			companyService.addCoupon(coupon3);
			Coupon coupon4 = createCoupon(Category.Vacation, "vacation for two in Batumi", "vacation for two 3/4 nights in a 5 star hotel in Batumi including Weekend option,Direct flights,Half board and Shuttles", LocalDate.parse("2021-01-27"), LocalDate.parse("2021-06-09"), 5, 230);
			companyService.addCoupon(coupon4);
			Coupon coupon5 = createCoupon(Category.Electricity, "Streamer Android 10", "PRO10 Streamer with wired internet connection and WI-FI including KODI for downloading applications and surfing the Internet on TV, including one year warranty", LocalDate.parse("2021-01-27"), LocalDate.parse("2021-06-09"), 5, 230);
			companyService.addCoupon(coupon5);
			Coupon coupon6 = createCoupon(Category.Vacation, "Vacation at the Nordoy Boutique Hotel", "An opportunity to experience Tel Aviv of yesteryear, and enjoy its beautiful views also including breakfast", LocalDate.parse("2021-01-27"), LocalDate.parse("2021-06-09"), 5, 399);
			companyService.addCoupon(coupon6);
			Coupon coupon7 = createCoupon(Category.Spa, "spa at Kibbutz Gaash", "Hamei Gaash invites you to a fun day with massage, thermal mineral water pools, swimming pool, saunas and more. Also on weekends.", LocalDate.parse("2022-04-05"), LocalDate.parse("2024-04-05"), 35, 77.3);
			companyService.addCoupon(coupon7);
			System.out.println("addCoupon succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Adds company coupons to database, using company2
	 * testing the method addCoupon(coupon) of CompanyFacade
	 * @param companyFacade
	 */
	public void initializeCoupons2(CompanyService companyService) {
		try {
			Coupon coupon1 = createCoupon(Category.Attractions, "Entrance to the Sky Jump trampoline complex", "An hour of jumping in the Sky Jump trampoline park, including climbing walls, a ninja and a selection of other facilities. Also on weekends and holidays", LocalDate.parse("2021-05-03"), LocalDate.parse("2023-02-04"), 10, 33);
			companyService.addCoupon(coupon1);
			Coupon coupon2 = createCoupon(Category.Attractions, "Cruise on the galaxy ship, Eilat", "Overlooking the water: a magical two-hour cruise for the whole family on the glass ship 'Galaxy' in Eilat. Also on Friday", LocalDate.parse("2021-02-05"), LocalDate.parse("2021-11-15"), 123, 34);
			companyService.addCoupon(coupon2);
			Coupon coupon3 = createCoupon(Category.Furniture, "9 compartment hive with 5 storage boxes", "Hive with 9 compartments and 5 colored boxes. Suitable for children's rooms, closets and seating areas. Includes one year warranty", LocalDate.parse("2021-02-27"), LocalDate.parse("2021-07-12"), 22, 132);
			companyService.addCoupon(coupon3);
			Coupon coupon4 = createCoupon(Category.Vacation, "Vacation with breakfast at the Palms Hotel Eilat", "A vacation at the Palms Hotel on Palm Boulevard and close to the shopping complexes, attractions and entertainment centers.Hotel facilities: pool, dry sauna, wet sauna, games room, children's gym", LocalDate.parse("2021-01-27"), LocalDate.parse("2021-08-23"), 1, 670);
			companyService.addCoupon(coupon4);
			Coupon coupon5 = createCoupon(Category.Spa, "Foot massage at Chiang Mai Spa", "It is not possible to take a plane to Thailand now - so we brought it to you in Bograshov.Full foot massage for 30 minutes with customized Thai products", LocalDate.parse("2022-04-05"), LocalDate.parse("2024-04-05"), 35, 77.3);
			companyService.addCoupon(coupon5);
			Coupon coupon6 = createCoupon(Category.Furniture, "Chicago garden furniture", "Garden seating area with aluminum chassis combined with synthetic rattan and cushions with thick and comfortable upholstery. Includes one year warranty.", LocalDate.parse("2022-04-05"), LocalDate.parse("2024-04-05"), 35, 2300);
			companyService.addCoupon(coupon6);
			Coupon coupon7 = createCoupon(Category.Electricity, "Benton air cooler,model BT-7036", "Air cooler with Multi Air Cooler technology that combines ventilation with water cooling. Cooling power 75W with a 4 liter tank and remote control", LocalDate.parse("2022-04-05"), LocalDate.parse("2024-04-05"), 35, 240);
			companyService.addCoupon(coupon7);
			Coupon coupon8 = createCoupon(Category.Vacation, "Your spring break in Dubai", "Direct flights to Dubai,Transportation from the airport and back,3/4 nights in a 4 star hotel,including Breakfast", LocalDate.parse("2021-01-27"), LocalDate.parse("2021-08-23"), 7, 1590);
			companyService.addCoupon(coupon8);
			System.out.println("addCoupon succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Adds company coupons to database, using company3
	 * testing the method addCoupon(coupon) of CompanyFacade
	 * @param companyFacade
	 */
	public void initializeCoupons3(CompanyService companyService) {
		try {
			Coupon coupon1 = createCoupon(Category.Attractions, "The educational zoo in Haifa", "About 100 species of animals (some of them rare) are waiting for you at the educational zoo overlooking the views of the Carmel. Also on weekends", LocalDate.parse("2021-05-03"), LocalDate.parse("2023-02-04"), 10, 23);
			companyService.addCoupon(coupon1);
			Coupon coupon2 = createCoupon(Category.Sport, "A two-week subscription to the ICON fitness club ", "Two-week subscription to one of the ICON FITNESS branches around the country - without obligation, registration fee or cancellation fee", LocalDate.parse("2021-02-05"), LocalDate.parse("2021-11-15"), 123, 24);
			companyService.addCoupon(coupon2);
			Coupon coupon3 = createCoupon(Category.Restaurant, "Stella Beach Double Meal Breakfast", "three starters for the center of the table,main course for each diner,bottle of soft drink for each diner", LocalDate.parse("2021-02-27"), LocalDate.parse("2021-09-12"), 45, 56);
			companyService.addCoupon(coupon3);
			Coupon coupon4 = createCoupon(Category.Sport, "Magnox 520 Dietary Supplement", "120 capsules of the most concentrated magnesium,Original Israeli patent", LocalDate.parse("2021-01-27"), LocalDate.parse("2021-09-23"), 89, 18);
			companyService.addCoupon(coupon4);
			Coupon coupon5 = createCoupon(Category.Sport, "online program for toning and a healthy", "an online program for toning and a healthy diet by Ira Dolphin training, a closed Facebook group and recipes ", LocalDate.parse("2022-04-05"), LocalDate.parse("2024-04-05"), 35, 75.3);
			companyService.addCoupon(coupon5);
			Coupon coupon6 = createCoupon(Category.Restaurant, "Sushi lunch from sushi restaurant", "Sushi meal that includes an opening dish, a first course to choose from, a main course to choose and a drink", LocalDate.parse("2022-04-05"), LocalDate.parse("2024-07-05"), 39, 10);
			companyService.addCoupon(coupon6);
			System.out.println("addCoupon succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Testing methods of CompanyFacade:
	 * getCompanyCoupons(), updateCoupon(coupon), deleteCoupon(couponID),
	 * getCompanyCoupons(category), getCompanyCoupons(maxPrice), 
	 * getCompanyDetails()
	 * @param companyFacade
	 */
	public void start(CompanyService companyService) {
		List<Coupon> coupons = null;
		coupons = companyService.getCompanyCoupons();
		System.out.println("\ncompany coupons: ");
		for(Coupon c: coupons) {
			System.out.println("	" + c.toString());
		}
		if(coupons.size() == 0) {
			System.out.println("	this company don't have coupons");
		}
		System.out.println("getCompanyCoupons succeeded");
		if(coupons != null && coupons.size() > 0) {
			try {
				companyService.updateCoupon(coupons.get(0));
				System.out.println("updateCoupon succeeded");
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("ERROR: coupons array is empty");
		}
		try {
			companyService.deleteCoupon(coupons.get(0).getId());
			System.out.println("deleteCoupon succeeded");
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		List<Coupon> couponsByCategory = null;
		Category category = Category.Restaurant;
		couponsByCategory = companyService.getCompanyCoupons(category);
		System.out.println("\ncompany coupons by category: " + category);
		for(Coupon c: couponsByCategory) {
			System.out.println("	" + c.toString());
		}
		if(couponsByCategory.size() == 0) {
			System.out.println("	this company don't have coupons with category " + category);
		}
		System.out.println("getCompanyCoupons by category succeeded");
		List<Coupon> couponsByMaxPrice = null;
		double maxPrice = 160.4;
		couponsByMaxPrice = companyService.getCompanyCoupons(maxPrice);
		System.out.println("\ncompany coupons by max price: " + maxPrice);
		for(Coupon c: couponsByMaxPrice) {
			System.out.println("	" + c.toString());
		}
		if(couponsByCategory.size() == 0) {
			System.out.println("	this company don't have coupons with price less then " + maxPrice);
		}
		System.out.println("getCompanyCoupons by max price succeeded");
		System.out.println(companyService.getCompanyDetails().toString());
		System.out.println("getCompanyDetails succeeded");
	}
	
	private Coupon createCoupon(Category category, String title, String description, LocalDate startDate, LocalDate endDate, int amount, double price) {
		Coupon coupon = new Coupon();
		coupon.setAmount(amount);
		coupon.setCategory(category);
		coupon.setDescription(description);
		coupon.setEndDate(endDate);
		coupon.setPrice(price);
		coupon.setStartDate(startDate);
		coupon.setTitle(title);
		return coupon;
	}

}
