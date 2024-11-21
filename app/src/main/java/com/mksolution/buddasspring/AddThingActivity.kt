package com.mksolution.buddasspring

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.mksolution.buddasspring.databinding.ActivityAddThingBinding
import java.util.*

class AddThingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddThingBinding
    private lateinit var dialog: Dialog
    private var imageUri : Uri?=null
    private var launchGelaryActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            imageUri = it.data!!.data
            binding.imageView5.setImageURI(imageUri)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddThingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = Dialog(this)
        dialog.setContentView(R.layout.progress_layout)
        dialog.setCancelable(true)

        binding.saveThing.setOnClickListener {
            if (binding.checkBoxStory.isChecked && !imageUri.toString().isEmpty()){
                saveStoryImage(imageUri!!)
                dialog.show()
            }
            if (binding.checkBoxTeching.isChecked && !imageUri.toString().isEmpty()){
                saveTeachingImage(imageUri!!)
                dialog.show()
            }
            if (binding.checkBoximageQu.isChecked && !imageUri.toString().isEmpty()){
                saveQuoteImage(imageUri!!)
                dialog.show()
            }
            if (binding.checkQuote.isChecked ){
                saveQuote()
                dialog.show()
            }




        }

        binding.imageView5.setOnClickListener {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            launchGelaryActivity.launch(intent)
        }
    }

    private fun saveQuote() {
        val dbRef = FirebaseDatabase.getInstance().reference.child("Quote")
        val postId= dbRef.push().key
        val postMap = HashMap<String, Any>()
        postMap["quote"] = binding.editquote.text.toString()
        postMap["quoteId"] = postId.toString()

        if (postId != null) {
            dbRef.child(postId).setValue(postMap).addOnSuccessListener {
                Toast.makeText(this, " Quote Added", Toast.LENGTH_SHORT).show()
                binding.editquote.text.clear()
                dialog.dismiss()

            }.addOnFailureListener {
                Toast.makeText(this, "Upload  Fail", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
    }

    private fun saveStoryImage(uri: Uri) {
        val fileName = UUID.randomUUID().toString()+".jpg"
        val storageRef = FirebaseStorage.getInstance().reference.child("StoryImage/$fileName")
        storageRef.putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {image->
                storeStory(image.toString())

            }
        }
            .addOnFailureListener{
                Toast.makeText(this, "Upload Storage Fail", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
    }
    private fun saveTeachingImage(uri: Uri) {
        val fileName = UUID.randomUUID().toString()+".jpg"
        val storageRef = FirebaseStorage.getInstance().reference.child("TeachingImage/$fileName")
        storageRef.putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {image->
                storeTeaching(image.toString())

            }
        }
            .addOnFailureListener{
                Toast.makeText(this, "Upload Storage Fail", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
    }
    private fun saveQuoteImage(uri: Uri) {
        val fileName = UUID.randomUUID().toString()+".jpg"
        val storageRef = FirebaseStorage.getInstance().reference.child("ImageQuote/$fileName")
        storageRef.putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {image->
                storeQuoteImage(image.toString())

            }
        }
            .addOnFailureListener{
                Toast.makeText(this, "Upload Storage Fail", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
    }


    private fun storeStory(imageUrl: String) {
        val dbRef = FirebaseDatabase.getInstance().reference.child("Story")
        val postId= dbRef.push().key
        val postMap = HashMap<String, Any>()
        postMap["ImageUrl"] = imageUrl
        postMap["storyId"] = postId.toString()
        postMap["storyTitle"] = binding.editTitle.text.toString()
        postMap["storyDes"] = binding.editStory.text.toString()


        if (postId != null) {
            dbRef.child(postId).setValue(postMap).addOnSuccessListener {
                Toast.makeText(this, "Story Added", Toast.LENGTH_SHORT).show()
                binding.editTitle.text.clear()
                binding.editStory.text.clear()
                imageUri = null
                dialog.dismiss()

            }.addOnFailureListener {
                Toast.makeText(this, "Upload  Fail", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
    }
    private fun storeTeaching(imageUrl: String) {
        val dbRef = FirebaseDatabase.getInstance().reference.child("Teaching")
        val postId= dbRef.push().key
        val postMap = HashMap<String, Any>()
        postMap["ImageUrl"] = imageUrl
        postMap["teachId"] = postId.toString()
        postMap["teachTitle"] = binding.editTitle.text.toString()
        postMap["teachDes"] = binding.editTeachging.text.toString()


        if (postId != null) {
            dbRef.child(postId).setValue(postMap).addOnSuccessListener {
                Toast.makeText(this, "Teaching Story Added", Toast.LENGTH_SHORT).show()
                binding.editTitle.text.clear()
                binding.editTeachging.text.clear()
                imageUri = null
                dialog.dismiss()

            }.addOnFailureListener {
                Toast.makeText(this, "Upload  Fail", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
    }
    private fun storeQuoteImage(imageUrl: String) {
        val dbRef = FirebaseDatabase.getInstance().reference.child("ImageQuote")
        val postId= dbRef.push().key
        val postMap = HashMap<String, Any>()
        postMap["ImageUrl"] = imageUrl
        postMap["imageQuoteId"] = postId.toString()

        if (postId != null) {
            dbRef.child(postId).setValue(postMap).addOnSuccessListener {
                Toast.makeText(this, "Image Quote Added", Toast.LENGTH_SHORT).show()

                dialog.dismiss()

            }.addOnFailureListener {
                Toast.makeText(this, "Upload  Fail", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
    }



}