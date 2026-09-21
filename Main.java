

import java.util.*;

public class Main {

	static Scanner scanner = new Scanner(System.in);

	static systemDataBase ourSystem = new systemDataBase();

	public static void main(String[] args) {
		initializeData();
		mainMenu();
		return;
	}

	public static void mainMenu() {

		while (true) {
			System.out.println("\n--- Main Menu ---");
			System.out.println("1. Login as Main Manager");
			System.out.println("2. Login as Manager");
			System.out.println("3. Login as Subscriber");
			System.out.println("0. Exit");
			System.out.print("Choose: ");
			String ch = scanner.nextLine();

			if (ch.equals("1")) {
				loginMainManager();
			} else if (ch.equals("2")) {
				loginManager();
			} else if (ch.equals("3")) {
				loginSubscriber();
			} else if (ch.equals("0")) {
				System.out.println("Goodbye!");
				break;
			} else {
				System.out.println("Invalid option.");
			}
		}
		return;
	}

	public static void initializeData() {
		ourSystem.addManager(
				new MainManager("9001", "Maria", "Fahoum", "0500000000", "Central Perk", "system", "12345"));
		ourSystem.addManager(new Manager("M1", "Mike", "Hannigan", "0500000001", "NYC"));
		ourSystem.addManager(new Manager("M2", "Janice", "Hosenstein", "0500000002", "Brooklyn"));
		ourSystem.addManager(new Manager("M3", "Estelle", "Leonard", "0500000003", "Queens"));
		ourSystem.addManager(new Manager("M4", "Jack", "Geller", "0500000004", "Manhattan"));

		ourSystem.addSubscription(new Subscription("S1", "Rachel", "Green", "Soho", "0501111111"));
		ourSystem.addSubscription(new Subscription("S2", "Monica", "Geller", "West Village", "0501111112"));
		ourSystem.addSubscription(new Subscription("S3", "Phoebe", "Buffay", "Upper East", "0501111113"));
		ourSystem.addSubscription(new Subscription("S4", "Joey", "Tribbiani", "Queens", "0501111114"));
		ourSystem.addSubscription(new Subscription("S5", "Ross", "Geller", "Museum District", "0501111115"));
		ourSystem.addSubscription(new Subscription("S6", "Chandler", "Bing", "Greenwich", "0501111116"));
		ourSystem.addSubscription(new Subscription("S7", "Emily", "Waltham", "London", "0501111117"));
		ourSystem.addSubscription(new Subscription("S8", "Carol", "Willick", "Brooklyn", "0501111118"));
		ourSystem.addSubscription(new Subscription("S9", "Susan", "Bunch", "Brooklyn", "0501111119"));
		ourSystem.addSubscription(new Subscription("S10", "Ben", "Geller", "NYC", "0501111120"));
		
		Taxi t1 = new Taxi("T1", true, 35);
		Taxi t2 = new Taxi("T2", true, 40);
		Taxi t3 = new ExpressTaxi("T3", true, 45, true, 10);
		Taxi t4 = new ExpressTaxi("T4", true, 50, true, 12);
		Taxi t5 = new IntercityTaxi("T5", true, 60, 
									new ArrayList<String>(Arrays.asList("Tel Aviv", "Haifa")), 15, 3);
		Taxi t6 = new IntercityTaxi("T6", true, 70,
									new ArrayList<String>(Arrays.asList("Jerusalem", "Eilat")), 20, 5);
		Taxi t7 = new Taxi("T7", true, 30);
		Taxi t8 = new ExpressTaxi("T8", true, 55, true, 13);
		Taxi t9 = new Taxi("T9", true, 32);
		Taxi t10 = new Taxi("T10", true, 38);
		ourSystem.addTaxi(t1);
		ourSystem.addTaxi(t2);
		ourSystem.addTaxi(t3);
		ourSystem.addTaxi(t4);
		ourSystem.addTaxi(t5);
		ourSystem.addTaxi(t6);
		ourSystem.addTaxi(t7);
		ourSystem.addTaxi(t8);
		ourSystem.addTaxi(t9);
		ourSystem.addTaxi(t10);
		
		ourSystem.addStation(new Station("Haifa", new ArrayList<Taxi>(Arrays.asList(t1, t2, t3))));
		ourSystem.addStation(new Station("Motzkin", new ArrayList<Taxi>(Arrays.asList(t4, t5, t6))));
		ourSystem.addStation(new Station("Bialik", new ArrayList<Taxi>(Arrays.asList(t8, t9))));
		
		ourSystem.addOrder(new Order("O1", "M1", 22, 11, 11, "S1", t1, t1.getMinPrice()));
		ourSystem.addOrder(new Order("O2", "M1", 30, 5, 10, "S1", t2, t2.getMinPrice()));
		ourSystem.addOrder(new Order("O3", "M2", 12, 11, 3, "S2", t3, t3.getMinPrice()));
		ourSystem.addOrder(new Order("O4", "M2", 11, 8, 9, "S2", t4, t4.getMinPrice()));
		ourSystem.addOrder(new Order("O5", "M3", 6, 12, 22, "S3", t5, t5.getMinPrice()));
	}

