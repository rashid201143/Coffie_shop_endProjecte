package android.wagday.com.coffie_shop_endproject.model;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.wagday.com.coffie_shop_endproject.R;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProdactAdapter extends ArrayAdapter {
    Context context;int resource;
    ArrayList<Product> list;
    public ProdactAdapter(@NonNull Context context, int resource, ArrayList<Product> list) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
        this.list=list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.prodact_item,parent,false);
        System.out.println("_________________5555555555_______________________");
        CircleImageView img=convertView.findViewById(R.id.img_pro);
        TextView name=convertView.findViewById(R.id.name);
        //TextView count=findViewById(R.id.count);
        TextView price=convertView.findViewById(R.id.price);
           img.setImageURI(Uri.parse(list.get(position).getImg1()));
        name.setText(list.get(position).getName());
        price.setText(list.get(position).getPrice()+"");
        return super.getView(position, convertView, parent);
    }
}
