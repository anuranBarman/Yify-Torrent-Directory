package adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mranuran.yifytorrentdirectory.DetailActivity;
import com.mranuran.yifytorrentdirectory.R;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.Torrent;

public class TorrentAdapter extends RecyclerView.Adapter<TorrentAdapter.ViewHolder> {

    List<Torrent> torrents;
    Context context;

    public TorrentAdapter(List<Torrent> torrents, Context context) {
        this.torrents = torrents;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.torrent_single_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.torrentNameText.setText("Torrent "+(position+1));
        holder.torrentSizeText.setText(torrents.get(position).getSize());
        holder.torrentQualityText.setText(torrents.get(position).getQuality());
        holder.torrentSeedText.setText("("+torrents.get(position).getSeeds()+","+torrents.get(position).getPeers()+")");
        holder.torrentTimeText.setText(torrents.get(position).getDate_uploaded());

        holder.copyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyMagnetLink(torrents.get(position));
            }
        });
    }

    private void copyMagnetLink(Torrent torrent) {
        //magnet:?xt=urn:btih:TORRENT_HASH&dn=Url+Encoded+Movie+Name&tr=http://track.one:1234/announce&tr=udp://track.two:80

        try {
            String hash=torrent.getHash();
            String movieName= DetailActivity.title;
            String movieNameURLEncoded= URLEncoder.encode(movieName,"UTF-8");
            String url="magnet:?xt=urn:btih:"+hash+"&dn="+movieNameURLEncoded;
            addTrackers(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void addTrackers(String url) {
        String[] trackers=new String[]{
                "udp://glotorrents.pw:6969/announce",
                "udp://tracker.opentrackr.org:1337/announce",
                "udp://torrent.gresille.org:80/announce",
                "udp://tracker.openbittorrent.com:80",
                "udp://tracker.coppersurfer.tk:6969",
                "udp://tracker.leechers-paradise.org:6969",
                "udp://p4p.arenabg.ch:1337",
                "udp://tracker.internetwarriors.net:1337"
        };

        for(int i=0;i<trackers.length;i++){
            url+="&tr="+trackers[i];
        }

        ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("torrent", url);
        clipboard.setPrimaryClip(clip);
        Log.d("ANURAN torrent",url);
        Toast.makeText(context,"Magnet Link Copied To Clipboard.",Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount() {
        if(torrents !=null)
            return torrents.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.torrentName)
        TextView torrentNameText;
        @BindView(R.id.torrentQualityText)
        TextView torrentQualityText;
        @BindView(R.id.torrentSeedText)
        TextView torrentSeedText;
        @BindView(R.id.torrentSizeText)
        TextView torrentSizeText;
        @BindView(R.id.torrentTimeText)
        TextView torrentTimeText;
        @BindView(R.id.copyText)
        LinearLayout copyText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
