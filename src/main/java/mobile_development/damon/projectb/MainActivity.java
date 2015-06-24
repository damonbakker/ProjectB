package mobile_development.damon.projectb;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Layout;
import android.util.Log;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public int counter = 0;
    public boolean workstate= false;

    public void notification(){
        new AlertDialog.Builder(this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   /*Load the parent activity and any saved instances if any are saved*/
        super.onCreate(savedInstanceState);
        /*Load layout*/
        setContentView(R.layout.activity_main);


        /*start*/
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (workstate == false)
        {
            workstate = true;



        Log.i("touchevent", "Er is gedrukt");
        Log.i("workingstatus ? false", Boolean.toString(workstate));

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                workstate = false;
            }
        }.start();


        Log.i("workingchange", Boolean.toString(workstate));


        int x = (int)event.getX();
        int y = (int)event.getY();

        final RelativeLayout mainlayout = (RelativeLayout) findViewById(R.id.main_layout);
        final RelativeLayout registerlayout = (RelativeLayout) findViewById(R.id.register_layout);

        mainlayout.setEnabled(false);
        registerlayout.setEnabled(false);
        int finalradius = Math.max(mainlayout.getWidth(), mainlayout.getHeight());


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //pressed
                String stringx = String.valueOf(x);
                String stringy = String.valueOf(y);

                Log.i("float x =", stringx);
                Log.i("float y =", stringy);




                Animator anim= ViewAnimationUtils.createCircularReveal(registerlayout, x, y, 0, finalradius);
                anim.setDuration(700);




                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        registerlayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        Log.i("malin", "klaar");
                     /*   workstate = false;*/
                       /* notification();*/

                        mainlayout.setBackgroundColor(Color.MAGENTA);
                        counter++;
                        Log.i("complete", "true");
                        Log.i("workingreset", Boolean.toString(workstate));

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Log.i("cancel", "true");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });


                if (counter == 1){
                    registerlayout.setVisibility(View.INVISIBLE);
                    registerlayout.setBackgroundColor(Color.YELLOW);
                }



                anim.start();



                return true;

            case MotionEvent.ACTION_UP:
                //released
                return true;
        }
        }
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
