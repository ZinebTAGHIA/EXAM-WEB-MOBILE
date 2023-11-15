package ma.taghia.exam_android.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import ma.taghia.exam_android.R;
import ma.taghia.exam_android.beans.Employe;


public class EmployeAdapter extends ArrayAdapter<Employe> {
    private List<Employe> employeList;
    private Context context;

    private String url = "http://192.168.1.233:8082/api/employes/";

    public EmployeAdapter(Context context, List<Employe> employeList) {
        super(context, 0, employeList);
        this.employeList = employeList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Employe employe = employeList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.employe_item, parent, false);
        }

        TextView id = convertView.findViewById(R.id.ids);
        TextView name = convertView.findViewById(R.id.name);
        TextView date = convertView.findViewById(R.id.date);
        TextView service = convertView.findViewById(R.id.service);


        id.setText(employe.getId()+"");
        name.setText(employe.getNom().toUpperCase(Locale.ROOT)+ " " + employe.getPrenom());
        date.setText(employe.getDate());
        service.setText(employe.getService().getNom());

        return convertView;
    }

    private void showDeleteConfirmation(final Employe employe) {
        new AlertDialog.Builder(getContext())
                .setTitle("Confirmation")
                .setMessage("Êtes vous sûr de vouloir supprimer cet étudiant?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        employeList.remove(employe);
                        EmployeAdapter.this.notifyDataSetChanged();
                        sendDeleteRequest(employe);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void sendDeleteRequest(Employe employe) {
        String deleteUrl = url + employe.getId();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.DELETE, deleteUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context.getApplicationContext(), "Studiant Deleted Successfully!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error+"");
            }
        });

        requestQueue.add(request);
    }
}
