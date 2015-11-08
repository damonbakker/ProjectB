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

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.Date;
import java.util.List;

import mobile_development.damon.projectb.Models.Project;

/**
 * Created by Damon on 10/4/2015.
 */
public class ListAdapter_Projects extends ArrayAdapter<Project>
{
    private LayoutInflater inflater;
    private Context _context;
    private List<Project> _projectlist;
    private int _layourresourceid;
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

    public void CalculateProgress()
    {
        Date startTime = null;
        Date currentDate = null;
        startTime = new Date(1444642918);
        currentDate = new Date(System.currentTimeMillis()/1000);


        long diffInSec = startTime.getTime() - currentDate.getTime();

        long seconds = diffInSec % 60;
        diffInSec /= 60;
        long minutes = diffInSec % 60;
        diffInSec /= 60;
        long hours = diffInSec % 24;
        diffInSec /= 24;
        long days = diffInSec;

        Log.i("Difference", days+":"+hours+":"+minutes);

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
        TextView eta = (TextView) convertView.findViewById(R.id.project_eta_text);
        RoundCornerProgressBar progress = (RoundCornerProgressBar) convertView.findViewById(R.id.project_progress);

        Project p = _projectlist.get(position);

        title.setText(p.getName());


        switch (p.getCompletion_status())
        {
            case 1:
                status.setTextColor(_context.getResources().getColor(R.color.project_finished_success));
                status.setText("Complete");
                eta.setText(" ");
                progress.setProgress(100);
                progress.setProgressColor(_context.getResources().getColor(R.color.project_finished_success));
                break;

            case 2:
                status.setTextColor(_context.getResources().getColor(R.color.project_finished_failed));
                status.setText("Failed");
                progress.setProgress(100);
                progress.setProgressColor(_context.getResources().getColor(R.color.project_finished_failed));
                break;

            case 3:
                status.setTextColor(_context.getResources().getColor(R.color.project_finished_underway));
                status.setText("Work in progress");
                eta.setText("D: 0" + "H: 0 ");
                progress.setProgress(50);
                progress.setProgressColor(_context.getResources().getColor(R.color.project_finished_underway));
                break;
        }





        return convertView;
    }
}
