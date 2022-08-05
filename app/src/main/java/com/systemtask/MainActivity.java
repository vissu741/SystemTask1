package com.systemtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class MainActivity extends AppCompatActivity {

    Realm mRealm;
    RecyclerView recyclerViewDummy;
    EmployeeAdapter employeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRealm = Realm.getDefaultInstance();
        initViews();
        Employee employee = mRealm.where(Employee.class).findFirst();
        if (employee!=null){
            readEmployeeRecords();
        }else{
            addDummyData();
        }

    }


    private void addDummyData() {

        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    try {
                        Employee employee = new Employee();
                        employee.name = "Alex";
                        employee.age = 50;
                        employee.city = "Mumbai";
                        realm.insertOrUpdate(employee);

                        employee.name = "Ellie";
                        employee.age = 25;
                        employee.city = "US";
                        realm.insertOrUpdate(employee);

                        employee.name = "Reina";
                        employee.age = 42;
                        employee.city = "Toranto";
                        realm.insertOrUpdate(employee);

                        employee.name = "Taylor";
                        employee.age = 37;
                        employee.city = "Hyderabad";
                        realm.insertOrUpdate(employee);

                        employee.name = "Abdul";
                        employee.age = 55;
                        employee.city = "Delhi";
                        realm.insertOrUpdate(employee);

                        employee.name = "Ryiaz";
                        employee.age = 33;
                        employee.city = "Kolkata";
                        realm.insertOrUpdate(employee);

                        employee.name = "Brenden";
                        employee.age = 62;
                        employee.city = "UK";
                        realm.insertOrUpdate(employee);

                        employee.name = "ellie";
                        employee.age = 19;
                        employee.city = "Ahmedabad";
                        realm.insertOrUpdate(employee);
                    } catch (RealmPrimaryKeyConstraintException e) {
                        Toast.makeText(getApplicationContext(), "Primary Key exists, Press Update instead", Toast.LENGTH_SHORT).show();
                    }
                }

            });

            readEmployeeRecords();
        }
    }


    private void readEmployeeRecords() {


        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmResults<Employee> results = realm.where(Employee.class).findAll();
                for (Employee employee : results) {
                    Log.d("response",employee.toString());
                      employeeAdapter = new EmployeeAdapter(getApplicationContext(), results);
                    recyclerViewDummy.setAdapter(employeeAdapter);
                }
            }
        });


    }

    private void initViews() {
        recyclerViewDummy = findViewById(R.id.recyclerView);
        recyclerViewDummy.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.name:
                sortingByName();
                Toast.makeText(getApplicationContext(),"Sort By name Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.age:
                sortingByAge();
                Toast.makeText(getApplicationContext(),"Sort By age Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.city:
                sortingByCity();
                Toast.makeText(getApplicationContext(),"Sort By city Selected",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sortingByName() {
        RealmResults<Employee> result = mRealm.where(Employee.class)
                .findAllSorted("name", Sort.ASCENDING);
        employeeAdapter.updateData(result);
        employeeAdapter.notifyDataSetChanged();
    }
    private void sortingByAge() {
        RealmResults<Employee> result = mRealm.where(Employee.class)
                .findAllSorted("age", Sort.ASCENDING);
        employeeAdapter.updateData(result);
        employeeAdapter.notifyDataSetChanged();
    }
    private void sortingByCity() {
        RealmResults<Employee> result = mRealm.where(Employee.class)
                .findAllSorted("city", Sort.ASCENDING);
        employeeAdapter.updateData(result);
        employeeAdapter.notifyDataSetChanged();
    }
}