package main.java.objects;

import java.util.ArrayList;

/**
 * This is the main class in the system which represents a single oven worker.
 * Oven extends a thread for the purpose of having multiple ovens work
 * simultaneously.
 */
public class Oven extends Thread {
	// Basic properties of an oven
	private int _id;
	private float _current_temp, _total_time;

	// Current status of the viewer. Visible in the output of the toString function.
	private String _status = "Waiting";

	private Configuration _config;
	private Transporter _transporter;

	// List of products that are waiting for the oven to finish. The top most
	// product in the queue represents the product that is currently in the oven.
	private ArrayList<Product> _queue;

	/**
	 * @param id ID of the Oven
	 * @param config Global Configuration Object
	 * @param transporter Transporter
	 */
	public Oven(int id, Configuration config, Transporter transporter) {
		this._id = id;
		this._config = config;
		this._transporter = transporter;

		this._queue = new ArrayList<>();
		this._current_temp = 0;
	}

	/**
	 * Main worker of the oven thread. It checks if there is a product in the queue
	 * and starts processing it until no more products are in the queue.
	 */
	@Override
	public void run() {
		while (true) {
			synchronized (this) {
				while (this._queue.isEmpty()) {
					try {
						// If the queue is empty, wait for a notification
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			// The purpose for the calculation below is to get the most ideal oven to pass
			// the product to based on the internal temperature of the product that goes in
			// at the last in the oven.
			Product currentProduct = this._queue.get(0);

			float temp_ramping_time = this._config.get_oven_temp_ramping_time()
					* ((int) Math.abs(this._current_temp - currentProduct.get_preheat_temp()));

			int initialTime = (int) Math.ceil((this._config.get_product_transition_time() + temp_ramping_time / 10));

			this._status = "Changing Temperature";

			try {
				// Processing time for temperature ramping and the transition time.
				Thread.sleep(initialTime * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this._status = "Pre-heating";

			try {
				// Processing time for pre-heating.
				Thread.sleep(((int) currentProduct.get_preheat_time()) * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this._status = "Burning";

			try {
				// Processing time for burning.
				Thread.sleep(((int) currentProduct.get_oven_time()) * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Move the product to the completed list using the transporter and remove it
			// from the oven.
			this._transporter.moveToCompleted(currentProduct);
			this.removeFromOven();
		}
	}

	/**
	 * @return ID of the Oven
	 */
	public int id() {
		return this._id;
	}

	/**
	 * @return The temperature of the last product in the oven queue. 
	 */
	public float getOvenTemperatureAtEnd() {
		return this._queue.isEmpty() ? 0 : this._queue.get(this._queue.size() - 1).get_preheat_temp();
	}

	/**
	 * @param product 
	 * This function add a product to the end of the oven queue. It
	 *                calculates the time the product will take and adds it to the
	 *                total time of the oven for priority sorting.
	 */
	public void addToOvenQueue(Product product) {
		float last_product_temp_diff = this.getOvenTemperatureAtEnd();

		float temp_ramping_time = this._config.get_oven_temp_ramping_time()
				* ((int) Math.abs(last_product_temp_diff - product.get_preheat_temp()));

		product.total_time = product.get_preheat_time() + product.get_oven_time()
				+ this._config.get_product_transition_time() + temp_ramping_time;

		this._total_time += product.total_time;

		this._queue.add(product);

		synchronized (this) {
			// Notify the waiting thread that a product is available
			this.notify();
		}
	}

	
	/**
	 * @return Remove the top most product in the queue and update the total time of
	 *         the oven.
	 */
	public Product removeFromOven() {
		if (this._queue.isEmpty())
			return null;

		Product product = this._queue.get(0);

		this._total_time -= product.total_time;

		return product;
	}

	/**
	 * @return Current Product in the Oven
	 */
	public Product getCurrentProductInOven() {
		return this._queue.get(0);
	}

	/**
	 * @return Total Time of the Oven
	 */
	public float getOvenReservationTime() {
		return this._total_time;
	}

	/**
	 * @param oven Another oven to compare it with.
	 * @return A comparison of the one oven with another.
	 */
	public int compareTo(Oven oven) {
		float ovenTime = oven.getOvenReservationTime();

		return this._total_time == ovenTime ? 0 : this._total_time > ovenTime ? 1 : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Oven) {
			return this._id == ((Oven) obj).id();
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		String status = "";

		if (this._queue.isEmpty()) {
			status = "Idle...";
		} else {
			status = this._status + " for Product \"" + this._queue.get(0).get_name() + "\"";
		}

		return "Oven " + this._id + " -> Status: " + status;
	}

}