	public static int getValidDay() {
		int day;
		do {
			System.out.print("Enter day (1-31): ");
			while (!scanner.hasNextInt()) {
				System.out.print("Invalid input. Enter day (1-31): ");
				scanner.next();
			}
			day = scanner.nextInt();
		} while (day < 1 || day > 31);
		return day;
	}

	public static int getValidMonth() {
		int month;
		do {
			System.out.print("Enter month (1-12): ");
			while (!scanner.hasNextInt()) {
				System.out.print("Invalid input. Enter month (1-12): ");
				scanner.next();
			}
			month = scanner.nextInt();
		} while (month < 1 || month > 12);
		return month;
	}

	public static int getValidHour() {
		int hour;
		do {
			System.out.print("Enter hour (0-23): ");
			while (!scanner.hasNextInt()) {
				System.out.print("Invalid input. Enter hour (0-23): ");
				scanner.next();
			}
			hour = scanner.nextInt();
		} while (hour < 0 || hour > 23);
		return hour;
	}

	public static double getValidPrice() {
		double price;
		do {
			System.out.print("Enter price (>0): ");
			while (!scanner.hasNextDouble()) {
				System.out.print("Invalid input. Enter price (>0): ");
				scanner.next();
			}
			price = scanner.nextDouble();
		} while (price <= 0);
		return price;
	}

	public static int getPositiveNumber() {
		int number;
		do {
			System.out.print("Enter a positive number (>0): ");
			while (!scanner.hasNextInt()) {
				System.out.print("Invalid input. Enter a positive number: ");
				scanner.next();
			}
			number = scanner.nextInt();
		} while (number <= 0);
		return number;
	}

	public static void loginMainManager() {
		System.out.print("Username: ");
		String user = scanner.nextLine();
		System.out.print("Password: ");
		String pass = scanner.nextLine();
		for (Manager m : ourSystem.getManagers()) {
			if (m instanceof MainManager) {
				MainManager mm = (MainManager) m;
				if (mm.getUserName().equals(user) && mm.getPassword().equals(pass)) {
					System.out.println("Welcome Main Manager " + mm.getFirstName());
					mainManagerMenu(mm);
				}
			}
		}
		System.out.println("Login failed.");
	}

	// updated
	public static void mainManagerMenu(MainManager mm) {
		while (true) {
			System.out.println("\n--- Main Manager Menu ---");
			System.out.println("1. Add Subscriber");
			System.out.println("2. Add Manager");
			System.out.println("3. Add Taxi");
			System.out.println("4. Assign Taxi to Manager");
			System.out.println("5. Add Order");
			System.out.println("6. Add Station");
			System.out.println("7. Get Free Taxis from Station");
			System.out.println("8. Get Express Taxis from Subscriber");
			System.out.println("9. Remove Functions");
			System.out.println("0. Back to Main Menu");
			System.out.print("Choose: ");
			String choice = scanner.nextLine();
			if (choice.equals("1")) {
				addSubscriber();
			} else if (choice.equals("2")) {
				addManager();
			} else if (choice.equals("3")) {
				addTaxi();
			} else if (choice.equals("4")) {
				assignTaxiToManager();
			} else if (choice.equals("5")) {
				addOrder(mm);
			} else if (choice.equals("6")) {
				addStation();
			} else if (choice.equals("7")) {
				getFreeTaxis();
			} else if (choice.equals("8")) {
				getExpressTaxis();
			} else if (choice.equals("9")) {
				removeMiniMenu();
			} else if (choice.equals("0")) {
				return;
			} else {
				System.out.println("Invalid option.");
			}

		}
	}

	

	

