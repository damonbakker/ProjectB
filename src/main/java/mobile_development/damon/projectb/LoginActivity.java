package mobile_development.damon.projectb;


import android.util.Log;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


public class LoginActivity extends AppCompatActivity {


    LoginModel lm = new LoginModel();

    public RelativeLayout login_view;
    public RelativeLayout login_view_2;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   //Load the parent activity and any saved instances if any are saved
        super.onCreate(savedInstanceState);
        //Load layout
        setContentView(R.layout.activity_main);

        login_view  = (RelativeLayout) findViewById(R.id.main_layout);
        login_view_2 = (RelativeLayout) findViewById(R.id.register_layout);



        //start
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        lm.LoginBackgroundChange(event, login_view, login_view_2);

        return super.onTouchEvent(event);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
