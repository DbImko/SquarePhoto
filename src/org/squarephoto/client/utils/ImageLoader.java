package org.squarephoto.client.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

/**
 * 
 * @author Alexander Katanov
 * 
 */
public class ImageLoader {
	private static final String TAG = ImageLoader.class.getName();

	private static final int MAX_ATTEMPT_COUNT = 3;

	private ImageCacheService mImageCacheService;

	private final List<ImageItem> mQueue;

	private final Handler mHandler;

	private static Thread mThread;

	private static Downloader mDownloader;

	public ImageLoader(File cacheDir) {
		mQueue = new ArrayList<ImageItem>();
		mHandler = new Handler();
		mDownloader = new Downloader();
		mThread = new Thread(mDownloader);
		mImageCacheService = new ImageCacheService(cacheDir);
	}

	public Bitmap loadImage(String uri, ImageListener listener) {
		try {
			Bitmap image = mImageCacheService.get(uri);
			if (image == null) {
				mQueue.add(new ImageItem(uri, listener));
				if (mThread.getState() == Thread.State.NEW) {
					mThread.start();
				} else if (mThread.getState() == Thread.State.TERMINATED) {
					mThread = new Thread(mDownloader);
					mThread.start();
				}
			}
			return image;
		} catch (Exception e) {
			Log.e(TAG, "", e);
			return null;
		}
	}

	private class Downloader implements Runnable {
		@Override
		public void run() {
			synchronized (this) {
				while (mQueue.size() > 0) {
					loadImage();
				}
			}
		}

		private void loadImage() {
			ImageItem item = mQueue.remove(0);
			try {
				if (item.attempts() < MAX_ATTEMPT_COUNT) {
					Bitmap image = mImageCacheService.get(item.getUrl());
					if (image != null) {
						mHandler.post(new HandlerExecuter(item));
					} else {
						item.incrementAttempts();
						Bitmap bmp = ImageUtils.readBitmapFromNetwork(item
								.getUrl());
						if (bmp != null) {
							mImageCacheService.save(item.getUrl(), bmp);
							mHandler.post(new HandlerExecuter(item));
						} else {
							mQueue.add(item);
						}
					}
				} else {
					mHandler.post(new HandlerExecuter(item));
				}
			} catch (Exception e) {
				Log.e(TAG, "", e);
			}
		}

	}

	private class HandlerExecuter implements Runnable {
		private ImageItem imageItem = null;

		public HandlerExecuter(ImageItem item) {
			imageItem = item;
		}

		@Override
		public void run() {
			ImageListener listener = imageItem.getListener();
			if (listener != null) {
				listener.imageLoaded(mImageCacheService.get(imageItem.getUrl()));
			}
		}
	}

}
