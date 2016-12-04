package kbl.chelbi.amawal;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Cursor mDataSet ;

    public MyAdapter(Cursor myDataSet) {
        this.mDataSet= myDataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView cardView =(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card_view,parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if( mDataSet.moveToPosition(position)) {
            ((TextView) holder.cardView.findViewById(R.id.info_text)).setText(mDataSet.getString(1));
            ((TextView) holder.cardView.findViewById(R.id.def_text)).setText(mDataSet.getString(2));
        }else{
            mDataSet.close();
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.getCount() ;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView ;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }


}
