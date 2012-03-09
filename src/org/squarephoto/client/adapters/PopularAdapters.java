package org.squarephoto.client.adapters;

import org.squarephoto.client.R;
import org.squarephoto.client.sql.PopularDbAdapter;
import org.squarephoto.client.utils.ImageListener;
import org.squarephoto.client.utils.ImageLoader;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

/**
 * 
 * @author Alexander Katanov
 * 
 */
public class PopularAdapters extends SimpleCursorAdapter {

	@SuppressWarnings("unused")
	private static final String TAG = PopularAdapters.class.getName();

	private ImageLoader mImageLoader;

	public static class ViewHolder {
		public ImageView imageView;
		public ProgressBar loadingBar;
	}

	public PopularAdapters(Context context, Cursor c) {
		super(context, R.layout.popular_item_layout, c,
				new String[] { PopularDbAdapter.KEY_THUMBNAIL },
				new int[] { R.id.image_view });
		mImageLoader = new ImageLoader(context.getExternalCacheDir());
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.popular_item_layout, null,
				true);

		ViewHolder holder = new ViewHolder();
		holder.imageView = (ImageView) rowView.findViewById(R.id.image_view);
		holder.loadingBar = (ProgressBar) rowView.findViewById(R.id.loadingBar);

		rowView.setTag(holder);
		return rowView;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		final ViewHolder holder = (ViewHolder) view.getTag();
		int imageIndex = cursor.getColumnIndex(PopularDbAdapter.KEY_THUMBNAIL);

		String profileImage = cursor.getString(imageIndex);

		Bitmap image = null;
		image = mImageLoader.loadImage(profileImage, new ImageListener() {

			@Override
			public void imageLoaded(Bitmap imageBitmap) {
				holder.loadingBar.setVisibility(View.GONE);
				holder.imageView.setImageBitmap(imageBitmap);
				holder.imageView.setVisibility(View.VISIBLE);
			}
		});
		if (image != null) {
			holder.imageView.setImageBitmap(image);
		} else {
			holder.loadingBar.setVisibility(View.VISIBLE);
			holder.imageView.setVisibility(View.GONE);
		}
	}
}
