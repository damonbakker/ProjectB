package mobile_development.damon.projectb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class RegisterActivity extends Activity {

    public boolean touch_state = false;
    public boolean bugfix_state = false;
    public String background_color;

    LoginModel lm = new LoginModel();

    public RelativeLayout register_view;
    public RelativeLayout register_view_2;
    public RelativeLayout login_view;

    public void returnTo_sender()
    {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.putExtra("background_color", background_color);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_from_left, R.anim.push_to_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_view  = (RelativeLayout) findViewById(R.id.register_layout);
        register_view_2 = (RelativeLayout) findViewById(R.id.underflow_layout);
        login_view = (RelativeLayout) findViewById(R.id.main_layout);

        String new_background_color =getIntent().getStringExtra("background_color");
        register_view.setBackgroundColor(Color.parseColor(new_background_color));
        background_color = new_background_color;

        Button button_login = (Button) findViewById(R.id.button_register);
        button_login.setOnLongClickListener(onLongClickListener);


        Log.i("1232131",background_color);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        returnTo_sender();

    }

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v)
        {

            returnTo_sender();
            return false;
        }
    };

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
                    Log.i("test", "COUNTDOWN KLAAR");
                    touch_state = false;
                }
            }.start();
        }

        if (!touch_state)
        {
            touch_state = true;
            lm.LoginBackgroundChange(event,register_view,register_view_2);
            background_color = lm.current_background_color;


        }


        return super.onTouchEvent(event);

    }
}
