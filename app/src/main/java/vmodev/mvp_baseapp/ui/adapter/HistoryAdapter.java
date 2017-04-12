//package vmodev.mvp_baseapp.ui.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import vmodev.mvp_baseapp.R;
//
///**
// * Created by thanhle on 3/11/17.
// */
//
//public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
//    protected ICallBack mCallback;
//
//    public HistoryAdapter(Context context, ICallBack callBack) {
//        mCallback = callBack;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        mCallback.updateViewHolder(holder, position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mCallback.getCount();
//    }
//
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView tvStatus, tvNote, tvDate, tvGiaoVan;
//        public ImageView imvPhoto;
//        public ViewHolder(View itemView) {
//            super(itemView);
//            imvPhoto = (ImageView) itemView.findViewById(R.id.imvPhoto);
//            tvGiaoVan = (TextView) itemView.findViewById(R.id.tvGiaoVan);
//            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
//            tvNote = (TextView) itemView.findViewById(R.id.tvNote);
//            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
//            IGetPosition getPosition = new IGetPosition() {
//                @Override
//                public int getPosition() {
//                    return getAdapterPosition();
//                }
//            };
//            itemView.setTag(getPosition);
//        }
//    }
//
//    public interface ICallBack {
//        int getCount();
//        void updateViewHolder(ViewHolder holder, int position);
//
//    }
//}