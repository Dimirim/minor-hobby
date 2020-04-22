package com.dimirim.minorhobby.ui.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.text.Spanned;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import com.dimirim.minorhobby.R;

import java.io.File;

import static android.graphics.Typeface.BOLD;


public class RegisterActivity extends AppCompatActivity implements  View.OnClickListener{
    private int id_view;

    private static final int PICK_FROM_CAMERA = 0; //사진촬영 ->이미지 처리
    private static final int PICK_FROM_ALBUM = 1; //앨범 사진 선택 -> 이미지 처리
    private static final int CROP_FROM_IMAGE = 2; //이미지 크롭

    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto;
    private int Id_view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView txt = (TextView) findViewById(R.id.text1);
        Spannable sp = (Spannable) txt.getText();
        sp.setSpan(new StyleSpan(Typeface.BOLD), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        iv_UserPhoto = (ImageView)this.findViewById(R.id.user);
        LinearLayout rl = (LinearLayout)findViewById((R.id.touchlayout));
        rl.setOnClickListener(this);


    }

    //카메라에서 사진 활영하는 함수
    public void doTakePhotoAction(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //임시 사용 파일 경로 생성
        String url = "tmp_"+String.valueOf(System.currentTimeMillis())+".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),url));

        intent.putExtra(MediaStore.EXTRA_OUTPUT,mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    //엘범에서 이미지 가져오는 함수
    public void doTakeAlbumAction(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode != RESULT_OK) return;

        switch(requestCode){
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel",mImageCaptureUri.getPath().toString());
            }
            case PICK_FROM_CAMERA: {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri,"image/*");
                intent.putExtra("outputX",200);
                intent.putExtra("outputY",200);
                intent.putExtra("aspectX",1);
                intent.putExtra("aspectY",1);
                intent.putExtra("scale",true);
                startActivityForResult(intent, CROP_FROM_IMAGE);
            }
            case CROP_FROM_IMAGE: {
                if(resultCode != RESULT_OK){
                    return;
                }
                final Bundle extras = data.getExtras();
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel/"+System.currentTimeMillis()+".jpg";

/*                if(extras != null){
                    Bitmap photo = extras.getParcelable("data");
                    iv_UserPhoto.setImageBitmap(photo);
                }*/
            }
        }
    }
    @Override

    public void onClick(View v) {

        id_view = v.getId();

        if(v.getId() == R.id.nextbutton) {
            //건너뛰기 이벤트
        }else if(v.getId() == R.id.touchlayout) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakePhotoAction();
                }
            };
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakeAlbumAction();
                }
            };
            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
            new AlertDialog.Builder(this)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진촬영", cameraListener)
                    .setNeutralButton("앨범선택", albumListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();
        }
    }

}
