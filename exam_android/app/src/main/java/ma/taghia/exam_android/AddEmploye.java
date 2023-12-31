package ma.taghia.exam_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Collections;

import ma.taghia.exam_android.beans.Service;
import ma.taghia.exam_android.beans.Service;

public class AddEmploye extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Service> serviceList;
    String[] services;

    private EditText lastName, firstName, dateNaissance;

    private Button btnSave;

    private int selectedServiceId = -1;
    private String url = "http://192.168.1.233:8082/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employe);

        String loadServicesUrl = this.url + "services";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, loadServicesUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response + "");
                        handleJsonResponseService(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("err", error + "");
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        Spinner spin = findViewById(R.id.service);
        spin.setOnItemSelectedListener(this);

        btnSave = findViewById(R.id.bnAdd);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmployeData();
            }
        });

    }

    private void sendEmployeData() {

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastname);
        dateNaissance = findViewById(R.id.date);

        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject serviceObject = new JSONObject();
            serviceObject.put("id", selectedServiceId);

            jsonObject.put("prenom", firstName.getText().toString());
            jsonObject.put("nom", lastName.getText().toString());
            jsonObject.put("dateNaissance", dateNaissance.getText().toString());
            jsonObject.put("serviceEntity", serviceObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest request = new StringRequest(Request.Method.POST, url + "employes", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddEmploye.this, "Employé créé avec succès", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddEmploye.this, "Erreur lors de l'envoi des données", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public byte[] getBody() {
                return jsonObject.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public void onItemSelected(AdapterView arg0, View arg1, int position, long id) {
        selectedServiceId = Math.toIntExact(serviceList.get(position).getId());
    }

    @Override
    public void onNothingSelected(AdapterView arg0) {
        // Auto-generated method stub
    }

    private void handleJsonResponseService(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            serviceList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Service service = new Service(
                        jsonObject.getLong("id"),
                        jsonObject.getString("nom")
                );
                serviceList.add(service);
            }

            services = new String[serviceList.size()];

            for (int i = 0; i < serviceList.size(); i++) {
                services[i] = serviceList.get(i).getNom();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner spinner = findViewById(R.id.service);
            spinner.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}