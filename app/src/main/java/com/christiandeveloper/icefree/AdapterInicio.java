package com.christiandeveloper.icefree;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;


class AdapterInicio extends RecyclerView.Adapter<AdapterInicio.ViewHolder> {

    private Context context;
    private List<productos> productoses;
    private EscuchaEventosClick escucha;

    interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Views
        ImageView imgee;

        ViewHolder(View itemView) {
            super(itemView);
            imgee = (ImageView) itemView.findViewById(R.id.ImageviewFoto);
            //imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewInicio);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }


    public AdapterInicio(List<productos> productoses, Context context, EscuchaEventosClick escucha) {
        this.productoses = productoses;
        this.context = context;
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return productoses.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_productos, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        productos prom = productoses.get(position);

        Glide.with(context)
                .load(prom.getImagen())
                .crossFade()
                .centerCrop()
                .into(holder.imgee);
    }

}

