package main.java.interfaces;

import main.java.objects.Product;

/**
 * This is an implementation of the Observer patter which makes use of an
 * interface to notify the Transporter to move the product to the list of
 * completed products.
 */
public interface CompletionObserver {

	/**
	 * @param product Completed Product
	 */
	void moveToCompleted(Product product);

}