	/**
	 * new function
	 */
	public static void removeMiniMenu() {
		System.out.println("\n--- Additional remove methods ---");
		System.out.println("1. Remove Subscriber");
		System.out.println("2. Remove Manager");
		System.out.println("3. Remove Taxi");
		System.out.println("4. Remove Station");
		System.out.println("5. Remove Order");
		System.out.println("0. Back to Main Manager Menu");
		System.out.print("Choose: ");
		String choice = scanner.nextLine();
		if (choice.equals("1")) {
			removeSubscriber();
		} else if (choice.equals("2")) {
			removeManager();
		} else if (choice.equals("3")) {
			removeTaxi();
		} else if (choice.equals("4")) {
			removeStation();
		} else if (choice.equals("5")) {
			removeOrder();
		} else if (choice.equals("0")) {
			return;
		} else {
			System.out.println("Invalid option.");
		}
	}

	/**
	 * new function
	 */
	public static void removeSubscriber() {
		if (ourSystem.getSubscriptions().isEmpty()) {
			System.out.println("There are no subscribers to remove");
			return;
		}
		System.out.println("Choose one code from the following subscribers to remove: ");
		for (Subscription sub : ourSystem.getSubscriptions()) {
			System.out.print(
					"code:" + sub.getSubCode() + " name:" + sub.getFirstName() + " " + sub.getLastName() + " || ");
		}
		String chosenSubCode = scanner.nextLine();
		Subscription chosenSub = null;
		for (Subscription sub : ourSystem.getSubscriptions()) {
			if (sub.getSubCode().equals(chosenSubCode)) {
				chosenSub = sub;
			}
		}
		if (ourSystem.removeSubscription(chosenSub)) {
			System.out.println("The subscriber " + chosenSubCode + " was removed");
			return;
		}
		System.out.println("Removal failed, the subscriber code " + chosenSubCode + " does not exist");
		return;
	}

	/**
	 * new function
	 */
	public static void removeManager() {
		if (ourSystem.getManagers().isEmpty()) {
			System.out.println("There are no managers to remove");
			return;
		}
		System.out.println("Choose one id from the following managers to remove: ");
		for (Manager manager : ourSystem.getManagers()) {
			System.out.print(
					"ID:" + manager.getId() + " name:" + manager.getFirstName() + " " + manager.getLastName() + " || ");
		}
		String chosenId = scanner.nextLine();
		Manager chosenManager = null;
		for (Manager manager : ourSystem.getManagers()) {
			if (manager.getId().equals(chosenId) && !(manager instanceof MainManager)) {
				chosenManager = manager;
			}
		}
		if (ourSystem.removeManager(chosenManager)) {
			System.out.println("The manager " + chosenId + " was removed");
			return;
		}
		System.out.println("Removal failed, the manager ID " + chosenId + " does not exist or is a Main Manager");
		return;
	}

	/**
	 * new function
	 */
	public static void removeTaxi() {
		if (ourSystem.getTaxis().isEmpty()) {
			System.out.println("There are no taxis to remove");
			return;
		}
		System.out.println("Choose one code from the following taxis to remove: ");
		for (Taxi taxi : ourSystem.getTaxis()) {
			System.out.print(taxi.getTaxiCode() + " || ");
		}
		String chosenTaxiCode = scanner.nextLine();
		Taxi chosenTaxi = null;
		for (Taxi taxi : ourSystem.getTaxis()) {
			if (taxi.getTaxiCode().equals(chosenTaxiCode)) {
				chosenTaxi = taxi;
			}
		}
		if (ourSystem.removeTaxi(chosenTaxi)) {
			System.out.println("The taxi " + chosenTaxiCode + " was removed");
			return;
		}
		System.out.println("Removal failed, the taxi code " + chosenTaxiCode + " does not exist");
		return;
	}

