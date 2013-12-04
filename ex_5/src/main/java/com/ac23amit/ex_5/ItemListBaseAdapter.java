package com.ac23amit.ex_5;

/**
 * Created by win7 on 05/12/13.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by pc on 05/11/13.
 */
public class ItemListBaseAdapter extends BaseAdapter
{
    //    private static ArrayList<ItemDetails> itemDetailsrrayList;
    private Context context;
    private LayoutInflater l_Inflater;
    private Connect_DB connectorDB = null;

    public ItemListBaseAdapter (Context context)
    {
        connectorDB = Connect_DB.getInstance(context);
        l_Inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public int getCount ()
    {
        return connectorDB.getSize();
    }

    public Object getItem (int position)
    {
        return connectorDB.getElm(position);

    }

    public long getItemId (int position) { return connectorDB.getElmID(position); }

    private final View.OnClickListener doneButtonOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick (View view)
        {
            int position = (Integer) view.getTag();
            connectorDB.deleteElm(position);
            notifyDataSetChanged();
        }
    };

    public View getView (int position, View convertView, ViewGroup parent)
    {
        final int positionFinal = position;
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = l_Inflater.inflate(R.layout.item_details_view, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.name);
            holder.tvbtnText = (Button) convertView.findViewById(R.id.Done_Btn);
            holder.tvbtnText.setOnClickListener(doneButtonOnClickListener);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvbtnText.setTag(position);
        holder.tvName.setText(connectorDB.getElm(position).getName());

        holder.tvbtnText.setText(connectorDB.getElm(position).getbtnText());

        return convertView;
    }

    private static class ViewHolder
    {
        TextView tvName;
        Button tvbtnText;

    }
}