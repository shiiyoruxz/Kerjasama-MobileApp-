package com.gremoryyx.kerjasama

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

class RegisterSetupProfilePictureFragment : Fragment() {

    private lateinit var username: String
    private var ProfilePictureListener: RegisterSetupProfilePictureFragment.OnProfilePictureFragmentInteractionListener? = null
    private val getPhoto = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        if(uri != null){
            requireView().findViewById<ImageView>(R.id.register_profile_picture).setImageURI(uri)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_setup_profile_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getView()?.findViewById<TextView>(R.id.register_username_profile_label)?.text = username

        // Save profile picture on click listener
        val image = readProfilePicture()
        if(image != null){
            getView()?.findViewById<ImageView>(R.id.register_profile_picture)?.setImageBitmap(image)
        }

        // Image on click listener to open the gallery
        getView()?.findViewById<ImageView>(R.id.register_profile_picture)?.setOnClickListener {
            getPhoto.launch("image/*")

        }

    }

    interface OnProfilePictureFragmentInteractionListener {
        fun onLoginInfoFragmentInteraction(data: Bundle)
    }

    public fun setUsername(text: String) {
        username = text
    }

    private fun saveProfilePicture(view: View) {
        val filename = "profile.png"
        val file = File(this.context?.filesDir, filename)
        val image = view as ImageView

        val bd = image.drawable as BitmapDrawable
        val bitmap = bd.bitmap
        val outputStream: OutputStream

        try{
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
            outputStream.flush()
            outputStream.close()

            // Add firebase code here


        } catch (e: FileNotFoundException){
            Toast.makeText(requireContext(), "Image not found!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun readProfilePicture(): Bitmap? {
        val filename = "profile.png"
        val file = File(this.context?.filesDir, filename)

        if(file.isFile){
            try{
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                return bitmap
            }catch (e: FileNotFoundException){
                e.printStackTrace()
            }
        }
        return null
    }

}