package com.example.p.jumptime;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Categories  extends Fragment {
    private LinkedHashMap<String, HeaderInfo> mySection = new LinkedHashMap<>();
    private ArrayList<HeaderInfo> SectionList = new ArrayList<>();
    Toolbar myToolbar;
    Spinner mySpinner;
    private MyListAdapter listAdapter;
    private ExpandableListView expandableListView;
    View view;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category , container, false);

        //Just add some data to start with
        AddProduct();
        myToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mySpinner = (Spinner) view.findViewById(R.id.spinner);

        myToolbar.setTitle(getResources().getString(R.string.app_name));

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_item,
                getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),
                        mySpinner.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //get reference to the ExpandableListView
        expandableListView = (ExpandableListView) view.findViewById(R.id.myList);
        //create the adapter by passing your ArrayList data
        listAdapter = new MyListAdapter(getContext(), SectionList);
        //attach the adapter to the list
        expandableListView.setAdapter(listAdapter);

        //expand all Groups
        expandAll();

        //add new item to the List
        Button add = (Button) view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    //add entry to the List
                    case R.id.add:

                  /*     // Spinner spinner = (Spinner) view.findViewById(R.id.department);
                       // String department = spinner.getSelectedItem().toString();
                        EditText editText = (EditText) view.findViewById(R.id.product);
                        String product = editText.getText().toString();
                        editText.setText("");

                        //add a new item to the list
                      ///  int groupPosition = addProduct(department,product);
                        //notify the list so that changes can take effect
                        listAdapter.notifyDataSetChanged();

                        //collapse all groups
                        collapseAll();
                        //expand the group where item was just added
                        expandableListView.expandGroup(groupPosition);
                        //set the current group to be selected so that it becomes visible
                        expandableListView.setSelectedGroup(groupPosition);
*/
                        break;
                }
            }
        });

        //listener for child row click
        expandableListView.setOnChildClickListener(myListItemClicked);
        //listener for group heading click
        expandableListView.setOnGroupClickListener(myListGroupClicked);

        return view;
    }
   //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expandableListView.expandGroup(i);
        }
    }//method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expandableListView.collapseGroup(i);
        }
    }
    private void AddProduct_Deled(){
        SectionList.clear();
        mySection.clear();
        addProduct("Vegetable","Potato");
        addProduct("Vegetable","Cabbage");


        addProduct("Fruits","Apple");
        addProduct("Fruits","Orange");
    }
    //load some initial data into out list
    private void AddProduct(){

        addProduct("Vegetable","Potato");
        addProduct("Vegetable","Cabbage");
        addProduct("Vegetable","Onion");

        addProduct("Fruits","Apple");
        addProduct("Fruits","Orange");
    }
    //our child listener
    private ExpandableListView.OnChildClickListener myListItemClicked =  new ExpandableListView.OnChildClickListener() {

        public void removeGroup(int group) {
            //TODO: Remove the according group. Dont forget to remove the children aswell!
            Log.v("Adapter", "Removing group"+group);
            listAdapter.notifyDataSetChanged();
        }


        public void removeChild(int group, int child) {
            //TODO: Remove the according child
            Log.v("Adapter", "Removing child "+child+" in group "+group);
           // removeProduct("Vegetable","Potato");

             //mySection.remove("Vegetable","Potato");
            SectionList.clear();
            AddProduct_Deled();
            listAdapter = new MyListAdapter(getContext(), SectionList);
            //attach the adapter to the list
            expandableListView.setAdapter(listAdapter);
            listAdapter.onGroupExpanded(0);
            listAdapter.onGroupExpanded(0);
            /*
           код  удалить группу
             SectionList.remove(0);
            listAdapter = new MyListAdapter(getContext(), SectionList);
            //attach the adapter to the list
            expandableListView.setAdapter(listAdapter);*/
            listAdapter.notifyDataSetChanged();
        }
        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {

            //get the group header
            HeaderInfo headerInfo = SectionList.get(groupPosition);
            //get the child info
            DetailInfo detailInfo =  headerInfo.getProductList().get(childPosition);
            //display it or do something with it
            Toast.makeText(getContext(), "Clicked on Detail " + headerInfo.getName()
                    + "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();
            /*removeProduct(headerInfo.getName(),detailInfo.getName());
            listAdapter.notifyDataSetChanged();*/
            removeChild(0,0);
            listAdapter.notifyDataSetChanged();
            return false;
        }
    };

    //our group listener
    private ExpandableListView.OnGroupClickListener myListGroupClicked =  new ExpandableListView.OnGroupClickListener() {

        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id) {

            //get the group header
            HeaderInfo headerInfo = SectionList.get(groupPosition);
            //display it or do something with it
            Toast.makeText(getContext(), "Child on Header " + headerInfo.getName(),
                    Toast.LENGTH_LONG).show();

            return false;
        }
    };

    //here we maintain our products in various departments
    private int addProduct(String department, String product){

        int groupPosition = 0;

        //check the hash map if the group already exists
        HeaderInfo headerInfo = mySection.get(department);
        //add the group if doesn't exists
        if(headerInfo == null){
            headerInfo = new HeaderInfo();
            headerInfo.setName(department);
            mySection.put(department, headerInfo);
            SectionList.add(headerInfo);
        }

        //get the children for the group
        ArrayList<DetailInfo> productList = headerInfo.getProductList();
        //size of the children list
        int listSize = productList.size();
        //add to the counter
        listSize++;

        //create a new child and add that to the group
        DetailInfo detailInfo = new DetailInfo();
        detailInfo.setSequence(String.valueOf(listSize));
        detailInfo.setName(product);

        productList.add(detailInfo);
        headerInfo.setProductList(productList);

        //find the group position inside the list
        groupPosition = SectionList.indexOf(headerInfo);
        return groupPosition;
    }
    private int removeProduct(String department, String product){

        int groupPosition = 0;

        //check the hash map if the group already exists
        HeaderInfo headerInfo = mySection.get(department);

        //get the children for the group
        ArrayList<DetailInfo> productList = headerInfo.getProductList();
        //size of the children list
        int listSize = productList.size();
        //add to the counter
        listSize--;

        //create a new child and add that to the group
        DetailInfo detailInfo = new DetailInfo();
        detailInfo.setSequence(String.valueOf(listSize));
        detailInfo.setName(product);

        productList.remove(detailInfo);
        headerInfo.setProductList(productList);

        //find the group position inside the list
        groupPosition = SectionList.indexOf(headerInfo);
        return groupPosition;
    }
    public void removeGroup(int group) {
        //TODO: Remove the according group. Dont forget to remove the children aswell!
        Log.v("Adapter", "Removing group"+group);

    }

    public void removeChild(int group, int child) {
        //TODO: Remove the according child
        Log.v("Adapter", "Removing child "+child+" in group "+group);
        listAdapter.notifyDataSetChanged();
    }

}
