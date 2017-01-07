package classadapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phuchai.myrestaurant.MenuFood;
import com.example.phuchai.myrestaurant.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import myclass.Food;

/**
 * Created by PhucHai on 12/14/2016.
 */

public class FoodAdapter extends ArrayAdapter<Food> {
    private List<Food> listData;
    private int resource;//chua biet dung de lam gi
    private Context context;

    public FoodAdapter(Context context, int resource, List<Food> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listData = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.activity_item_food, parent, false);
            viewHolder.imgFood = (ImageView) convertView
                    .findViewById(R.id.imageView_foodImg);
            viewHolder.chFood = (CheckBox) convertView
                    .findViewById(R.id.checkBox_foodName);
            viewHolder.editSoluong = (EditText) convertView
                    .findViewById(R.id.editText_quanitity);
            viewHolder.btUp = (Button) convertView.findViewById(R.id.buttonUp);
            viewHolder.btDown = (Button) convertView
                    .findViewById(R.id.buttonDown);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Food food = listData.get(position);
        //----
        viewHolder.chFood.setText(food.getNameFood());
        Picasso.with(context).load(food.getLinkImg()).into(viewHolder.imgFood);
        //tao su kien cho checkbox
        viewHolder.chFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.chFood.isChecked()) {
                    viewHolder.editSoluong.setEnabled(true);
                    viewHolder.editSoluong.setText("1");
                    viewHolder.btUp.setEnabled(true);
                    viewHolder.btDown.setEnabled(true);
                    MenuFood.soluong[position] = 1;
                } else {
                    viewHolder.editSoluong.setEnabled(false);
                    viewHolder.editSoluong.setText(null);
                    viewHolder.btUp.setEnabled(false);
                    viewHolder.btDown.setEnabled(false);
                    MenuFood.soluong[position] = 0;
                }
            }
        });
        //tao su kien cho button
        viewHolder.btUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soluong = viewHolder.editSoluong.getText().toString().trim();
                int sl = (Integer.parseInt(soluong) + 1);
                viewHolder.editSoluong.setText("" + sl);
                MenuFood.soluong[position] = sl;
            }
        });
        viewHolder.btDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soluong = viewHolder.editSoluong.getText().toString().trim();
                int sl = Integer.parseInt(soluong);
                if (sl > 1) {
                    sl = sl - 1;
                    viewHolder.editSoluong.setText("" + sl);
                    MenuFood.soluong[position] = sl;
                }
            }
        });
        //tao su kien cho edittext
        viewHolder.editSoluong.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String soluong = viewHolder.editSoluong.getText().toString()
                        .trim();
                if (TextUtils.isEmpty(soluong) == false) {
                    MenuFood.soluong[position] = Integer.parseInt(soluong);
                } else {
                    viewHolder.chFood.setChecked(false);
                    viewHolder.btUp.setEnabled(false);
                    viewHolder.btDown.setEnabled(false);
                    viewHolder.editSoluong.setEnabled(false);
                }
                return false;
            }
        });
        return convertView;
    }

    public class ViewHolder {
        ImageView imgFood;
        CheckBox chFood;
        EditText editSoluong;
        Button btUp, btDown;
    }

}
