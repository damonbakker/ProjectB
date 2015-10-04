package mobile_development.damon.projectb;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Damon on 10/4/2015.
 */
public class ListAdapter_Projects extends ArrayAdapter<> // // TODO: 10/4/2015 Make adapter type class project
{
    private Context _context;
    private int _layourresourceid;
    private List<Project> _projects;

    public ListAdapter_Projects(Context context,int layoutResourceId,List<Project> projects)
    {
        //Call super constructor for extending class initialisation (has no default)
        super(context,layoutResourceId);
        this._projects=projects;
    }


  /*  @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return true;
    }*/
}
