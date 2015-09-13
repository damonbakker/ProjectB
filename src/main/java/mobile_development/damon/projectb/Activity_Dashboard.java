package mobile_development.damon.projectb;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class Activity_Dashboard extends AppCompatActivity
{
    private android.support.v7.widget.Toolbar mToolbar;
    private Drawer result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

       android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       final PrimaryDrawerItem item_dashboard = new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(GoogleMaterial.Icon.gmd_home).withIdentifier(1);
       final SecondaryDrawerItem item_projects = new SecondaryDrawerItem().withName(R.string.drawer_item_projects).withIcon(GoogleMaterial.Icon.gmd_folder).withIdentifier(2);
       final SecondaryDrawerItem item_students = new SecondaryDrawerItem().withName(R.string.drawer_item_students).withIcon(GoogleMaterial.Icon.gmd_account_box).withIdentifier(3);
       final SecondaryDrawerItem item_inventory = new SecondaryDrawerItem().withName(R.string.drawer_item_inventory).withIcon(GoogleMaterial.Icon.gmd_laptop_windows).withIdentifier(4);
       final SecondaryDrawerItem item_feedback = new SecondaryDrawerItem().withName(R.string.drawer_item_feedback).withIcon(GoogleMaterial.Icon.gmd_info).withIdentifier(5);
       final SecondaryDrawerItem item_settings = new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(6);

       final android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();


        result = new DrawerBuilder(this)
                //this layout have to contain child layouts
                .withRootView(R.id.drawer_container)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        item_dashboard,
                        item_projects,
                        item_students,
                        item_inventory,
                        new DividerDrawerItem(),
                        item_feedback,
                        item_settings
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        result.closeDrawer();

                        switch (drawerItem.getIdentifier()) {
                            case 1:
                                Fragment newFragment = new Activity_DashboardFragment();

                                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.frame_container, newFragment);
                                transaction.addToBackStack(null);

                                transaction.commit();
                                break;
                            case 2:
                                Toast.makeText(Activity_Dashboard.this, "Navigation", Toast.LENGTH_SHORT).show();
                                break;

                        }


                        // do something with the clicked item :D
                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();




                //icon ideas,  material desgn / xml, (account/account multiple,clippboard account), poll, school, (file-document,folder-multiple)
                // Colors or NO colors for the sidebar



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__dashboard, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
