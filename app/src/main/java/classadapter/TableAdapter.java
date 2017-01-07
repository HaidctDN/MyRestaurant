package classadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phuchai.myrestaurant.R;

import java.util.List;

import myclass.Table;

public class TableAdapter extends BaseAdapter {
    private List<Table> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public TableAdapter(Context aContext, List<Table> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_item_table,
                    null);
            holder = new ViewHolder();
            holder.img_table = (ImageView) convertView
                    .findViewById(R.id.imageView_Table);
            holder.num_table = (TextView) convertView
                    .findViewById(R.id.textView_number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Table table = this.listData.get(position);

        holder.num_table.setText("" + table.getNumberTable());

        int imageId = this.getMipmapResIdByName(table.getColorTable());

        holder.img_table.setBackgroundResource(imageId);

        return convertView;
    }

    private int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();

        int resID = context.getResources().getIdentifier(resName, "drawable", pkgName);
        return resID;
    }

    static class ViewHolder {
        ImageView img_table;
        TextView num_table;
    }
}
