package main.java.objects;

public class Configuration {
	private final float _oven_temp_ramping_time, _product_transition_time;

	/**
	 * @param _oven_temp_ramping_time
	 */
	public Configuration(float oven_temp_ramping_time, float product_transition_time) {
		this._oven_temp_ramping_time = oven_temp_ramping_time;
		this._product_transition_time = product_transition_time;
	}

	public float get_oven_temp_ramping_time() {
		return _oven_temp_ramping_time;
	}

	public float get_product_transition_time() {
		return _product_transition_time;
	}
}
