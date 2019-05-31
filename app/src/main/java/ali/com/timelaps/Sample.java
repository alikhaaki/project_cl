package ali.com.timelaps;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

public class Sample extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check);

        View checkBoxView = View.inflate(this, R.layout.dialog_check, null);
        CheckBox checkBox =  checkBoxView.findViewById(R.id.check_box);

        checkBox.setText("Text to the right of the check box.");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" MY_TEXT");
        builder.setMessage(" MY_TEXT ")
                .setView(checkBoxView)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Uri uri = Uri.parse("market://details?id=MY_APP_PACKAGE");
                        Intent intent = new Intent (Intent.ACTION_VIEW, uri);
                        startActivity(intent);                          }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }


}