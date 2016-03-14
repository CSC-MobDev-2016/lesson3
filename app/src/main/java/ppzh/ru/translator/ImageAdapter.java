package ppzh.ru.translator;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class ImageAdapter extends BaseAdapter {
    private String[] mContent;
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
        mContent = new String[0];
    }

    public void setContent(String[] array) {
        mContent = Arrays.copyOf(array, array.length);
    }

    @Override
    public int getCount() {
        return mContent.length;
    }

    @Override
    public Object getItem(int position) {
        return mContent[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = new ImageView(mContext);

            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            int devider = size.x > size.y ? 3 : 2;
            int width = size.x / devider;
            int height = width;

            view.setLayoutParams(new GridView.LayoutParams(height, width));
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);

        }
        String url = (String) getItem(position);

        Picasso.with(mContext).load(url).into(view);
        return view;
    }

    public String[] getContent() {
        return mContent;
    }
}
