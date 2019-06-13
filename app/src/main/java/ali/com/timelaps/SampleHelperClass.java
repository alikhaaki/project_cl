package ali.com.timelaps;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class SampleHelperClass extends AppCompatActivity {


    public static final String TAG_MAIN_ACTIVITY = "main_activity";
    public static final String TAG_HARD1 = "hard_1";
    public static final String TAG_HARDER = "harder";
    public static final String TAG_HARDEST = "hardest";
    public static final String ID_PUBLIC = "idpublic";


     private Dialog dialog;
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

    private AlertDialog alertDialog;
    private TextView textYourScore;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent();
        String sssssss=intent.getStringExtra(SampleHelperClass.ID_PUBLIC);

        switch (sssssss){
            case SampleHelperClass.TAG_MAIN_ACTIVITY:
                //dddd
                break;
            case SampleHelperClass.TAG_HARD1:
                //dldldl
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.go_to_hard_1_menu:
                textYourScore.setText("dlfkjdkl");
                finish();
                startActivity(new Intent(this, HardActivity.class));
                break;
            case R.id.go_to_hard_2_menu:
                textYourScore.setText("dlfkjdkl");
                finish();
                startActivity(new Intent(this, HardActivity.class));
                break;
            case R.id.go_to_hardest_gaem:
                textYourScore.setText("dlfkjdkl");
                finish();
                startActivity(new Intent(this, HardActivity.class));
                break;


        }
        return true;

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check);

        Intent intent=new Intent(this, HardActivity.class);
        intent.putExtra("dlfdl", "dlfjld");
        startActivity(intent);

        textYourScore.setText(SampleHelperClass.TAG_MAIN_ACTIVITY);

          intent = new Intent();
        String idddd = intent.getStringExtra(SampleHelperClass.ID_PUBLIC);

        switch (idddd) {
            case SampleHelperClass.TAG_MAIN_ACTIVITY:
                //dddddd
                break;
            case SampleHelperClass.TAG_HARD1:
                //ddddd
                break;

        }


        startActivity(new Intent(this, MainActivity.class));
        Button button = new Button(this);

        dialog = new Dialog(SampleHelperClass.this);
        dialog.setContentView(R.layout.newcustom_layout_dialog);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(dialog.getWindow().getAttributes());
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimary);
        FrameLayout frameLayout = dialog.findViewById(R.id.frmOk);
        if (!SampleHelperClass.this.isFinishing()) {
            //show dialog
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();


        dialogBuilder = new AlertDialog.Builder(this);
        alertDialog = new AlertDialog.Builder(this)
                .setView(getLayoutInflater().inflate(R.layout.newcustom_layout_dialog, null))
                .create();

        View view = getLayoutInflater().inflate(R.layout.newcustom_layout_dialog, null);
        FrameLayout btn = (FrameLayout) view.findViewById(R.id.frmOk);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        alertDialog
                .setView(view);
        dialog.show();

        final AlertDialog alertD = new AlertDialog.Builder(this).create();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.cancel();
            }
        });

        LayoutInflater inflater = this.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.newcustom_layout_dialog, null);
        dialogBuilder.setView(dialogView);

        ViewDialog alert = new ViewDialog();
        alert.showDialog(SampleHelperClass.this);
        dialog.dismiss();
    }

    class RandomColors {
        private Stack<Integer> recycle, colors;

        public RandomColors() {
            colors = new Stack<>();
            recycle = new Stack<>();
            recycle.addAll(Arrays.asList(
                    0xfff44336, 0xffe91e63, 0xff9c27b0, 0xff673ab7,
                    0xff3f51b5, 0xff2196f3, 0xff03a9f4, 0xff00bcd4,
                    0xff009688, 0xff4caf50, 0xff8bc34a, 0xffcddc39,
                    0xffffeb3b, 0xffffc107, 0xffff9800, 0xffff5722,
                    0xff795548, 0xff9e9e9e, 0xff607d8b, 0xff333333
                    )
            );
        }

        public int getColor() {
            if (colors.size() == 0) {
                while (!recycle.isEmpty())
                    colors.push(recycle.pop());
                Collections.shuffle(colors);
            }
            Integer c = colors.pop();
            recycle.push(c);
            return c;
        }
    }


    public class ViewDialog {

        public void showDialog(Activity activity) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.newcustom_layout_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


            FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);
            mDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Okay", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });

            dialog.show();
        }
    }

}
