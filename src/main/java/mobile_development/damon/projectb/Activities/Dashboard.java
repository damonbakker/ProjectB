package mobile_development.damon.projectb.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import net.danlew.android.joda.JodaTimeAndroid;

import mobile_development.damon.projectb.Fragments.Main.Feedback;
import mobile_development.damon.projectb.Fragments.Main.Inventory;
import mobile_development.damon.projectb.Fragments.Main.Projects;
import mobile_development.damon.projectb.Fragments.Main.Settings;
import mobile_development.damon.projectb.Fragments.Main.Students;
import mobile_development.damon.projectb.R;
import mobile_development.damon.projectb.SharedPreference;


/*Uses the Main fragments*/
public class Dashboard extends AppCompatActivity
{
    private android.support.v7.widget.Toolbar mToolbar;
    private Drawer result;
    public  boolean exit_state = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //initialise the jodatime datetime library for java.
        JodaTimeAndroid.init(this);

       android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(getResources().getString(R.string.title_toolbar_home));

       final PrimaryDrawerItem item_dashboard = new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(GoogleMaterial.Icon.gmd_home).withIdentifier(1);
       final SecondaryDrawerItem item_projects = new SecondaryDrawerItem().withName(R.string.drawer_item_projects).withIcon(GoogleMaterial.Icon.gmd_folder).withIdentifier(2);
       final SecondaryDrawerItem item_students = new SecondaryDrawerItem().withName(R.string.drawer_item_students).withIcon(GoogleMaterial.Icon.gmd_account_box).withIdentifier(3);
       final SecondaryDrawerItem item_inventory = new SecondaryDrawerItem().withName(R.string.drawer_item_inventory).withIcon(GoogleMaterial.Icon.gmd_archive).withIdentifier(4);
       final SecondaryDrawerItem item_feedback = new SecondaryDrawerItem().withName(R.string.drawer_item_feedback).withIcon(GoogleMaterial.Icon.gmd_info).withIdentifier(5);
       final SecondaryDrawerItem item_settings = new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(6);

       final android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        result = new DrawerBuilder(this)
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
                                Fragment dashboardFragment = new mobile_development.damon.projectb.Fragments.Main.Dashboard();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container,dashboardFragment)
                                        .addToBackStack(null)
                                        .commit();

                                break;
                            case 2:
                                Fragment projectsFragment = new Projects();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container,projectsFragment)
                                        .addToBackStack(null)
                                        .commit();

                                break;
                            case 3:
                                Fragment studentsFragment = new Students();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container,studentsFragment)
                                        .addToBackStack(null)
                                        .commit();

                                break;
                            case 4:
                                Fragment inventoryFragment = new Inventory();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container,inventoryFragment)
                                        .addToBackStack(null)
                                        .commit();
                                break;
                            case 5:
                                Fragment feedbackFragment = new Feedback();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container,feedbackFragment)
                                        .addToBackStack(null)
                                        .commit();

                                break;
                            case 6:
                                Fragment settingsFragment = new Settings();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container,settingsFragment)
                                        .addToBackStack(null)
                                        .commit();

                                break;

                        }


                        // do something with the clicked item :D
                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        Fragment dashboardFragment = new mobile_development.damon.projectb.Fragments.Main.Dashboard();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container,dashboardFragment)
                .addToBackStack(null)
                .commit();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_dashboard, menu);
        return true;


    }



    @Override
    public void onBackPressed() {
        if (exit_state) {
            Dashboard.this.finish();
            return;
        }

        this.exit_state = true;
        Toast.makeText(this, "Please press back once more to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                exit_state =false;
            }
        }, 2000);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId())
        {
            case R.id.action_logout:

                AlertDialog.Builder builder_logout =
                        new AlertDialog.Builder(this);
                builder_logout.setTitle("Are you sure you want to log out?");
                builder_logout.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        SharedPreference.unsetLogin(getApplicationContext());
                        Intent intent = new Intent(Dashboard.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
                builder_logout.setNegativeButton("Cancel", null);
                builder_logout.show();

                break;

            case R.id.action_about:

                AlertDialog.Builder builder_about =
                        new AlertDialog.Builder(this);
                builder_about.setTitle("About project B");
                builder_about.setMessage(getResources().getString(R.string.about_app));
                builder_about.setPositiveButton("Got it", null);
                builder_about.show();

                break;

            case R.id.action_settings:

                break;

        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
