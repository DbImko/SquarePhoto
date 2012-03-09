package org.squarephoto.client.models;

import java.util.ArrayList;
import java.util.List;

public class PopularResult {
	private Exception exception;
	private List<PopularItemModel> items;
	
	public PopularResult() {
		items = new ArrayList<PopularItemModel>();
	}
	
	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public List<PopularItemModel> getItems() {
		return items;
	}

	public void setItems(List<PopularItemModel> items) {
		this.items = items;
	}
	
	public boolean hasError() {
		return exception != null;
	}
}
