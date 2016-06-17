package mobile_development.damon.projectb.Adapters;

/**
 * Created by damon on 10/9/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import mobile_development.damon.projectb.R;

public class GridViewAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private int test;
    private int test2;

    public GridViewAdapter(Context mContext,int test,int test2) {
        this.mContext = mContext;
        this.test = test;
        this.test2 = test2;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.list_item_inventory, null);
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView t = (TextView)convertView.findViewById(R.id.position);
        t.setText((position + 1 )+".");
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}