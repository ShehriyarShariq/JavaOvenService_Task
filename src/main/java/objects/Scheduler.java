package main.java.objects;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The scheduler is tasked with keeping track of all the ovens and assigning any
 * new products that come into the system to the next best oven.
 */
public class Scheduler {
	private ArrayList<Oven> _ovens;

	private Configuration _config;

	static int product_count = 1;

	/**
	 * @param config Global Configuration Object
	 */
	public Scheduler(Configuration config) {
		this._config = config;

		this._ovens = new ArrayList<>();
	}

	/**
	 * @param oven Add new oven to the system
	 */
	public void addNewOven(Oven oven) {
		this._ovens.add(oven);
	}

	/**
	 * @param product
	 * Add a new product to the next best available oven based on the total time of the oven + the time required for the product.
	 */
	public void scheduleNewProduct(Product product) {
		this._ovens.sort(new Comparator<Oven>() {
			public int compare(Oven o1, Oven o2) {
				float o1_temp_ramping_time = _config.get_oven_temp_ramping_time()
						* ((int) Math.abs(o1.getOvenTemperatureAtEnd() - product.get_preheat_temp()));

				float o2_temp_ramping_time = _config.get_oven_temp_ramping_time()
						* ((int) Math.abs(o2.getOvenTemperatureAtEnd() - product.get_preheat_temp()));

				float o1_reservation_time = o1.getOvenReservationTime() + o1_temp_ramping_time;
				float o2_reservation_time = o2.getOvenReservationTime() + o2_temp_ramping_time;

				return Float.compare(o1_reservation_time, o2_reservation_time);
			}
		});

		product.set_id(product_count++);

		this._ovens.get(0).addToOvenQueue(product);
	}

	/**
	 * Display a list of all the ovens sorted by their id. 
	 */
	public void showAllOvens() {
		ArrayList<Oven> displayList = new ArrayList<>();

		displayList.addAll(this._ovens);

		displayList.sort(new Comparator<Oven>() {

			@Override
			public int compare(Oven o1, Oven o2) {
				return Integer.compare(o1.id(), o2.id());
			}

		});

		displayList.forEach(oven -> {
			System.out.println("\t-> " + oven);
		});
	}

}
