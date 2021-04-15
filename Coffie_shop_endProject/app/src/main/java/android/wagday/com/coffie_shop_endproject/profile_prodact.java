package android.wagday.com.coffie_shop_endproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class profile_prodact extends AppCompatActivity {
int id;
String name,qwe,uri;
    ImageView imgg;
    TextView name1,namee,qwee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_prodact);
        Intent intent = getIntent();
        id= intent.getIntExtra("id",1);
        name=intent.getStringExtra("name");
        qwe=intent.getStringExtra("qwe");
        uri=intent.getStringExtra("uri");
        imgg=findViewById(R.id.img_pro);
        name1=findViewById(R.id.name);
        namee=findViewById(R.id.namee);
        name1.setText(name);
        namee.setText(qwe);
        Picasso.with(this).load(uri).into(imgg);
    }
}