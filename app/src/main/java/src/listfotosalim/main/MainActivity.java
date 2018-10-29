package src.listfotosalim.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import src.listfotosalim.R;
import src.listfotosalim.api.BaseApiService;
import src.listfotosalim.api.UtilsApi;
import src.listfotosalim.model.ResponsePhoto;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @BindView(R.id.rvMain)
    RecyclerView rvMin;
    private BaseApiService mApiService;
    private ProgressDialog loading;
    private Context context;
    private MainAdapter adapter;
    private List<ResponsePhoto> poto = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mApiService = UtilsApi.getAPIService();
        context = this;
        viewData();

    }

    private void viewData() {
        rvMin.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }

    private void getData() {
        loading = ProgressDialog.show(context, null, "Loading...", false, false);
        mApiService.getListFoto().enqueue(new Callback<List<ResponsePhoto>>() {
            @Override
            public void onResponse(Call<List<ResponsePhoto>> call, Response<List<ResponsePhoto>> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    poto = response.body();
                    adapter = new MainAdapter(context, poto);
                    rvMin.setAdapter(adapter);
                } else {
                    loading.dismiss();
                    Toast.makeText(context, "Jaringan Sibuk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponsePhoto>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(context, "Tidak ada konkesi internet", Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("search all u need");
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        rvMin.setVisibility(View.GONE);
        //loading = ProgressDialog.show(context, null, "searching...", false, false);
        mApiService.getListFoto().enqueue(new Callback<List<ResponsePhoto>>() {
            @Override
            public void onResponse(Call<List<ResponsePhoto>> call, Response<List<ResponsePhoto>> response) {
                if (response.isSuccessful()) {
                    //loading.dismiss();
                    rvMin.setVisibility(View.VISIBLE);
                    poto = response.body();
                    adapter = new MainAdapter(context, poto);
                    rvMin.setAdapter(adapter);
                } else {
                   // loading.dismiss();
                    Toast.makeText(context, "Jaringan Sibuk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponsePhoto>> call, Throwable t) {
                //loading.dismiss();
                Toast.makeText(context, "Tidak ada konkesi internet", Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });

        return true;
    }
}
