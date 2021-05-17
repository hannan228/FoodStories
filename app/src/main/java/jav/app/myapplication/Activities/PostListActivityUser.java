package jav.app.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import jav.app.myapplication.ContactUs;
import jav.app.myapplication.Data.BlogRecyclerAdapter;
import jav.app.myapplication.LoginActivity;
import jav.app.myapplication.MainActivity;
import jav.app.myapplication.Model.Blog;
import jav.app.myapplication.R;
import jav.app.myapplication.SplashScreen;

public class PostListActivityUser extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private RecyclerView recyclerView;
    private BlogRecyclerAdapter blogRecyclerAdapter;
    private List<Blog> blogList;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("MBlog");
        mDatabaseReference.keepSynced(true);

        blogList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewEd);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main_menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.contact:
               if(mUser !=null && mAuth != null){
                   startActivity(new Intent(PostListActivityUser.this, ContactUs.class));
                   finish();
               }
               break;

           case R.id.action_signout:
               if (mUser != null && mAuth!= null){
                   mAuth.signOut();

                   startActivity(new Intent(PostListActivityUser.this, LoginActivity.class));
               finish();
               }
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Blog blog = dataSnapshot.getValue(Blog.class);
                blogList.add(blog);
                blogRecyclerAdapter = new BlogRecyclerAdapter(PostListActivityUser.this,blogList);
//                Toast.makeText(PostListActivity.this,""+blogRecyclerAdapter,Toast.LENGTH_LONG).show();
                recyclerView.setAdapter(blogRecyclerAdapter);
                blogRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error Loading Image"+databaseError, Toast.LENGTH_SHORT).show();
            }
        } );
    }

}
