package com.nopal.awokowokwok

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.PermissionRequest
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.nopal.awokowokwok.data.AppDatabase
import com.nopal.awokowokwok.data.MyFriendDao
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.main_frame_fragment.*
import kotlinx.android.synthetic.main.my_friend_add_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.security.Permission
import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import kotlinx.android.synthetic.main.my_friend_item.*
import java.io.ByteArrayOutputStream
import java.io.IOException


class addfriend: Fragment(),
    com.fondesa.kpermissions.request.PermissionRequest.AcceptedListener, com.fondesa.kpermissions.request.PermissionRequest.DeniedListener{

    override fun onPermissionsDenied(permissions: Array<out String>) {
        requestPermission()
    }

    override fun onPermissionsAccepted(permissions: Array<out String>) {
        showPictureDialog()
    }

    private val GALLERY = 1
    private val CAMERA = 2

    companion object{
        fun newInstance() : addfriend {
            return addfriend()
        }
    }
    private var namaInput : String = ""
    private var emailInput : String = ""
    private var telpInput : String = ""
    private var alamatInput : String = ""
    private var genderInput : String = ""
    private var imageByte : ByteArray? = null


    private var db: AppDatabase? = null
    private var MyFriendDao: MyFriendDao? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_friend_add_fragment, container, false)
    }

    fun showPictureDialog(){
        val picturedialog = AlertDialog.Builder(activity!!)
        picturedialog.setTitle("Silahkan Pilih")
        val picturedialogitem = arrayOf("Ambil foto dari galeri","Ambil foto dari kamera")
        picturedialog.setItems(picturedialogitem){ dialog, which ->
            when (which){
                0 -> choosephotofromgallery()
                1 -> takephotofromgalery()
            }
        }
        picturedialog.show()
    }

    private fun takephotofromgalery() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    private fun choosephotofromgallery() {
        val galleyintent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleyintent, GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY){
            if (data != null){
                val contextURI = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        activity!!.contentResolver, contextURI)
                    setImageProfile(bitmap)
                } catch (e: IOException){
                    e.printStackTrace()
                    Toast.makeText(activity, "Failed!",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }else if (requestCode == CAMERA){
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            setImageProfile(thumbnail)
        }
    }

    private fun checkversiandroid(){
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.M){
            requestPermission()
        }else{
            showPictureDialog()
        }
    }

    private fun requestPermission(){
        val request = permissionsBuilder(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE).build()

        request.acceptedListener(this)
        request.deniedListener(this)

        request.send()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSave.setOnClickListener{
            validasiInput()

        }
        initLocalDB()
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDataBase(activity!!)
        MyFriendDao = db?.MyFriendDao()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

    private fun tampilToast(message: String){
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

    fun setImageProfile(bitmap: Bitmap){
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)

        imgProfile.setImageBitmap(BitmapFactory.decodeByteArray
            (stream.toByteArray(), 0, stream.toByteArray().size))
        imageByte = stream.toByteArray()
    }

    private fun validasiInput() {
        namaInput = edtName.text.toString()
        emailInput = edtEmail.text.toString()
        telpInput = edtTelp.text.toString()
        alamatInput = edtAddress.text.toString()
        genderInput = spinnerGender.selectedItem.toString()

        when{
            namaInput.isEmpty() -> edtName.error = "Nama tidak boleh kosong"
            genderInput.equals("Pilih Kelamin") -> tampilToast("Kelamin harus diisi")
            emailInput.isEmpty() -> edtEmail.error = "email tidak boleh kosong"
            telpInput.isEmpty() -> edtTelp.error = "no telp tidak boleh kosong"
            alamatInput.isEmpty() -> edtAddress.error = "alamat tidak boleh kososng"

            else -> {
                val friend = mapren(
                    nama = namaInput,
                    kelamin = genderInput,
                    email = emailInput,
                    telp = telpInput,
                    alamat = alamatInput,
                    image = imageByte)
                tambahDataTeman(friend)

            }
        }
    }

    private fun tambahDataTeman(teman: mapren) : Job{
        return GlobalScope.launch { MyFriendDao?.tambahTeman(teman)
            (activity as MainActivity).tampilMyFriendFragment()
        }
    }

    }