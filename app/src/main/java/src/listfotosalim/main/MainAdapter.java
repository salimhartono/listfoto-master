package src.listfotosalim.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import src.listfotosalim.R;
import src.listfotosalim.model.ResponsePhoto;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.PhotoHolder> {
    private Context context;
    List<ResponsePhoto> photo;

    public MainAdapter(Context context, List<ResponsePhoto> photo) {
        this.context = context;
        this.photo = photo;
    }

    @NonNull
    @Override
    public MainAdapter.PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.raw_foto, parent, false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.PhotoHolder holder, int position) {

        final String Gambar = photo.get(position).getThumbnailUrl();
        holder.tvTitle.setText(photo.get(position).getTitle());

        //holder.tvId.setText(photo.get(position).getId());
        Picasso.with(context).load(Gambar).into(holder.ivPoto);

    }

    @Override
    public int getItemCount() {
        return photo.size();
    }

    public class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.raw_title)
        TextView tvTitle;
        @BindView(R.id.raw_iv)
        ImageView ivPoto;

        public PhotoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
