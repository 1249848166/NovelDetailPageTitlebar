package su.com.noveldetailpagetitlebar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyHolder> {

    Context context;
    List<CommentData> datas;

    public MyRecyclerAdapter(Context context, List<CommentData> datas){
        this.context=context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.comment_item,parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ((ImageView)holder.itemView.findViewById(R.id.head2)).setImageBitmap(datas.get(position).getHead());
        ((TextView)holder.itemView.findViewById(R.id.name)).setText(datas.get(position).getName());
        ((TextView)holder.itemView.findViewById(R.id.description)).setText(datas.get(position).getComment());
        ((TextView)holder.itemView.findViewById(R.id.time)).setText(datas.get(position).getTime());
        ((TextView)holder.itemView.findViewById(R.id.num)).setText(datas.get(position).getResponseNum());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        View itemView;

        public MyHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
        }
    }
}
