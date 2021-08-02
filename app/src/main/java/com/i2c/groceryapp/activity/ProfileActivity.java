package com.i2c.groceryapp.activity;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.i2c.groceryapp.R;
import com.i2c.groceryapp.databinding.ActivityProfileBinding;
import com.i2c.groceryapp.model.Data;
import com.i2c.groceryapp.retrofit.APIClient;
import com.i2c.groceryapp.retrofit.APIInterface;
import com.i2c.groceryapp.retrofit.RequestParam;
import com.i2c.groceryapp.retrofit.response.RestResponse;
import com.i2c.groceryapp.utils.BaseActivity;
import com.i2c.groceryapp.utils.Constant;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static okhttp3.MediaType.parse;

public class ProfileActivity extends BaseActivity {
    ActivityProfileBinding binding;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public static Uri selectedImage;
    File compressedImageFile = null;
    File file;
    private String Gst_image_name, TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        setUpControls();
    }

    @Override
    protected void setContent() {
    }

    private void setUpControls() {
            if (sessionManager.isKeyExist(Constant.PROFILE_IMAGE)) {
                Glide.with(this)
                        .load(sessionManager.getStringValue(Constant.PROFILE_IMAGE))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                binding.pbImage.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                           DataSource dataSource, boolean isFirstResource) {
                                binding.pbImage.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(binding.ivProfileImage);
            }else{
                Glide.with(this)
                        .load(R.mipmap.ic_launcher)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                binding.pbImage.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                           DataSource dataSource, boolean isFirstResource) {
                                binding.pbImage.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(binding.ivProfileImage);
            }

            binding.tvUserName.setText(sessionManager.getLoginData().getName());

            Log.e(TAG, "setUpControls: ALL::: "+sessionManager.getLoginData());
            if(sessionManager.getLoginData().getShipping_address()!=null){
                binding.etShippingAdd.setText(sessionManager.getLoginData().getShipping_address());
        }

        if (sessionManager.getLoginData().getUser_type().equals("0")) {
            /* user type = 0 customer*/
            binding.constShop.setVisibility(View.GONE);
        } else {
            /*seller*/
            binding.constShop.setVisibility(View.GONE);
        }

        binding.constProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, Personal_InfoActivty.class));
            }
        });

        binding.constRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getApplication().getPackageName()));
                Log.e("TAG", "onClick:PACKAGE NAME" + getApplication().getPackageName());
                startActivity(i);            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.rlUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(ProfileActivity.this)
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        selectImage();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).check();
            }
        });

        binding.etShippingAdd.setEnabled(false);

        binding.tvChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.tvChangeAddress.getText().toString().equals(getResources().
                        getString(R.string.change))){

                    binding.etShippingAdd.setEnabled(true);
                    binding.tvChangeAddress.setText(getResources().getString(R.string.save));

                }else {
                    checkValidation();
                }
            }
        });
    }

    private void checkValidation() {
        if (binding.etShippingAdd.getText().length() == 0) {
            showToast(getResources().getString(R.string.enter_shipping_address));
        } else {

            if (!isInternetOn(this)) {
                showToast(getResources().getString(R.string.check_internet));
                return;
            }

            showCustomLoader(this);
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

            Call<RestResponse<Data>> callAPi = apiInterface.update_location(
                    sessionManager.getStringValue(Constant.API_TOKEN),
                    binding.etShippingAdd.getText().toString());

            callAPi.enqueue(new Callback<RestResponse<Data>>() {
                @Override
                public void onResponse(Call<RestResponse<Data>> call, Response<RestResponse<Data>> response) {
                    Log.e(TAG, "onResponse: ADDRESS:::::"+new Gson().toJson(response.body()));

                    if (response.body() != null) {
                        if (response.body().getSuccess().equals("1")) {
                            sessionManager.saveLoginData(response.body().getData());
                            binding.tvChangeAddress.setText(getResources().getString(R.string.change));
                            binding.etShippingAdd.setEnabled(false);
                            showToast(response.body().getMessage());

                        } else if (response.body().getSuccess().equals("0")) {
                            showToast(response.body().getMessage());
                            binding.etShippingAdd.setEnabled(true);
                        } else {
                            showToast(response.body().getMessage());
                            binding.etShippingAdd.setEnabled(true);
                        }

                    }else if(response.code()==404){
                        showToast("Address is not updated");
                    }
                    dismissCustomLoader();
                }

                @Override
                public void onFailure(Call<RestResponse<Data>> call, Throwable t) {
                    dismissCustomLoader();
                }
            });
        }

    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                selectedImage = data.getData();

                String filePath = null;
                if (selectedImage != null && "content".equals(selectedImage.getScheme())) {
                    Cursor cursor = getContentResolver().query(selectedImage, new String[]{MediaStore.Images.ImageColumns.DATA},
                            null, null, null);
                    cursor.moveToFirst();
                    filePath = cursor.getString(0);
                    cursor.close();
                } else {
                    filePath = selectedImage.getPath();
                }
                file = new File(filePath);

                try {
                    compressedImageFile = new Compressor(this).compressToFile(file);
                    file = compressedImageFile;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Gst_image_name = file.getName();
                Log.e(TAG, "onActivityResult:!!!!!!!! " + file.getName());

                call_update_user_image(file.getName());
                binding.ivProfileImage.setImageURI(selectedImage);

            } else if (requestCode == REQUEST_CAMERA) {

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                file = destination;

                Gst_image_name = file.getName();

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                binding.ivProfileImage.setImageBitmap(thumbnail);
                call_update_user_image(file.getName());
                Log.e(TAG, "onActivityResult:@@@@@@@ " + file.getName());
            }

        }
    }


    public void call_update_user_image(String file_name) {
        if (!isInternetOn(this)) {
            showToast(getString(R.string.check_internet));
            return;
        }

        showCustomLoader(this);

        MultipartBody.Part body = null;
        if (file_name != null) {
            RequestBody requestBody = RequestBody.create(parse("image/*"), file);
            body = MultipartBody.Part.createFormData(RequestParam.PROFILE_IMAGE, file_name, requestBody);
        } else {
            RequestBody requestBody = RequestBody.create(parse("image/*"), "");
            body = MultipartBody.Part.createFormData(RequestParam.PROFILE_IMAGE, "", requestBody);
        }

        RequestBody api_token = RequestBody.create(parse("text/plain"),
                sessionManager.getStringValue(Constant.API_TOKEN));

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<RestResponse<Data>> call = apiInterface.update_profile_logo(body, api_token);

        call.enqueue(new Callback<RestResponse<Data>>() {
            @Override
            public void onResponse(Call<RestResponse<Data>> call, Response<RestResponse<Data>> response) {
                if (response.body() != null) {
                    Log.e(TAG, "onResponse:ccc:::::" + new Gson().toJson(response.body()));

                    if (response.body().getSuccess().equals("1")) {
                        sessionManager.saveLoginData(response.body().getData());
                        sessionManager.setStringValue(Constant.PROFILE_IMAGE, response.body().getData().getLogo());
                        showToast(response.body().getMessage());


                    } else if (response.body().getSuccess().equals("0")) {
                        showToast(response.body().getMessage());

                    } else if (response.body().getSuccess().equals("404")) {
                        sessionManager.logoutUser();
                        showToast(response.body().getMessage());
                        launchActivityWithClearStack(ProfileActivity.this, LoginActivity.class);

                    } else if (response.body().getSuccess().equals("2")) {
                        showToast(response.body().getMessage());
                    }

                }else if(response.code()==404){
                    showToast("Image is not uploaded");
                }
                dismissCustomLoader();
            }

            @Override
            public void onFailure(Call<RestResponse<Data>> call, Throwable t) {
                dismissCustomLoader();
            }
        });
    }

}