	/**
	 * new function
	 */
	public static void removeStation() {
		if (ourSystem.getStations().isEmpty()) {
			System.out.println("There are no stations to remove");
			return;
		}
		System.out.println("Choose one station from the following to remove: ");
		for (Station station : ourSystem.getStations()) {
			System.out.print(station.getStationName() + " || ");
		}
		String chosenStationName = scanner.nextLine();
		Station chosenStation = null;
		for (Station station : ourSystem.getStations()) {
			if (station.getStationName().equals(chosenStationName)) {
				chosenStation = station;
			}
		}
		if (ourSystem.removeStation(chosenStation)) {
			System.out.println("The station " + chosenStationName + " was removed");
			return;
		}
		System.out.println("Removal failed, the station code " + chosenStationName + " does not exist");
		return;
	}

	/**
	 * new function
	 */
	public static void removeOrder() {
		if (ourSystem.getOrders().isEmpty()) {
			System.out.println("There are no orders to remove");
			return;
		}
		System.out.println("Choose one order from the following to remove: ");
		for (Order order : ourSystem.getOrders()) {
			System.out.print(order.getOrderNum() + " || ");
		}
		String chosenOrderNum = scanner.nextLine();
		Order chosenOrder = null;
		for (Order order : ourSystem.getOrders()) {
			if (order.getOrderNum().equals(chosenOrderNum)) {
				chosenOrder = order;
			}
		}
		if (ourSystem.removeOrder(chosenOrder)) {
			System.out.println("The order " + chosenOrderNum + " was removed");
			return;
		}
		System.out.println("Removal failed, the order code " + chosenOrderNum + " does not exist");
		return;
	}

	public static void addSubscriber() {
		System.out.print("ID: ");
		String id = scanner.nextLine();
		System.out.print("First name: ");
		String fn = scanner.nextLine();
		System.out.print("Last name: ");
		String ln = scanner.nextLine();
		System.out.print("Phone: ");
		String phone = scanner.nextLine();
		System.out.print("Address: ");
		String addr = scanner.nextLine();
		boolean added = ourSystem.addSubscription(new Subscription(id, fn, ln, phone, addr));
		if (added) {
			System.out.println("Subscriber added.");
		} else
			System.out.println("Failed to add subscriber .");
		return;
	}

	public static void addManager() {
		System.out.println("Type: 1. Regular  2. Main");
		int type = scanner.nextInt();
		scanner.nextLine();
		System.out.print("ID: ");
		String id = scanner.nextLine();
		System.out.print("First name: ");
		String fn = scanner.nextLine();
		System.out.print("Last name: ");
		String ln = scanner.nextLine();
		System.out.print("Phone: ");
		String phone = scanner.nextLine();
		System.out.print("Address: ");
		String addr = scanner.nextLine();
		boolean added;
		if (type == 1) {
			added = ourSystem.addManager(new Manager(id, fn, ln, phone, addr));
		} else {
			System.out.print("Username: ");
			String un = scanner.nextLine();
			System.out.print("Password: ");
			String pw = scanner.nextLine();
			added = ourSystem.addManager(new MainManager(id, fn, ln, phone, addr, un, pw));

		}
		if (added) {
			System.out.println("Manager added.");
		}

		else
			System.out.println("Failed to add manager!");
		return;
	}

