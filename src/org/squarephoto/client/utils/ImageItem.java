package org.squarephoto.client.utils;

/**
 * 
 * @author Alexander Katanov
 * 
 */
public final class ImageItem {

	private ImageListener listener;
	private String url;
	private int attempts = 0;

	public ImageItem() {

	}

	public ImageItem(String url, ImageListener listener) {
		this.url = url;
		this.listener = listener;
	}

	public ImageListener getListener() {
		return listener;
	}

	public void setListener(ImageListener listener) {
		this.listener = listener;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int attempts() {
		return attempts;
	}

	public void incrementAttempts() {
		this.attempts++;
	}
}