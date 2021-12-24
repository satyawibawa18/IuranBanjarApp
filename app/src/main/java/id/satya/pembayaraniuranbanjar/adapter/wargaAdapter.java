package id.fabian.pembayaraniuranbanjar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.fabian.pembayaraniuranbanjar.R;
import id.fabian.pembayaraniuranbanjar.database.wargaEntity;

public class wargaAdapter extends RecyclerView.Adapter<wargaAdapter.ViewAdapter> {

    private List<wargaEntity> list;
    private Context context;
    private Dialog dialog;

    public interface Dialog {
        void onClick(int position);
    }
    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }

    public wargaAdapter(Context context, List<wargaEntity> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter holder, int position) {
        holder.fullname.setText(list.get(position).fullname);
        holder.alamat.setText(list.get(position).alamat);
        holder.nik.setText(""+list.get(position).NIK_KITAS);
        holder.gender.setText(list.get(position).jeniskelamin);
        holder.iuran.setText(list.get(position).iuran);
        holder.familynumber.setText(""+list.get(position).anggota);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewAdapter extends RecyclerView.ViewHolder{
        TextView fullname, alamat, nik, gender, iuran, familynumber;

        public  ViewAdapter(@NonNull View itemView){
            super(itemView);
            fullname = itemView.findViewById(R.id.nama);
            alamat = itemView.findViewById(R.id.alamat);
            nik = itemView.findViewById(R.id.NIK);
            gender = itemView.findViewById(R.id.JK);
            iuran = itemView.findViewById(R.id.jenisIuran);
            familynumber =itemView.findViewById(R.id.jumlahkel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog!=null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });

        }

    }
}
