package example.myapplication.assignment4;

        import android.content.Context;
        import android.content.DialogInterface;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.appcompat.app.AlertDialog;
        import androidx.recyclerview.widget.RecyclerView;

        import java.util.List;

public class CosmeticAdapter extends RecyclerView.Adapter<CosmeticAdapter.TasksViewHolder> {

    interface cosmeticClickListener {
        public void cosmeticClicked(Cosmetics selectedCosmetic);
    }
    private Context mCtx;
    public List<Cosmetics> cosmeticList;
    cosmeticClickListener listener;

    public CosmeticAdapter(Context mCtx, List<Cosmetics> cityList) {
        this.mCtx = mCtx;
        this.cosmeticList = cityList;
        listener = (cosmeticClickListener)mCtx;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_list, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Cosmetics t = cosmeticList.get(position);
        holder.cosmeticTextView.setText(t.getBrand() +": "+t.getName());
    }

    @Override
    public int getItemCount() {
        return cosmeticList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cosmeticTextView;

        public TasksViewHolder(View itemView) {
            super(itemView);

            cosmeticTextView = itemView.findViewById(R.id.product);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Cosmetics city = cosmeticList.get(getAdapterPosition());
            listener.cosmeticClicked(city);

        }
    }


}
