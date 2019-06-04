package ali.com.timelaps;

import Fragment.FragmentOne;
import Fragment.FragmentThree;
import Fragment.FragmentTwo;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import com.github.paolorotolo.appintro.AppIntro;

public class IntroActivity extends AppIntro {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new FragmentOne());
        addSlide(new FragmentTwo());
        addSlide(new FragmentThree());

        showSkipButton(true);
        setProgressButtonEnabled(true);

//        setSkipTextTypeface(R.font.iransans);

        setColorSkipButton(ContextCompat.getColor(this, R.color.black));
        setSkipText("رد شدن");
        setDoneText("باشه !");
        setBarColor(ContextCompat.getColor(this, R.color.grey_100));
        setColorDoneText(ContextCompat.getColor(this,R.color.black));
    }
    @Override
    public void onBackPressed(){
    }
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}
