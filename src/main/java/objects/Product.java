package main.java.objects;

/**
 * The Product class refers to the items that come into the system for the
 * purpose of getting in the oven.
 */
public class Product {
	private int _id;
	private String _name;
	private float _preheat_temp, _preheat_time, _oven_time;
	
	/**
	 * Product total time 
	 */
	public float total_time;

	/**
	 * @param _name Name of the Product
	 * @param _preheat_temp Required Pre-heating temperature of the Product
	 * @param _preheat_time Required Pre-heating time of the Product
	 * @param _oven_time Required Burning time for the Product
	 */
	public Product(String _name, float _preheat_temp, float _preheat_time, float _oven_time) {
		this._name = _name;
		this._preheat_temp = _preheat_temp;
		this._preheat_time = _preheat_time;
		this._oven_time = _oven_time;
	}

	/**
	 * @return ID of the Oven
	 */
	public int get_id() {
		return _id;
	}
	
	/**
	 * @param id Set the ID of the Product
	 */
	public void set_id(int id) {
		this._id = id;
	}

	/**
	 * @return Name of the Product
	 */
	public String get_name() {
		return _name;
	}

	/**
	 * @return Pre-heat Temperature of the Product
	 */
	public float get_preheat_temp() {
		return _preheat_temp;
	}

	/**
	 * @return Pre-heat Time for the Product
	 */
	public float get_preheat_time() {
		return _preheat_time;
	}

	/**
	 * @return Burning Time for the Product
	 */
	public float get_oven_time() {
		return _oven_time;
	}

	@Override
	public String toString() {
		return "Product [id=" + _id + ", name=" + _name + "]";
	}

}
