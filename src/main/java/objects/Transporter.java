package main.java.objects;

import java.util.ArrayList;

import main.java.interfaces.CompletionObserver;

/**
 * To keep track of the completed products. Implements the CompletionObserver to
 * get notified by the ovens when a product is done.
 */
public class Transporter implements CompletionObserver {

	private ArrayList<Product> _completedProducts;
	
	/**
	 * Default constructor for Transporter
	 */
	public Transporter() {
		this._completedProducts = new ArrayList<>();
	}

	@Override
	public void moveToCompleted(Product product) {
		_completedProducts.add(product);

	}

	
	/**
	 * Display a list of completed Products. 
	 */
	public void showAllProducts() {
		this._completedProducts.forEach(product -> {
			System.out.println("\t-> " + product);
		});
	}

}
