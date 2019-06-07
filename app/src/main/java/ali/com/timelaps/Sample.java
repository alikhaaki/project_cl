package ali.com.timelaps;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class Sample extends AppCompatActivity {


     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check);

         Button button=new Button(this);
         Random random=new Random();
         int color= Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

         RandomColors randomColors=new RandomColors();
         int clclcl=randomColors.getColor();
         Drawable drawable=button.getBackground();
         DrawableCompat.setTint(drawable, color);

         CoordinatorLayout coordinatorLayout=findViewById(R.id.viewSnack);
         Snackbar snackbar=Snackbar.make(coordinatorLayout, "text", Snackbar.LENGTH_LONG);

         snackbar.show();

//          Snackbar.make(coordinatorLayout, "your text", Snackbar.LENGTH_SHORT)
//                 .setAnchorView(yourfabView.getVisibility() == View.VISIBLE ? YourFabView : BottomAppBarView).show();

         button.setBackgroundColor(color);

    }
    class RandomColors {
        private Stack<Integer> recycle, colors;

        public RandomColors() {
            colors = new Stack<>();
            recycle =new Stack<>();
            recycle.addAll(Arrays.asList(
                    0xfff44336,0xffe91e63,0xff9c27b0,0xff673ab7,
                    0xff3f51b5,0xff2196f3,0xff03a9f4,0xff00bcd4,
                    0xff009688,0xff4caf50,0xff8bc34a,0xffcddc39,
                    0xffffeb3b,0xffffc107,0xffff9800,0xffff5722,
                    0xff795548,0xff9e9e9e,0xff607d8b,0xff333333
                    )
            );
        }

        public int getColor() {
            if (colors.size()==0) {
                while(!recycle.isEmpty())
                    colors.push(recycle.pop());
                Collections.shuffle(colors);
            }
            Integer c= colors.pop();
            recycle.push(c);
            return c;
        }
    }
}
