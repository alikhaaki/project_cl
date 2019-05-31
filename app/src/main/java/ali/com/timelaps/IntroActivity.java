package ali.com.timelaps;

import Fragment.FragmentOne;
import Fragment.FragmentThree;
import Fragment.FragmentTwo;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    }
}
