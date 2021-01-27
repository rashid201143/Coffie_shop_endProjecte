package android.wagday.com.coffie_shop_endproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.wagday.com.coffie_shop_endproject.model.Product;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class all_prodact_list extends AppCompatActivity {
    ListView list_prodact;
    ArrayList<Product> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_prodact_list);
        list_prodact=findViewById(R.id.list_prodact);

        list_prodact.setAdapter(new adabter(this,list));
    }
    class adabter extends BaseAdapter {
        ArrayList<Product> list;
        Context context;
        public   adabter (Context context,ArrayList<Product> list){
            this.context=context;
            this.list=list;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1=view;
            if(view1==null){
                view1= LayoutInflater.from(context).inflate(R.layout.prodact_item,null,false);
            }
            CircleImageView img=findViewById(R.id.img_pro);
            TextView name=findViewById(R.id.name);
            //TextView count=findViewById(R.id.count);
            TextView price=findViewById(R.id.price);
            img.setImageURI(Uri.parse(list.get(i).getImage()));
            name.setText(list.get(i).getName());
            price.setText(list.get(i).getPrice()+"");
            return view1;
        }
    }
}