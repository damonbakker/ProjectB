package mobile_development.damon.projectb;

import android.animation.Animator;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Damon on 6/24/2015.
 */


public class LoginModel extends AppCompatActivity
{

    public int animation_duration = 700;
    public int counter = 0;
    public boolean animation_state = false;
    public boolean countdown_state = false;
    public List listPalette = new ArrayList<String>();
    public boolean Generator_initialised = false;




    public String Color_Generator(){

        if (!Generator_initialised)
        {

            listPalette.add(0,"#25ae90");
            listPalette.add(1,"#3267B1");
            listPalette.add(2,"#84d9d2");
            listPalette.add(3,"#f1924e");
            listPalette.add(4,"#13383e");
            listPalette.add(5,"#3f51b5");
            listPalette.add(6,"#673ab7");
            listPalette.add(7,"#AC193D");
            listPalette.add(8,"#3267B1");
            listPalette.add(9,"#094AB2");





        }

        Generator_initialised = true;

        int random_index = new Random().nextInt(arrayPalette.length);


        return arrayPalette[random_index];
    }





    //Animations

    public void LoginBackgroundChange(MotionEvent event,RelativeLayout mainlayout1,RelativeLayout registerlayout1)
    {
        final RelativeLayout mainlayout = mainlayout1;
        final RelativeLayout registerlayout = registerlayout1;



        if (!animation_state)
        {
            animation_state = true;
            //animation bug workaround, equals duration of animation
            if (!countdown_state)
            {
                countdown_state = true;
                new CountDownTimer(600, 1000)
                {
                    public void onTick(long millisUntilFinished)
                    {

                    }

                    public void onFinish()
                    {
                        countdown_state = false;
                        animation_state = false;
                        Log.i("sate","COUNTDOWN KLAAR");


                    }
                }.start();
            }


            //get pressed location(coordinates)
            int x = (int)event.getX();
            int y = (int)event.getY();

            //set backgroundcolor
           String newcolor = Color_Generator();

            registerlayout.setBackgroundColor(Color.parseColor("#AC193D"));

            //get the entire field radius of the view
            int finalradius = Math.max(mainlayout.getWidth(), mainlayout.getHeight());




            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    //pressed

                    //create reveal animation with parameters
                    Animator anim= ViewAnimationUtils.createCircularReveal(registerlayout, x, y, 0, finalradius);
                    anim.setDuration(animation_duration);



                    //listen to the animation
                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation)
                        {
                            //animation is started
                            registerlayout.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            //animation has ended
                            mainlayout.setBackgroundColor(Color.MAGENTA);
                            counter++;



                        }

                        @Override
                        public void onAnimationCancel(Animator animation)
                        {
                            //animation is cancelled
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation)
                        {
                            //animation has repeated
                        }
                    });

                    if (counter == 1)
                    {
                        String malin = Color_Generator();
                        Log.i("NEKEN",malin);
                        registerlayout.setVisibility(View.INVISIBLE);
                        registerlayout.setBackgroundColor(Color.YELLOW);
                    }

                    //start animation
                    anim.start();

                    break; //end switch ACTION_DOWN

                case MotionEvent.ACTION_UP:
                    //released
                    break; //end switch ACTION_UP
            }
        }
    }




}
