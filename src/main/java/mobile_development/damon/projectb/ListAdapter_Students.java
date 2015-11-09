package mobile_development.damon.projectb;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.ArrayList;
import java.util.List;

import mobile_development.damon.projectb.Models.Project;
import mobile_development.damon.projectb.Models.Student;

/**
 * Created by damon on 10/16/2015.
 */
public class ListAdapter_Students extends ArrayAdapter<Student>
{
    private LayoutInflater inflater;
    private Context _context;
    private List<Student> _studentlist;
    private int _layourresourceid;
    private Activity activity;

    public ListAdapter_Students(Context context,int layoutResourceId,List<Student> students)
    {

        //Call super constructor for extending class initialisation (has no default)
        super(context,layoutResourceId);
        this._layourresourceid = layoutResourceId;
        this._context = context;
        this._studentlist = students;

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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item_student, parent, false);

        RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.layout_student_item);
        ImageView student_avatar = (ImageView) convertView.findViewById(R.id.avatar);

        TextView student_name = (TextView) convertView.findViewById(R.id.student_name);
        TextView coding = (TextView) convertView.findViewById(R.id.coding_value);
        TextView planning = (TextView) convertView.findViewById(R.id.planning_value);
        TextView design = (TextView) convertView.findViewById(R.id.design_value);
        TextView level = (TextView) convertView.findViewById(R.id.level);

        Student s = _studentlist.get(position);



        if (s.getId() == 3)
        {
            layout.setBackgroundColor(_context.getResources().getColor(R.color.quality3));
        }
        if (s.getId() == 4)
        {
            layout.setBackgroundColor(_context.getResources().getColor(R.color.quality2));
        }
        if (s.getId() == 2)
        {
            layout.setBackgroundColor(_context.getResources().getColor(R.color.quality1));
        }

        student_name.setText(s.getName());
        coding.setText(String.valueOf(s.getCoding()));
        planning.setText(String.valueOf(s.getPlanning()));
        design.setText(String.valueOf(s.getDesign()));
        level.setText(String.valueOf(s.getLevel()));






        return convertView;
    }

}
