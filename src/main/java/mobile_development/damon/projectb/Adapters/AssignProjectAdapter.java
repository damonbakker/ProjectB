package mobile_development.damon.projectb.Adapters;

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
import mobile_development.damon.projectb.NetworkHandler;
import mobile_development.damon.projectb.R;

/**
 * Created by damon on 11/20/2015.
 */
public class AssignProjectAdapter extends ArrayAdapter<Student> {
    private LayoutInflater inflater;
    private Context _context;
    private List<Student> _studentlist;
    private int _layourresourceid;
    private Activity activity;

    public AssignProjectAdapter(Context context, int layoutResourceId, List<Student> list)
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

        CircularNetworkImageView student_avatar = (CircularNetworkImageView) convertView.findViewById(R.id.avatar);
        TextView coding_value = (TextView) convertView.findViewById(R.id.coding_value_student);
        TextView planning_value = (TextView) convertView.findViewById(R.id.planning_value_student);
        TextView design_value = (TextView) convertView.findViewById(R.id.design_value_student);

        Student s = _studentlist.get(position);

            coding_value.setText(String.valueOf(s.getCoding()));
            planning_value.setText(String.valueOf(s.getPlanning()));
            design_value.setText(String.valueOf(s.getDesign()));


        if (s.getAvatar_url() != null)
        {
            try
            {
                student_avatar.setDefaultImageResId(R.drawable.avatar_placeholder_white);
                student_avatar.setErrorImageResId(R.drawable.avatar_placeholder_white);
                //Student_avatar.setAdjustViewBounds(true);
                student_avatar.setImageUrl(s.getAvatar_url(), NetworkHandler.getInstance(_context).getImageLoader());
            }
            catch (Exception e)
            {
                Log.i("FailedImgPlacement", e.toString());
            }
        }


        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("CurrentStudentPosition", String.valueOf(position));
                Log.i("Dragstart",_studentlist.get(position).getName());
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);

                return true;
            }
        });
        return convertView;
    }




}

