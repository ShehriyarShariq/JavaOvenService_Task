package main.java.objects;

/**
 * The Configuration class is for the purpose of some common configurations required for the system to work.
 */
public class Configuration {
	private final float _oven_temp_ramping_time, _product_transition_time;

	/**
	 * @param oven_temp_ramping_time Temperature Ramping Time
	 * @param product_transition_time Product Transition Time
	 */
	public Configuration(float oven_temp_ramping_time, float product_transition_time) {
		// The time it takes the oven to increase its temperature by 1 degree.
		this._oven_temp_ramping_time = oven_temp_ramping_time;
		// The time it takes for one product to get replaced by another as soon as the first product finishes.
		this._product_transition_time = product_transition_time;
	}

	/**
	 * @return Oven Temperature Ramping Time
	 */
	public float get_oven_temp_ramping_time() {
		return _oven_temp_ramping_time;
	}

	/**
	 * @return Oven Product Transition Time
	 */
	public float get_product_transition_time() {
		return _product_transition_time;
	}
}
