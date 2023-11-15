package main.java.objects;

import java.util.ArrayList;

import main.java.interfaces.OvenObserver;

public class Oven extends Thread {
	private int _id;
	private float _current_temp, _total_time;

	private Configuration _config;
	private OvenObserver _scheduler;
	private Transporter _transporter;

	private long _product_added_to_oven_time;

	private ArrayList<Product> _queue;

	public Oven(int id, Configuration config, OvenObserver scheduler, Transporter transporter) {
		this._id = id;
		this._config = config;
		this._scheduler = scheduler;
		this._transporter = transporter;

		this._queue = new ArrayList<>();
		this._current_temp = 0;
		this._product_added_to_oven_time = -1;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}

	public int id() {
		return this._id;
	}
	
	public float getOvenTemperatureAtEnd() {
		return this._queue.isEmpty() ? 0 : this._queue.get(this._queue.size() - 1).get_preheat_temp();
	}

	public void addToOvenQueue(Product product) {
		float last_product_temp_diff = this.getOvenTemperatureAtEnd();

		float temp_tamping_time = this._config.get_oven_temp_ramping_time()
				* ((int) Math.abs(last_product_temp_diff - product.get_preheat_temp()));

		product.total_time = product.get_preheat_time() + product.get_oven_time()
				+ this._config.get_product_transition_time() + temp_tamping_time;

		this._total_time += product.total_time;

		if (this._queue.isEmpty()) {
			this._product_added_to_oven_time = System.currentTimeMillis();
		}

		this._queue.add(product);
	}

	public Product removeFromOven() {
		if (this._queue.isEmpty())
			return null;

		Product product = this._queue.get(0);

		this._total_time -= product.total_time;

		return product;
	}

	public Product getCurrentProductInOven() {
		return this._queue.get(0);
	}

	public float getOvenReservationTime() {
		return this._total_time;
	}

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
			status = "Handling Product \"" + this._queue.get(0).get_name() + "\"";
		}
		
		return "Oven " + this._id + " -> Status: " + status;
	}

}
