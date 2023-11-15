package main.java.objects;

/**
 * The Product class refers to the items that come into the system for the
 * purpose of getting in the oven.
 */
public class Product {
	private int _id;
	private String _name;
	private float _preheat_temp, _preheat_time, _oven_time;
	public float total_time;

	/**
	 * @param _id
	 * @param _name
	 * @param _preheat_temp
	 * @param _preheat_time
	 * @param _oven_time
	 */
	public Product(String _name, float _preheat_temp, float _preheat_time, float _oven_time) {
		this._name = _name;
		this._preheat_temp = _preheat_temp;
		this._preheat_time = _preheat_time;
		this._oven_time = _oven_time;
	}

	public int get_id() {
		return _id;
	}
	
	public void set_id(int id) {
		this._id = id;
	}

	public String get_name() {
		return _name;
	}

	public float get_preheat_temp() {
		return _preheat_temp;
	}

	public float get_preheat_time() {
		return _preheat_time;
	}

	public float get_oven_time() {
		return _oven_time;
	}

	@Override
	public String toString() {
		return "Product [id=" + _id + ", name=" + _name + "]";
	}

}
