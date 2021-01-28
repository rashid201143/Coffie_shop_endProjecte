package android.wagday.com.coffie_shop_endproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.wagday.com.coffie_shop_endproject.model.Product;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import de.hdodenhof.circleimageview.CircleImageView;

public class all_prodact_list extends AppCompatActivity {
    ListView list_prodact;
   public ArrayList<Product> list=new ArrayList<>();
    int Dep_id;adabter aaa;
    ProgressDialog pro;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pro=new ProgressDialog(all_prodact_list.this);
        Intent intent = getIntent();
        Dep_id= intent.getIntExtra("id",1);
        GetAllDepartment(Dep_id);
        setContentView(R.layout.activity_all_prodact_list);
        list_prodact=findViewById(R.id.list_prodact);
         aaa=new adabter(this,list);
        list_prodact.setAdapter(aaa);
        list_prodact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    private ArrayList<Product> loginFromWebServiceAPI(int id) {
        ArrayList<Product>list1=new ArrayList<>();
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

                          list.add(p);
                        }
                        aaa.notifyDataSetChanged();
                        System.out.println("_________________33333333_______________________");
                    }
                    else if (success.equals("0"))
                    {
                        Toast.makeText(all_prodact_list.this,"يرجى التأكد من البيانات المدخلة...",Toast.LENGTH_LONG).show();

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
    private ArrayList<Product> GetAllDepartment(int id) {
        ArrayList<Product>list1=new ArrayList<>();
        if (IsConnectedInternet())
        {pro.setTitle("جاري عملية التسجيل...");
            pro.show();
         list1=   loginFromWebServiceAPI(id);
        }
        else {
            Toast.makeText(all_prodact_list.this,"يرجى التاكد من الاتصال بالانترنت",Toast.LENGTH_LONG).show();
        }
return list1;
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
    class adabter extends ArrayAdapter {
        ArrayList<Product> list;
        Context context;
        public   adabter (Context context,ArrayList<Product> list){
            super(context,R.layout.prodact_item,list);
            System.out.println("_________________44444444_______________________");
            this.context=context;
            this.list=list;
        }
        @Override
        public int getCount() {
            return list.size();
           // return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint({"SetTextI18n", "ViewHolder"})
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
          //  View view1=view;
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.prodact_item,viewGroup,false);
          //  if(view1==null){
        //        view1= LayoutInflater.from(context).inflate(R.layout.prodact_item,null,false);
        //    }
            System.out.println("_________________5555555555_______________________");
            CircleImageView img=view.findViewById(R.id.img_pro);
            TextView name=view.findViewById(R.id.name);
            //TextView count=findViewById(R.id.count);
            TextView price=view.findViewById(R.id.price);
            img.setImageURI(Uri.parse(list.get(i).getImg1()));
            name.setText(list.get(i).getName());
            price.setText(list.get(i).getPrice()+"");
            return view;
        }

    }
}