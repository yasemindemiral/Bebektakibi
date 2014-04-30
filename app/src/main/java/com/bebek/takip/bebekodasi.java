package com.bebek.takip;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;
import com.bebek.takip.GMailSender;

/**
 * Created by yasemin on 4/27/14.
 */
public class bebekodasi extends Activity {
   public Button buttontamam;
    public EditText editeposta;
    int str, rNumber;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebekodasi);
        editeposta = (EditText) findViewById(R.id.editEposta);

        buttontamam = (Button) findViewById(R.id.buttonTamam);
        buttontamam.setOnClickListener(new View.OnClickListener() {
            @Override
          public void onClick(View v) {

                final Random numGen = new Random(); //Random sayı oluşturmak için olan kısım
                rNumber = numGen.nextInt(1000);
                //editeposta.setText(String.valueOf(rNumber));
                //str=rNumber;
                try {
                    GMailSender sender = new GMailSender("applockermail@gmail.com" , "app192837");
                    sender.sendMail("konu",Integer.toString(rNumber),"applockergmail.com", String.valueOf(editeposta.getText()));

                }
                catch (Exception e) {
                    Log.e("SendMail", e.getMessage());
                }

            }
      });
    }
}
