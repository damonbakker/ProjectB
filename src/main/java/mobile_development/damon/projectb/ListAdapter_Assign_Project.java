package mobile_development.damon.projectb;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.List;

import mobile_development.damon.projectb.Models.CircularNetworkImageView;
import mobile_development.damon.projectb.Models.Project;
import mobile_development.damon.projectb.Models.Student;

/**
 * Created by damon on 11/20/2015.
 */
public class ListAdapter_Assign_Project extends ArrayAdapter<Student> {
    private LayoutInflater inflater;
    private Context _context;
    private List<Student> _studentlist;
    private int _layourresourceid;
    private Activity activity;

    public ListAdapter_Assign_Project(Context context,int layoutResourceId,List<Student> list)
    {
        //Call super constructor for extending class initialisation (has no default)
        super(context,layoutResourceId);
        this._layourresourceid = layoutResourceId;
        this._context = context;
        this._studentlist = list;

        inflater= ((Activity)context).getLayoutInflater();
    }

    @Override
    public int getCount() {
        return _studentlist.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item_assign_student, parent, false);



      //  TextView tvname = (TextView) convertView.findViewById(R.id.tvHome);
       // TextView tvhome = (TextView) convertView.findViewById(R.id.tvName);
            CircularNetworkImageView student_avatar = (CircularNetworkImageView) convertView.findViewById(R.id.avatar);
       // tvname.setText(list.get(position));

        Student s = _studentlist.get(position);

        if (s.getAvatar_url() != null)
        {
            try
            {
                Log.i("GOOD",s.getAvatar_url());
                student_avatar.setDefaultImageResId(R.drawable.avatar_placeholder_white);
                student_avatar.setErrorImageResId(R.drawable.avatar_placeholder_white);
                //  student_avatar.setAdjustViewBounds(true);
                student_avatar.setImageUrl(s.getAvatar_url(),NetworkHandler.getInstance(_context).getImageLoader());
            }
            catch (Exception e)
            {
                Log.i("FAIL", e.toString());
            }
        }


        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("test", _studentlist.get(position).getName());
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
              //  v.setVisibility(View.INVISIBLE);
                return true;
            }
        });
        return convertView;
    }



  /*  private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }*/
}

