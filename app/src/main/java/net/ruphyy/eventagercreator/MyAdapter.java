package net.ruphyy.eventagercreator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String s1[], s2[];
    int images[];
    Context context;

    public MyAdapter(Context context,String s1[], String s2[], int images[] ){
        this.context = context;
        this.s1 = s1;
        this.s2 = s2;
        this.images = images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater infalter = LayoutInflater.from(context);
        View view = infalter.inflate(R.layout.item_event,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.myText1.setText(s1[position]);
        holder.myText2.setText(s2[position]);
        holder.myImage.setImageResource(images[position]);
        holder.deleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Delete Event from Database
            }
        });

        holder.editEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Edit Event from Database
            }
        });

       /* holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetail.class);
                intent.putExtra("s1", s1[position]);
                intent.putExtra("s2", s2[position]);
                intent.putExtra("myImage", images[position]);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return s1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText1, myText2;
        ImageView myImage;
        ImageButton editEvent, deleteEvent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.eventTopic);
            myText2 = itemView.findViewById(R.id.eventDate);
            myImage = itemView.findViewById(R.id.eventKind);
            editEvent = itemView.findViewById(R.id.eventEdit);
            deleteEvent = itemView.findViewById(R.id.eventDelete);

        }
    }
}