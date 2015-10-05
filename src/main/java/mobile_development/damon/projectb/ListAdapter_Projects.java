package mobile_development.damon.projectb;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Damon on 10/4/2015.
 */
public class ListAdapter_Projects extends ArrayAdapter<Project> // // TODO: 10/4/2015 Make adapter type class project
{
    private LayoutInflater inflater;
    private Context _context;
    private int _layourresourceid;
    private List<Project> _projectlist;
    private Activity activity;

    public ListAdapter_Projects(Context context,int layoutResourceId,List<Project> projects)
    {
        //Call super constructor for extending class initialisation (has no default)
        super(context,layoutResourceId);
        this._layourresourceid = layoutResourceId;
        this._context = context;
        this._projectlist=projects;

        inflater= ((Activity)context).getLayoutInflater();
    }

    @Override
    public int getCount() {
        return _projectlist.size();
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

        TextView title = (TextView) convertView.findViewById(R.id.project_title);
        TextView status = (TextView) convertView.findViewById(R.id.project_status);
        ImageView display_result = (ImageView) convertView.findViewById(R.id.project_result_icon);

        Log.d("TEST","LIST SIZE" + Integer.toString(_projectlist.size()));
        Project p = _projectlist.get(position);
        Log.d("TEST",Integer.toString(position));
        Log.d("TEST",Integer.toString(p.getCompletion_status()));
        Log.d("TEST","name" + p.getName());



        switch (p.getCompletion_status())
        {
            case 1:
                display_result.setColorFilter(_context.getResources().getColor(R.color.project_finished_success));
                display_result.setImageResource(R.drawable.ic_check_circle_24dp);

                break;

            case 2:
                display_result.setColorFilter(_context.getResources().getColor(R.color.project_finished_failed));
                display_result.setImageResource(R.drawable.ic_cancel_24dp);
                break;

            case 3:
                display_result.setColorFilter(_context.getResources().getColor(R.color.project_finished_underway));
                display_result.setImageResource(R.drawable.ic_report_24dp);
                break;
        }

            status.setText("In progress");



        return convertView;
    }
}
