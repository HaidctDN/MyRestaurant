package classadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.phuchai.myrestaurant.R;

import java.util.List;

import myclass.ChiTietHoaDon;

public class DetailOrderAdapter extends ArrayAdapter<ChiTietHoaDon> {
    private List<ChiTietHoaDon> listData;
    private int resource;
    private Context context;

    public DetailOrderAdapter(Context context, int resource,
                              List<ChiTietHoaDon> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listData = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.activity_item_detail_order, parent, false);
            viewHolder.stt = (TextView) convertView.findViewById(R.id.stt);
            viewHolder.tenmon = (TextView) convertView
                    .findViewById(R.id.tenmon);
            viewHolder.sl = (TextView) convertView.findViewById(R.id.sl);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ChiTietHoaDon cthd = listData.get(position);
        viewHolder.stt.setText("" + (position + 1));
        viewHolder.tenmon.setText(cthd.getTenma());
        viewHolder.sl.setText("" + cthd.getSoluong());

        return convertView;
    }

    public class ViewHolder {
        TextView stt, tenmon, sl;
    }
}
