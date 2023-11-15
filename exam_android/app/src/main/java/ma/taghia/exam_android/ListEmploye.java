package ma.taghia.exam_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.taghia.exam_android.adapter.EmployeAdapter;
import ma.taghia.exam_android.beans.Employe;
import ma.taghia.exam_android.beans.Service;

public class ListEmploye extends AppCompatActivity {

    private ListView listView;
    private List<Employe> employeList;
    private EmployeAdapter adapter;

    private Button btnAdd;
    private String url = "http://192.168.1.233:8082/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_employe);
        listView = findViewById(R.id.listView);
        retrieveStudentsData();

    }

    private void retrieveStudentsData() {
        String loadUrl = this.url + "employes";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, loadUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Parse the JSON response and display data in ListView
                        Log.d("response", response + "");
                        handleJsonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListEmploye.this, "Error fetching data", Toast.LENGTH_SHORT).show();

                        Log.d("err", error + "");
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void handleJsonResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            employeList = new ArrayList<>();
            JSONObject services = new JSONObject();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Employe employe = new Employe(
                        jsonObject.getLong("id"),
                        jsonObject.getString("prenom"),
                        jsonObject.getString("nom"),
                        jsonObject.getString("dateNaissance"),
                        new Service(jsonObject.getJSONObject("serviceEntity").getLong("id"), jsonObject.getJSONObject("serviceEntity").getString("nom"))
                );
                employeList.add(employe);
            }

            adapter = new EmployeAdapter(this, employeList);
            listView.setAdapter((ListAdapter) adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}