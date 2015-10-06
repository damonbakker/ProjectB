package mobile_development.damon.projectb;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Damon on 10/4/2015.
 */
public class ListAdapter_Projects extends ArrayAdapter<Project>
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

    public void CalculatePROGRESS()
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



        /*
        * Get start and end DATE
        * Calculate difference
        * if difference is 10 days
        * then if 5 days has surpassed progress is 50%
        * implement this at later stage
        *
        *
        *
        * */
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
        ImageView display_result = (ImageView) convertView.findViewById(R.id.project_result_icon);
        RoundCornerProgressBar progress = (RoundCornerProgressBar) convertView.findViewById(R.id.project_progress);

        Project p = _projectlist.get(position);

        title.setText(p.getName());



        switch (p.getCompletion_status())
        {
            case 1:
                status.setTextColor(_context.getResources().getColor(R.color.project_finished_success));
                status.setText("Complete");
                display_result.setColorFilter(_context.getResources().getColor(R.color.project_finished_success));
                display_result.setImageResource(R.drawable.ic_check_circle_24dp);
                eta.setText(" ");
                progress.setProgress(100);
                progress.setProgressColor(_context.getResources().getColor(R.color.project_finished_success));


                break;

            case 2:
                status.setTextColor(_context.getResources().getColor(R.color.project_finished_failed));
                status.setText("Failed");
                display_result.setColorFilter(_context.getResources().getColor(R.color.project_finished_failed));
                display_result.setImageResource(R.drawable.ic_cancel_24dp);
                progress.setProgress(100);
                progress.setProgressColor(_context.getResources().getColor(R.color.project_finished_failed));
                break;

            case 3:
                status.setTextColor(_context.getResources().getColor(R.color.project_finished_underway));
                status.setText("Work in progress");
                display_result.setColorFilter(_context.getResources().getColor(R.color.project_finished_underway));
                eta.setText("D: 0" + "H: 0 ");
                display_result.setImageResource(0);
                progress.setProgress(50);
                progress.setProgressColor(_context.getResources().getColor(R.color.project_finished_underway));
                break;
        }





        return convertView;
    }
}
