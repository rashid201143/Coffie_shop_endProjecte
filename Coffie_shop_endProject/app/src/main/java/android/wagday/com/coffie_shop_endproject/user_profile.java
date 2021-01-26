package android.wagday.com.coffie_shop_endproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class user_profile extends AppCompatActivity {
  ImageView img_users , camera , galry;
  Animation flat;
    LinearLayout select_galry,select_camara;

    private static final int CAMERA_REQUEST = 1888;
    private static final int fallary_REQUEST = 1888;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        img_users = (ImageView)findViewById(R.id.img_acc);
        camera = (ImageView)findViewById(R.id.btn_camera);
        galry = (ImageView)findViewById(R.id.btn_gallry);
        select_galry = (LinearLayout)findViewById(R.id.len_galyer);
        select_camara = (LinearLayout)findViewById(R.id.len_camera);
        flat = AnimationUtils.loadAnimation(this,R.anim.anmi);

        select_camara.setAlpha(0);
        select_galry.setAlpha(0);

        galry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1234);
                select_galry.setAlpha(0);
                select_galry.startAnimation(flat);
                select_camara.setAlpha(0);
                select_camara.startAnimation(flat);


            }
        });

        //  عند الظغط على الكيمره
        camera.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {    // التحقق من البرميشن
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    // فتح الكيمره للتصوير
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    // اخفاء الزرين
                    select_galry.setAlpha(0);
                    select_galry.startAnimation(flat);
                    select_camara.setAlpha(0);
                    select_camara.startAnimation(flat);
                }
            }
        });


        //  عند الضغط على البروفايل يطلع لك االايقونات كيمره ام معرض
        img_users.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {

                select_galry.setAlpha(1);
                select_galry.startAnimation(flat);

                select_camara.setAlpha(1);
                select_camara.startAnimation(flat);
             //   galry.setAlpha(1);
             //   galry.startAnimation(flat);


            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // اخ الصوره من الكيمرا للمستعرض الصور
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img_users.setImageBitmap(photo);
        }
        //  اذا ضغط مستعرض الصور واختار صوره ياخذه للبروفايل
        else if (requestCode == 1234 && resultCode == Activity.RESULT_OK)
        {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                img_users.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "هناك خطاء ماا", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "لم تختار صوره ",Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "تم منح تصريح الكيمرا", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "لم يتم اعطاء تصاريح الكيمرا", Toast.LENGTH_LONG).show();
            }
        }
    }
}