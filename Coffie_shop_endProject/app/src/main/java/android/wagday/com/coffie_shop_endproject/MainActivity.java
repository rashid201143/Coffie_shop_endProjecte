package android.wagday.com.coffie_shop_endproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.wagday.com.coffie_shop_endproject.model.MySQLiteOpenHelper;
import android.wagday.com.coffie_shop_endproject.model.Users;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button log_btn;
    TextView txt_create;
    EditText userName,pass;
    public static Users users;
    ProgressDialog pro;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log_btn = findViewById(R.id.loin_btn);
        txt_create = findViewById(R.id.creat_lbl);
        final MySQLiteOpenHelper sql=new MySQLiteOpenHelper(this);
        userName=findViewById(R.id.txtUsername);
        pass=findViewById(R.id.pass);
        pro=new ProgressDialog(MainActivity.this);
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!userName.getText().toString().equals("")&&!pass.getText().toString().equals("")) {
               //     DataBes d = new DataBes(sql);
                    login_fu();
                  //  users=d.p(pass.getText().toString(), userName.getText().toString());
                  //  if (users!=null) {
                   //     Toast.makeText(MainActivity.this, "مرحبا بك "+users.getFirst_name()+" "+users.getLast_name(), Toast.LENGTH_SHORT).show();
                   //     Intent intent = new Intent(MainActivity.this, home_type_pro.class);
                   //     startActivity(intent);
                   //     finish();
                    //     }//else {
                  //      Toast.makeText(MainActivity.this, "اسم المستخدم او كلمةالمرور غير صحيحة", Toast.LENGTH_LONG).show();
                   //     userName.requestFocus();
                  //  }

                }else {
                    Toast.makeText(MainActivity.this, "ادخل اسم المستخدم و كلمة المرور", Toast.LENGTH_LONG).show();}
                  Intent intent = new Intent(MainActivity.this, home_type_pro.class);
                   startActivity(intent);
                    finish();
              //  }
             //  Intent intent = new Intent(MainActivity.this,home_type_pro.class);
             //   startActivity(intent);
            }
        });




        txt_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this,create_account.class);
                startActivity(intent);
            }
        });

    }
    private void loginFromWebServiceAPI() {
        String url="https://investigatory-boile.000webhostapp.com/log.php";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pro.dismiss();
                System.out.println("_________________DONE____________________________"+response.toString());

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("sucess");
                    JSONArray jsonArray=jsonObject.getJSONArray("user_data");
                    if (success.equals("1"))
                    {
                        JSONObject object=jsonArray.getJSONObject(0);
                        Users u =new Users();
                        u.setId(Integer.parseInt(object.getString("id")));
                        u.setFirst_name(object.getString("fname"));
//                        u.setU_email(object.getString("u_email"));
                        u.setLast_name(object.getString("lname"));
                        u.setPhone(object.getString("phone"));
                        u.setEmail(object.getString("email"));
//                        u.setU_notes(object.getString("u_notes"));
                        u.setPassword(object.getString("pass"));
                        u.setNameLog(object.getString("user"));
                        u.setImges(object.getString("img"));
                        u.setBloack_full(object.getString("b_full"));
                        u.setBlock_comment(object.getString("b_com"));
                        u.setBlock_like(object.getString("b_like"));


                      if(u.getBloack_full().toString()=="1")
                      {
                          Toast.makeText(MainActivity.this,"تم حظرك ي حبيبي",Toast.LENGTH_LONG).show();
                      }
                      else {
                          Toast.makeText(MainActivity.this, "تمت عملية التسجيل بنجاح", Toast.LENGTH_LONG).show();

                          Intent intent = new Intent(MainActivity.this, home_type_pro.class);
                          startActivity(intent);
                          finish();
                      }
                    }
                    else if (success.equals("0"))
                    {
                        Toast.makeText(MainActivity.this,"يرجى التأكد من البيانات المدخلة...",Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parems=new HashMap<String, String>();
                parems.put("username", userName.getText().toString());
                parems.put("u_pass", pass.getText().toString());
                parems.put("op","login");


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


    }
    private void login_fu() {

        if (IsConnectedInternet())
        {pro.setTitle("جاري عملية التسجيل...");
            pro.show();
            loginFromWebServiceAPI();
        }
        else {
            Toast.makeText(MainActivity.this,"يرجى التاكد من الاتصال بالانترنت",Toast.LENGTH_LONG).show();
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
}