	public static void addTaxi() {
		System.out.println("Type: 1. Regular 2. Express 3. InterCity");
		String type = scanner.nextLine();
		System.out.print("Code: ");
		String code = scanner.nextLine();
		System.out.print("Available (true/false): ");
		boolean avail = scanner.nextBoolean();
		System.out.print("Min price: ");
		double price = getValidPrice();
		scanner.nextLine();

		boolean added = false;

		if (type.equals("1")) {
			added = ourSystem.addTaxi(new Taxi(code, avail, price));
		} else if (type.equals("2")) {
			System.out.print("City Taxi (true/false): ");
			boolean city = scanner.nextBoolean();
			System.out.print("Extra Price: ");
			double extra = getValidPrice();
			scanner.nextLine();
			added = ourSystem.addTaxi(new ExpressTaxi(code, avail, price, city, extra));
		} else if (type.equals("3")) {
			System.out.print("Number of cities: ");
			int n = scanner.nextInt();
			scanner.nextLine();
			ArrayList<String> cities = new ArrayList<>();
			for (int j = 0; j < n; j++) {
				System.out.print("City " + (j + 1) + ": ");
				cities.add(scanner.nextLine());
			}
			System.out.print("Extra price: ");
			double extra = getValidPrice();
			System.out.print("Max hours: ");
			int hours = getPositiveNumber();
			scanner.nextLine();
			added = ourSystem.addTaxi(new IntercityTaxi(code, avail, price, cities, extra, hours));
		}
		if (added) {
			System.out.println("Taxi added.");

		}

		else
			System.out.println("failed to add Taxi");
		return;
	}

	public static void assignTaxiToManager() {
		System.out.print("Taxi code: ");
		String taxiCode = scanner.nextLine();
		System.out.print("Manager ID: ");
		String managerId = scanner.nextLine();
		Taxi taxi = null;
		for (Taxi t : ourSystem.getTaxis()) {
			if (t != null && t.getTaxiCode().equals(taxiCode)) {
				taxi = t;
				break;
			}
		}
		if (taxi == null) {
			System.out.println("Taxi not found.");
			return;
		}
		boolean m2 = false;
		for (Manager m : ourSystem.getManagers()) {
			if (m != null && m.getId().equals(managerId)) {
				m.addTaxi(taxi);
				System.out.println("Taxi " + taxiCode + " assigned to Manager " + m.getFirstName());
				m2 = true;
			}
		}
		if (!m2)
			System.out.println("Manager not found.");
		return;
	}
	
	public static void addStation() {
		System.out.print("Station Name: ");
		String name = scanner.nextLine();
		System.out.println("Choose taxi codes from the following: ");
		for (Taxi taxi: ourSystem.getTaxis()) {
			System.out.print(taxi.getTaxiCode() + " || ");
		}
		
		System.out.print("Number of Taxis: ");
		int n = scanner.nextInt();
		if (n > ourSystem.getTaxis().size()) {
			System.out.println("Too many taxis");
			return;
		}
		scanner.nextLine();
		ArrayList<Taxi> taxis = new ArrayList<>();
		for (int j = 0; j < n; j++) {
			System.out.print("Taxi " + (j + 1) + ": ");
			String chosenTaxiCode = scanner.nextLine();
			Taxi chosenTaxi = null;
			for (Taxi taxi: ourSystem.getTaxis()) {
				if (taxi.getTaxiCode().equals(chosenTaxiCode)) {
					chosenTaxi = taxi;
				}
			}
			if (chosenTaxi == null) {
				System.out.println("There's no taxi with code " + chosenTaxiCode);
				return;
			}
			taxis.add(chosenTaxi);
		}
		if (ourSystem.addStation(new Station(name, taxis))) {
			System.out.println("Station added.");
			return;
		}
		System.out.println("Failed to add station.");
		return;
	}
	
	/**
	 * new function
	 */
	public static void getFreeTaxis() {
		if (ourSystem.getStations().isEmpty()) {
			System.out.println("There are no stations");
			return;
		}
		System.out.println("Choose one station from the following: ");
		for (Station station : ourSystem.getStations()) {
			System.out.print(station.getStationName() + " , ");
		}
		String chosenStationname = scanner.nextLine();
		Station chosenStation = null;
		for (Station station : ourSystem.getStations()) {
			if (station.getStationName().equals(chosenStationname)) {
				chosenStation = station;
			}
		}
		if (chosenStation == null) {
			System.out.println("The station " + chosenStationname + " does not exist");
			return;
		}
		System.out.println("The free taxis in station " + chosenStationname + " are:");
		System.out.println(ourSystem.getFreeTaxis(chosenStation));
	}

