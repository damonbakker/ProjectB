package mobile_development.damon.projectb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.List;

import mobile_development.damon.projectb.Models.Project;
import mobile_development.damon.projectb.Models.Student;

/**
 * Created by damon on 11/20/2015.
 */
public class ListAdapter_Assign_Project extends ArrayAdapter<Student> {
    private LayoutInflater inflater;
    private Context _context;
    private List<Project> _studentlist;
    private int _layourresourceid;
    private Activity activity;

    public ListAdapter_Assign_Project(Context context,int layoutResourceId,List<Project> projects)
    {
        //Call super constructor for extending class initialisation (has no default)
        super(context,layoutResourceId);
        this._layourresourceid = layoutResourceId;
        this._context = context;
        this._studentlist=projects;

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
            convertView = inflater.inflate(R.layout.list_item_project, parent, false);

        TextView title = (TextView) convertView.findViewById(R.id.textView4);


        Project p = _studentlist.get(position);

        //title.setText(" ejaculation");


        return convertView;
    }
}

