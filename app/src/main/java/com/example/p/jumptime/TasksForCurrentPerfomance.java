package com.example.p.jumptime;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class TasksForCurrentPerfomance extends Fragment {
    ArrayList<TaskForRecyclerView> tasks;
    FirebaseUser user;
    DataAdaptermy adapter;
    ArrayList sizeArray = new ArrayList();
    View rootView;
    List<String[]> array;
    ArrayList images;
    RecyclerView recyclerView;
    CoordinatorLayout coordinatorLayout;
    DatabaseReference myRef;
    DatabaseReference myRef1;
    ArrayList arForAdapter;
    LinearLayoutManager layoutManager;
    int[] image_priority = {R.mipmap.white, R.mipmap.yellow, R.mipmap.orange, R.mipmap.red};

    public TasksForCurrentPerfomance() {
        tasks = new ArrayList<>();
        array = new ArrayList<>();
        images = new ArrayList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab2, container, false);
        //user = FirebaseAuth.getInstance().getCurrentUser();
        arForAdapter = new ArrayList();
      /*  myRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        myRef1 = FirebaseDatabase.getInstance().getReference();*/
        coordinatorLayout = rootView.findViewById(R.id.coordinatorLayout);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView = rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);
        /*setRef();
        setElement();*/


        sizeArray = GetListTask();
        getArray(sizeArray);
        adapter = new DataAdaptermy(getContext(), tasks);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);


        enableSwipeToDeleteAndUndo();


        return rootView;

    }

    //метод читает список задач из бд sqlite
    private ArrayList GetListTask() {
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        // создаем объект для создания и управления версиями БД
        DataBase.DBHelper dbHelper = new DataBase.DBHelper(getContext());
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        ArrayList listtasks = new ArrayList();
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int dataColIndex = c.getColumnIndex("data");
            int timeColIndex = c.getColumnIndex("time");
            int kColIndex = c.getColumnIndex("k");
            int iColIndex = c.getColumnIndex("i");
            int lColIndex = c.getColumnIndex("l");
            int oColIndex = c.getColumnIndex("o");
            int priorityColIndex = c.getColumnIndex("priority");
            int projectColIndex = c.getColumnIndex("project");
            int activeColIndex = c.getColumnIndex("active");


            do {
                ArrayList temp = new ArrayList();
                temp.add(c.getInt(idColIndex));
                temp.add(c.getString(nameColIndex));
                temp.add(c.getString(dataColIndex));
                temp.add(c.getString(timeColIndex));
                temp.add(c.getString(kColIndex));
                temp.add(c.getString(iColIndex));
                temp.add(c.getString(lColIndex));
                temp.add(c.getString(oColIndex));
                temp.add(c.getString(priorityColIndex));
                temp.add(c.getString(projectColIndex));
                temp.add(c.getString(activeColIndex));
                listtasks.add(temp);
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false -
                // выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d("TAG", "0 rows");
        c.close();

        return listtasks;
    }

    private void getArray(ArrayList ar) {
        if (ar.isEmpty()) {
        } else {
            for (int i = 0; i < ar.size(); i++) {
                ArrayList<ArrayList> temp = (ArrayList) ar.get(i);
                Log.d("Tag", Integer.valueOf(String.valueOf(temp.get(8))).getClass() + "");
               int index = Integer.valueOf(String.valueOf(temp.get(8)));
                Toast.makeText(getContext(),"size =  "+temp.size()+" /" + temp.get(8)+"/",Toast.LENGTH_SHORT).show();
                tasks.add(new TaskForRecyclerView(String.valueOf(temp.get(1)), String.valueOf(temp.get(2)), String.valueOf(temp.get(3)), image_priority[index],  Integer.valueOf(String.valueOf(temp.get(0))), getActivity()));
                temp.clear();
            }
        }
    }
   /* private void setRef() {
       /* myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSbapshot : dataSnapshot.getChildren()) {
                    images.add((childSbapshot.getValue()));
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
     /*   myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int i = 0; i < sizeArray.size(); i++) {
                    String s = sizeArray.get(i).toString();

                    // Toast.makeText(rootView.getContext(),s, Toast.LENGTH_SHORT).show();
                    String name = "" + dataSnapshot.child("users").child(user.getUid()).child("tasks").child(s).child("name").getValue(String.class);
                    String data = "" + dataSnapshot.child("users").child(user.getUid()).child("tasks").child(s).child("data").getValue(String.class);
                    String time = "" + dataSnapshot.child("users").child(user.getUid()).child("tasks").child(s).child("time").getValue(String.class);
                    String priority = dataSnapshot.child("users").child(user.getUid()).child("tasks").child(s).child("priority").getValue(String.class);
                    String kilo = "" + dataSnapshot.child("users").child(user.getUid()).child("tasks").child(s).child("kilo").getValue(String.class);
                    String project = "" + dataSnapshot.child("users").child(user.getUid()).child("tasks").child(s).child("project").getValue(String.class);


                    String[] a = {name, data, kilo, time, project};
                    array.add(a);

                        tasks.add(new TaskForRecyclerView(s, a[1], a[3], image_priority[0], getActivity()));




                }
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }*/

   /* private void setElement() {

        DatabaseReference info = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("tasks");
        info.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String child = ds.getKey();
                    sizeArray.add(child);
                    Log.d("TAG", child);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("w", "onCancelled", databaseError.toException());
            }
        });


    }*/

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();

                DataBase.DBHelper dbHelper = new DataBase.DBHelper(getContext());

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Log.d("LOG", "--- Delete from mytable: ---");
                // удаляем по id
                TaskForRecyclerView temp = tasks.get(position);


                int delCount = db.delete("mytable", "id = " + temp.getID(), null);
                Log.d("LOG", "deleted rows count = " + delCount);
                adapter.removeItem(position);
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {


                            adapter.restoreItem(tasks.get(0), position);

                        } catch (IndexOutOfBoundsException ex) {

                        }
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }


}