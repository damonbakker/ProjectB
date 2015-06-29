package mobile_development.damon.projectb;


import android.os.CountDownTimer;
import android.util.Log;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


public class LoginActivity extends AppCompatActivity
{

    public boolean touch_state = false;
    public boolean bugfix_state = false;

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

        //workaround animation end bug
        if (!bugfix_state)
        {
                bugfix_state = true;

                new CountDownTimer(700, 1000)
                {

                    public void onTick(long millisUntilFinished)
                    {

                    }

                    public void onFinish()
                    {
                        bugfix_state = false;
                        Log.i("test","COUNTDOWN KLAAR");
                        touch_state = false;
                    }
                }.start();
        }

        if (!touch_state)
        {
            touch_state = true;
            lm.LoginBackgroundChange(event,login_view,login_view_2);
            Log.i("Touch event","HAS started");
        }


        return super.onTouchEvent(event);

    }

}