	/**
	 * new function
	 */
	public static void getExpressTaxis() {
		if (ourSystem.getSubscriptions().isEmpty()) {
			System.out.println("There are no subscribers");
			return;
		}
		System.out.println("Choose one code from the following subscribers: ");
		for (Subscription sub : ourSystem.getSubscriptions()) {
			System.out.print(
					"code:" + sub.getSubCode() + " name:" + sub.getFirstName() + " " + sub.getLastName() + " || ");
		}
		String chosenSubCode = scanner.nextLine();
		Subscription chosenSub = null;
		for (Subscription sub : ourSystem.getSubscriptions()) {
			if (sub.getSubCode().equals(chosenSubCode)) {
				chosenSub = sub;
			}
		}
		if (chosenSub == null) {
			System.out.println("The subscriber code " + chosenSubCode + " does not exist");
			return;
		}
		System.out.println("The express taxis " + chosenSub.getFirstName() + " " + chosenSub.getLastName() + "("
				+ chosenSubCode + ") ordered are:");
		System.out.println(ourSystem.getExpressTaxis(chosenSub));
	}

	public static void loginManager() {
		System.out.print("Manager ID: ");
		String id = scanner.nextLine();
		boolean founded = false;
		for (Manager m : ourSystem.getManagers()) {
			if (!(m instanceof MainManager) && m.getId().equals(id)) {
				System.out.println("Hello Manager " + m.getFirstName());
				founded = true;
				managerMenu(m);
			}
		}
		if (!founded)
			System.out.println("Manager not found.");
		return;

	}

	public static void managerMenu(Manager manager) {
		while (true) {
			System.out.println("\n--- Manager Menu ---");
			System.out.println("1. Add Order");
			System.out.println("2. Change Taxi in Order");
			System.out.println("0. Back to Main Menu");
			System.out.print("Choose: ");
			String choice = scanner.nextLine();
			switch (choice) {
			case "1":
				addOrder(manager);
			case "2":
				changeTaxiInOrder(manager);
			case "0": {
				return;
			}
			default:
				System.out.println("Invalid option.");
			}
		}
	}

	public static void addOrder(Manager manager) {
		System.out.print("Order ID: ");
		String orderId = scanner.nextLine();
		System.out.print("Subscriber ID: ");
		String subId = scanner.nextLine();
		System.out.print("Taxi Code: ");
		String taxiCode = scanner.nextLine();

		Subscription sub = null;
		for (Subscription s : ourSystem.getSubscriptions()) {
			if (s != null && s.getSubCode().equals(subId)) {
				sub = s;
				break;
			}
		}

		Taxi taxi = null;
		for (Taxi t : ourSystem.getTaxis()) {
			if (t != null && t.getTaxiCode().equals(taxiCode)) {
				taxi = t;
				break;
			}
		}

		if (sub == null || taxi == null) {
			System.out.println("Subscriber or Taxi not found.");
			return;
		}

		if (!taxi.isAvailable()) {
			System.out.println("Taxi is not available.");
			return;
		}

		System.out.print("Day: ");
		int day = getValidDay();
		System.out.print("Month: ");
		int month = getValidMonth();
		System.out.print("Hour: ");
		int hour = getValidHour();
		scanner.nextLine();

		if (ourSystem.addOrder(new Order(orderId, manager.getId(), day, month, hour, subId, taxi, taxi.getMinPrice()))) {
			taxi.setAvailable(false);
			System.out.println("Order added.");			
			return;
		}
		System.out.println("Order addition failed");
		return;
	}

