package org.squarephoto.client.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageCacheService {

	private static final String TAG = ImageCacheService.class.getName();

	private File cacheDir = null;

	public ImageCacheService(File cacheDir) {
		this.cacheDir = cacheDir;
	}

	public void save(String url, Bitmap image) {
		File file = new File(cacheDir, getHash(url));
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		boolean keep = true;

		try {
			fos = new FileOutputStream(file);
			image.compress(CompressFormat.PNG, 100, fos);
		} catch (Exception e) {
			keep = false;
			Log.e(TAG, e.toString(), e);
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (fos != null)
					fos.close();
				if (keep == false)
					file.delete();
			} catch (Exception e) {
				/* do nothing */
			}
		}
	}

	public Bitmap get(String url) {

		Bitmap image = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(cacheDir, getHash(url)));
			image = BitmapFactory.decodeStream(fis);
		} catch (Exception e) {
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
				// do nothing
			}
		}
		return image;
	}

	private String getHash(String str) {
		String res = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			res = new BigInteger(1, md.digest(str.getBytes())).toString();
		} catch (Exception e) {
			Log.e(TAG, "", e);
		}
		return res;
	}
}
