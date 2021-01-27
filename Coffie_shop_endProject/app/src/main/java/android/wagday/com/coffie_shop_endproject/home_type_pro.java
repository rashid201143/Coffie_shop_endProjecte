package android.wagday.com.coffie_shop_endproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class home_type_pro extends AppCompatActivity {
  ImageView img_accou;
  TextView name_acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_type_pro);
        img_accou = (ImageView)findViewById(R.id.img_acc);
        name_acc=findViewById(R.id.name_acc);
        if(!MainActivity.users.getImges().equals("nu"))
        {
            Bitmap bt = ConvertBase64Toimages(MainActivity.users.getImges());
            img_accou.setImageBitmap(bt);
        }
        name_acc.setText(MainActivity.users.getFirst_name()+"  "+MainActivity.users.getLast_name());
        img_accou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_type_pro.this,user_profile.class);
                startActivity(intent);
            }
        });

    }
    // تحويل النص الى صوره
    public Bitmap ConvertBase64Toimages(String img){

        byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        System.out.println("The size of bitmap image agter low converting is ::::::::::::::::::::::::::::::::::::::::"+decodedByte.getByteCount());

        return decodedByte;
    }
}