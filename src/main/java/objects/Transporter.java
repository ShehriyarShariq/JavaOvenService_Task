package main.java.objects;

import java.util.ArrayList;

import main.java.interfaces.CompletionObserver;

public class Transporter implements CompletionObserver {

	private ArrayList<Product> _completedProducts;

	/**
	 * @param completedProducts
	 */
	public Transporter() {
		this._completedProducts = new ArrayList<>();
	}

	@Override
	public void moveToCompleted(Product product) {
		// TODO Auto-generated method stub
		_completedProducts.add(product);
		
	}
	
	public void showAllProducts() {
		this._completedProducts.forEach(product -> {
			System.out.println("\t-> " + product );
		});
	}

}
