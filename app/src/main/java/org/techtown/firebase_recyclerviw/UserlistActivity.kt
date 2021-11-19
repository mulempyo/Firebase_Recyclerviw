package org.techtown.firebase_recyclerviw

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class UserlistActivity : AppCompatActivity() {
    lateinit var mDatabase:DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit  var userArrayList : ArrayList<User>
    private lateinit var adapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userlist_activity)
        userArrayList = ArrayList()
        userRecyclerview = findViewById(R.id.userList)
        adapter = MyAdapter(userArrayList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        mDatabase= FirebaseDatabase.getInstance().getReference("User")
           mDatabase.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children){
                            val animal = userSnapshot.getValue(User::class.java)
                            userArrayList.add(animal!!)
                        }
                        userRecyclerview.adapter = adapter
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@UserlistActivity,error.message,Toast.LENGTH_LONG).show()
                }
            })


    }

}
