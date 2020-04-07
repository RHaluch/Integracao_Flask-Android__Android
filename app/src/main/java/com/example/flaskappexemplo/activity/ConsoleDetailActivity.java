package com.example.flaskappexemplo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.flaskappexemplo.R;
import com.example.flaskappexemplo.model.Console;
import com.example.flaskappexemplo.util.APISingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class ConsoleDetailActivity extends AppCompatActivity {

    private TextView textName, textYear, textPrice, textQuantidade, textAtivo;
    private long id;
    private Console console;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console_detail);

        id = getIntent().getLongExtra("ID",0);

        textName = findViewById(R.id.textName);
        textYear = findViewById(R.id.textYear);
        textPrice = findViewById(R.id.textPrice);
        textQuantidade = findViewById(R.id.textQuantidade);
        textAtivo = findViewById(R.id.textAtivo);
    }

    @Override
    protected void onStart(){
        super.onStart();
        loadConsole();
    }

    private void loadConsole() {
        String url = "http://10.0.2.2:5000/api/console/"+id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                console = new Console();
                try {
                    console.setId(response.getLong("id"));
                    console.setName(response.getString("name"));
                    console.setYear(response.getInt("year"));
                    console.setPrice(response.getDouble("price"));
                    console.setQuantidade_jogos(response.getInt("quantidade_jogos"));
                    console.setAtivo(response.getBoolean("ativo"));

                    textName.setText(console.getName());
                    textYear.setText(String.valueOf(console.getYear()));
                    textPrice.setText(String.valueOf(console.getPrice()));
                    textQuantidade.setText(String.valueOf(console.getQuantidade_jogos()));
                    if(console.isAtivo()){
                        textAtivo.setText("SIM");
                    }else{
                        textAtivo.setText("NÃO");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        APISingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    public void updateConsole(View view) {
        Intent newConsole = new Intent(ConsoleDetailActivity.this,NewConsoleActivity.class);
        newConsole.putExtra("ID",console.getId());
        startActivity(newConsole);
    }

    public void deleteConsole(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tem certeza que deseja excluir o console "+console.getId()+"?");
        builder.setTitle("Excluir");
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String url = "http://10.0.2.2:5000/api/console/"+id;
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(ConsoleDetailActivity.this, "Console "+response.getLong("id")+" removido.", Toast.LENGTH_SHORT).show();
                            Intent main = new Intent(ConsoleDetailActivity.this,MainActivity.class);
                            startActivity(main);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                APISingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
            }
        });
        builder.setNegativeButton("NÃO",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
