package android.wagday.com.coffie_shop_endproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class user_profile extends AppCompatActivity {
  ImageView img_users , camera , galry;
  Animation flat;
    ProgressDialog pro;
    LinearLayout select_galry,select_camara;
    Button btn_save;
    int img_count=0;
TextView name_acc ,email_title,name_acc1 ,email_title1,Phone,userName;
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
        pro=new ProgressDialog(user_profile.this);
        btn_save = (Button)findViewById(R.id.save_btn);
        select_galry = (LinearLayout)findViewById(R.id.len_galyer);
        select_camara = (LinearLayout)findViewById(R.id.len_camera);
        flat = AnimationUtils.loadAnimation(this,R.anim.anmi);
        name_acc=findViewById(R.id.name_acc);
        name_acc.setText(MainActivity.users.getFirst_name());
        email_title=findViewById(R.id.email_title);
        email_title.setText(MainActivity.users.getEmail());
        name_acc1=findViewById(R.id.name_acc1);
        name_acc1.setText(MainActivity.users.getFirst_name()+" "+MainActivity.users.getLast_name());
        email_title1=findViewById(R.id.email_title1);
        email_title1.setText(MainActivity.users.getEmail());
        Phone=findViewById(R.id.Phone);
        Phone.setText(MainActivity.users.getPhone());
        userName=findViewById(R.id.userName);
        userName.setText(MainActivity.users.getNameLog());
//MainActivity.users.setPhone();
        select_camara.setAlpha(0);
        select_galry.setAlpha(0);
        if(!MainActivity.users.getImges().equals("nu"))
        {
            Bitmap bt = ConvertBase64Toimages(MainActivity.users.getImges());
            img_users.setImageBitmap(bt);
        }
        // عند الظغط على زر حفظ
        btn_save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(img_count>0)
                {add_imges();}

                else
                {
                    // هنا تحيث باقي المعلومات
                }
            }
        });
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
    // داله تحويل الصوره الى نص
    public String ConvertImageToBase64(Bitmap bitmap){
        String img_str = "";
        try{
            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
            byte[] image_bytes=stream.toByteArray();

            img_str = Base64.encodeToString(image_bytes, Base64.DEFAULT);
        }catch (Exception e){
            System.out.println("The exception from Converting is :::"+e.getMessage());
        }

        return img_str;
    }

    // تحويل النص الى صوره
    public Bitmap ConvertBase64Toimages(String img){

        byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        System.out.println("The size of bitmap image agter low converting is ::::::::::::::::::::::::::::::::::::::::"+decodedByte.getByteCount());

        return decodedByte;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    void  add_imges()
    {
        if (IsConnectedInternet())
        {
            sendDataToWebServiceADI_Register();
        }
        else {
            // Toast.makeText(create_account.this,"يرجى التاكد من الاتصال بالانترنت",Toast.LENGTH_LONG).show();
            StyleableToast.makeText(user_profile.this, "يرجى التاكد من الاتصال بالانترنت",  R.style.Eror_net).show();


            //  StyleableToast.makeText(create_account.this, "Hello World!", Toast.LENGTH_LONG, R.style.Eror_net).show();
        }
    }
    public boolean IsConnectedInternet(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            return connected;
        }
        else{
            connected = false;
            return connected;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendDataToWebServiceADI_Register() {
        pro.setTitle("جاري عملية تغير الصوره");
        pro.show();
        //  String url="https://712051643.000webhostapp.com/connection.php";  https://investigatory-boile.000webhostapp.com/log.php
        String url="https://investigatory-boile.000webhostapp.com/log.php";
        // System.out.println("1111111111111111111111111111111111111111111111");


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pro.dismiss();

                System.out.println("_________________IN REspons_______________________"+response);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);

                    JSONArray result = jsonObject.getJSONArray("result");

                    if (result.getString(0).equals("1"))
                    {
                        //  StyleableToast.makeText(create_account.this,result.getString(1), Toast.LENGTH_LONG, R.style.Eror_net).show();
                        Toast.makeText(user_profile.this,result.getString(1),Toast.LENGTH_LONG).show();
                     //   Intent intent=new Intent(user_profile.this,MainActivity.class);
                       // startActivity(intent);

                    }
                    else {
                        // StyleableToast.makeText(create_account.this,result.getString(1), Toast.LENGTH_LONG, R.style.Eror_connection).show();

                        Toast.makeText(user_profile.this,result.getString(1),Toast.LENGTH_LONG).show();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }  }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pro.dismiss();
                Toast.makeText(user_profile.this,"eroor respones",Toast.LENGTH_LONG).show();

                System.out.println("_______________________ERROR______________________"+error);
            }


        }
        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parems=new HashMap<String, String>();
                Bitmap bitm = ((BitmapDrawable) img_users.getDrawable()).getBitmap();
                String imgSend = ConvertImageToBase64(bitm);

                parems.put("u_imge", imgSend);
                parems.put("u_id", String.valueOf(MainActivity.users.getId()));

                parems.put("op","img_pro");


                return parems;
            }
        };

        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 30000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 30000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });


        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // اخ الصوره من الكيمرا للمستعرض الصور
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            img_count++;
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
                img_count++;
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