package com.mksolution.buddasspring.Adaptars

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mksolution.buddasspring.Models.ImageQuote
import com.mksolution.buddasspring.R
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class ImageQuoteAdapter(var context: Context,var list: ArrayList<ImageQuote>):RecyclerView.Adapter<ImageQuoteAdapter.ImageQuoteHolder>() {



    inner class ImageQuoteHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val imageQ = itemView.findViewById<ImageView>(R.id.imageQuote)
        val save = itemView.findViewById<CircleImageView>(R.id.imSave)
        val shave = itemView.findViewById<CircleImageView>(R.id.imShare)
        val backGround = itemView.findViewById<ConstraintLayout>(R.id.connstantFotShot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageQuoteHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_quote_item,parent,false)
        return ImageQuoteHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ImageQuoteHolder, position: Int) {
        val data = list[position]

        if (data.ImageUrl.isNotEmpty()){
            Glide.with(context).load(data.ImageUrl).into(holder.imageQ)
        }

        holder.save.setOnClickListener {

            val bitmap = getScreenshot(holder.backGround, holder.save,holder.shave)
            saveImageToGallery(bitmap,holder.save,holder.shave)
        }

        // Share the background as an image
        holder.shave.setOnClickListener {

            val bitmap = getScreenshot(holder.backGround, holder.save, holder.shave)
            shareImage(bitmap)
        }

    }
    private fun getScreenshot(view: View, save: CircleImageView, shave: CircleImageView): Bitmap {
        save.visibility = View.GONE
        shave.visibility = View.GONE
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        save.visibility = View.VISIBLE
        shave.visibility= View.VISIBLE
        return bitmap

    }

    // Function to save image to gallery
    private fun saveImageToGallery(bitmap: Bitmap, save: CircleImageView, shave: CircleImageView) {


        val filename = "quote_${System.currentTimeMillis()}.png"
        val fos: OutputStream?

        // For Android Q and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        } else {
            // For devices below Android Q
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show()

            save.visibility = View.VISIBLE
            shave.visibility= View.VISIBLE

        }
    }

    // Function to share image via other apps
    private fun shareImage(bitmap: Bitmap) {
        val filename = "quote_share_${System.currentTimeMillis()}.png"

        // Save the image to the cache directory
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs() // Ensure the directory exists

        val file = File(cachePath, filename)
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.close()

        // Get the URI for the file
        val imageUri = FileProvider.getUriForFile(context, "com.mksolution.buddasspring.provider", file)

        // Share the image using an Intent
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = "image/png"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share image via"))
    }
}