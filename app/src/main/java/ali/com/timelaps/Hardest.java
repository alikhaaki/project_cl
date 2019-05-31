package ali.com.timelaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.AbsoluteLayout;
import android.widget.Button;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Hardest extends AppCompatActivity {

    private Button button;
    private final String TAG = Hardest.this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardest);
        DisplayMetrics displayMetrics = new DisplayMetrics();

        button = findViewById(R.id.button_hardessttttt);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        Log.i(TAG, "width safhe = "+width);
        Log.i(TAG, "height safhe = "+height);

        DisplayMetrics displayMetrics1 = this.getResources().getDisplayMetrics();

        int dispHH = (int) (displayMetrics1.heightPixels / displayMetrics1.density);
        int dispWW = (int) (displayMetrics1.widthPixels / displayMetrics1.density);

        Log.i(TAG, "dispHH = " +dispHH);
        Log.i(TAG, "dispWW = " +dispWW);

        Random random = new Random();

        AbsoluteLayout.LayoutParams position = (AbsoluteLayout.LayoutParams) button.getLayoutParams();

        Log.i(TAG, "position = " +position);

        int pwidt = position.x = (random.nextInt(dispHH))-8;
        int phegh = position.y = (random.nextInt(dispWW))-8;

        Log.i(TAG, "position widtd = "+pwidt);
        Log.i(TAG, "position phegh = " +phegh);


        button.setLayoutParams(position);
    }
}