	public static void changeTaxiInOrder(Manager manager) {
		if (ourSystem.getOrders().size() == 0) {
			System.out.print("There are no orders yet!");
			managerMenu(manager);

		}
		System.out.print("Orders codes : ");
		for (Order order : ourSystem.getOrders()) {
			System.out.println(order.getOrderNum() + " , ");
		}
		System.out.println();
		System.out.print("Enter order's code that you want to change : ");

		String num = scanner.nextLine();
		Order o = null;

		for (Order order : ourSystem.getOrders()) {
			if (order.getOrderNum().equals(num)) {
				o = order;
			}
		}

		if (o == null) {
			System.out.println("Invalid order number.");
			return;
		}

		Taxi oldTaxi = null;
		for (Taxi t : ourSystem.getTaxis()) {
			if (t != null && t.getTaxiCode().equals(o.getTaxi().getTaxiCode())) {
				oldTaxi = t;
				break;
			}
		}

		if (oldTaxi == null || !(oldTaxi instanceof Taxi)) {
			System.out.println("Original taxi is not regular. Cannot change.");
			return;
		}

		System.out.print("New Taxi Code: ");
		String newCode = scanner.nextLine();

		Taxi newTaxi = null;
		for (Taxi t : ourSystem.getTaxis()) {
			if (t != null && t.getTaxiCode().equals(newCode) && t.isAvailable()) {
				newTaxi = t;
				break;
			}
		}

		if (newTaxi == null) {
			System.out.println("New Taxi not found or not available.");
			return;
		}

		o.setTaxi(newTaxi);
		o.setOrderPrice(newTaxi.getMinPrice());
		oldTaxi.setAvailable(true);
		newTaxi.setAvailable(false);
		// update the new collection
		ourSystem.getTaxisPerSub().get(o.getSubCode()).remove(oldTaxi);
		ourSystem.getTaxisPerSub().get(o.getSubCode()).add(newTaxi);
		System.out.println("Taxi changed in order.");
		managerMenu(manager);
	}

	public static void loginSubscriber() {
		System.out.print("Subscriber ID: ");
		String id = scanner.nextLine();
		Subscription sub = null;
		for (Subscription s : ourSystem.getSubscriptions()) {
			if (s != null && s.getSubCode().equals(id)) {
				sub = s;
				break;
			}
		}
		if (sub != null) {
			System.out.println("Hello Subscriber " + sub.getFirstName());
			subscriberMenu(sub);
		} else {
			System.out.println("Subscriber not found.");
		}
	}

	public static void subscriberMenu(Subscription sub) {
		while (true) {
			System.out.println("\n--- Subscriber Menu ---");
			System.out.println("1. Print My Orders");
			System.out.println("2. Update Personal Details");
			System.out.println("3. Show Taxi Details");
			System.out.println("0. Back to Main Menu");
			System.out.print("Choose: ");
			String choice = scanner.nextLine();
			switch (choice) {
			case "1": {
				printSubscriberOrders(sub);
				break;
			}
			case "2": {
				updateSubscriberDetails(sub);
				break;
			}
			case "3": {
				showTaxiDetails(sub);
				break;
			}
			case "0": {
				return;
			}
			default:
				System.out.println("Invalid option.");
			}
		}
	}

	// Updated to use the new collection
	public static void printSubscriberOrders(Subscription sub) {
		ArrayList<Order> ordersOfSub = ourSystem.getOrdersPerSub().get(sub.getSubCode());
		if (ordersOfSub != null) {
			for (Order order : ordersOfSub) {
				System.out.println(order);
			}
		} else {
			System.out.println("No orders found for this subscriber.");
		}
	}

	public static void updateSubscriberDetails(Subscription sub) {
		System.out.print("New Phone: ");
		String phone = scanner.nextLine();
		System.out.print("New Address: ");
		String address = scanner.nextLine();
		sub.setPhone(phone);
		sub.setAddress(address);
		System.out.println("Details updated.");
		return;
	}

	public static void showTaxiDetails(Subscription sub) {
		System.out.print("Taxi Code: ");
		boolean found = false;
		String taxiCode = scanner.nextLine();
		for (Taxi t : ourSystem.getTaxis()) {
			if (t != null && t.getTaxiCode().equals(taxiCode)) {
				found = true;
				System.out.println(t);

			}
		}
		if (!found) {
			System.out.println("Taxi not found.");
		}
		return;

	}

	public static void displayAllTaxis() {
		System.out.println("\n--- All Taxis ---");
		for (Taxi t : ourSystem.getTaxis()) {
			System.out.println(t);
		}
	}

}
