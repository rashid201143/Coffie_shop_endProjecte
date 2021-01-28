package android.wagday.com.coffie_shop_endproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.wagday.com.coffie_shop_endproject.model.Product;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class home_type_pro extends AppCompatActivity {
  ImageView img_accou;
  TextView name_acc;
    ProgressDialog pro;
    CardView jes_tab,Coffee,IceCream,Sweets,Yamen;
 // public static   ArrayList<Product> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_type_pro);
        img_accou = (ImageView)findViewById(R.id.img_acc);
        name_acc=findViewById(R.id.name_acc);
        Yamen=findViewById(R.id.Yamen);
        pro=new ProgressDialog(this);
        jes_tab=findViewById(R.id.jes_tab);
        Coffee=findViewById(R.id.Coffee);
        IceCream=findViewById(R.id.IceCream);
        Sweets=findViewById(R.id.Sweets);
        if(!MainActivity.users.getImges().equals("nu"))
        {
            Bitmap bt = ConvertBase64Toimages(MainActivity.users.getImges());
            img_accou.setImageBitmap(bt);
        }
        Yamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_type_pro.this,all_prodact_list.class);
             //   list= loginFromWebServiceAPI(5);
                intent.putExtra("id",5);
                startActivity(intent);
            }
        });
        jes_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_type_pro.this,all_prodact_list.class);
                intent.putExtra("id",2);
                startActivity(intent);
            }
        });
        Coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_type_pro.this,all_prodact_list.class);
                intent.putExtra("id",1);
                startActivity(intent);
            }
        });
        IceCream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_type_pro.this,all_prodact_list.class);
                intent.putExtra("id",3);
                startActivity(intent);
            }
        });
        Sweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_type_pro.this,all_prodact_list.class);
                intent.putExtra("id",4);
                startActivity(intent);
            }
        });
        name_acc.setText(MainActivity.users.getFirst_name()+"  "+MainActivity.users.getLast_name());
        img_accou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_type_pro.this,user_profile.class);
                startActivity(intent);
            }
        });

    }
    private ArrayList<Product> loginFromWebServiceAPI(int id) {
        ArrayList<Product>list1=new ArrayList<>();
        pro.setTitle("جاري عملية التسجيل...");
        pro.show();
        String url="https://investigatory-boile.000webhostapp.com/log.php";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pro.dismiss();
                System.out.println("_________________DONE____________________________"+response.toString());

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("GetAllPro");
                    JSONArray jsonArray=jsonObject.getJSONArray("Product");
                    if (success.equals("1"))
                    {

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            Product p=new Product();
                            p.setId(Integer.parseInt(object.getString("id")));
                            p.setName(object.getString("Name"));
                            p.setPrice(Integer.parseInt(object.getString("price")));
                            p.setDep_id(Integer.parseInt(object.getString("Dep_id")));
                            p.setImg1(object.getString("Img1"));
                            p.setImg2(object.getString("Img2"));
                            p.setImg3(object.getString("Img3"));
                            p.setOther_details(object.getString("Other_detilis"));

                            list1.add(p);
                        }
                        // list_prodact.notifySubtreeAccessibilityStateChanged();
                       // list_prodact.deferNotifyDataSetChanged();
                        System.out.println("_________________33333333_______________________");
                    }
                    else if (success.equals("0"))
                    {
                        Toast.makeText(home_type_pro.this,"يرجى التأكد من البيانات المدخلة...",Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("_________________error_______________________"+error.getMessage());
            }


        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parems=new HashMap<String, String>();
                // parems.put("username", userName.getText().toString());
                parems.put("Dep_id", String.valueOf(id));
                parems.put("op","GetAllProduct");

                System.out.println("_________________2222222222_______________________");
                return parems;
            }
        };

        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

        return list1;
    }
    // تحويل النص الى صوره
    public Bitmap ConvertBase64Toimages(String img){

        byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        System.out.println("The size of bitmap image agter low converting is ::::::::::::::::::::::::::::::::::::::::"+decodedByte.getByteCount());

        return decodedByte;
    }
}