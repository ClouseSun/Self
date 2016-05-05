package com.abition.self;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.provider.MediaStore.Images;

import org.json.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    private static MeFragment mInstance;

    CardView resetView;
    CardView shareView;
    ImageView avatar;
    String imageId = null;
    String imageURL = null;
    BmobFile image;

    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment getInstance() {
        if (mInstance == null) {
            mInstance = new MeFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_me, container, false);
        resetView = (CardView) rootView.findViewById(R.id.cv_reset_psw);

        shareView = (CardView) rootView.findViewById(R.id.cv_share);
        avatar = (ImageView) rootView.findViewById(R.id.iv_avatar);

        resetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).pswResetDialog.show((getActivity()).getSupportFragmentManager(), null);

            }
        });

        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareInt = new Intent();
                shareInt.setAction("Intent.ACTION_SEND");
                shareInt.putExtra(Intent.EXTRA_TEXT, "Sharing From SELF");
                shareInt.setType("text/plain");
                startActivity(Intent.createChooser(shareInt, "Sharing to"));

            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 111);
            }
        });

        final BmobUser user = BmobUser.getCurrentUser(getActivity());
        TextView idView = (TextView) rootView.findViewById(R.id.text_user_id);
        idView.setText(user.getUsername());

        BmobConfig config = new BmobConfig.Builder()
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setBlockSize(500 * 1024)
                .build();
        Bmob.getInstance().initConfig(config);

        BmobQuery<UserImageTable> query = new BmobQuery<UserImageTable>();
        query.addWhereEqualTo("user_id", user.getObjectId());
        query.setLimit(50);

        query.findObjects(getActivity(), new FindListener<UserImageTable>() {
            @Override
            public void onSuccess(List<UserImageTable> list) {
                for (final UserImageTable imageTable : list) {
                    BmobFile bmobfile = new BmobFile(user.getObjectId() + ".png", "", imageTable.getImageURL());
                    bmobfile.download(getActivity(), new DownloadFileListener() {
                        @Override
                        public void onSuccess(String s) {
                            Bitmap bitmap = BitmapFactory.decodeFile(s);
                            avatar.setImageBitmap(bitmap);
                            imageId = imageTable.getObjectId();
                            imageURL = imageTable.getImageURL();
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView resetPasswordView = (TextView) getView().findViewById(R.id.view_reset_password);
        TextView signOutView = (TextView) getView().findViewById(R.id.view_sign_out);
        signOutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser user = BmobUser.getCurrentUser(getActivity());
                user.logOut(getActivity());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 111) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();
            ContentResolver cr = getActivity().getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                avatar.setImageBitmap(bitmap);
                image = new BmobFile(getFileByUri(uri));
                image.uploadblock(getActivity(), new UploadFileListener() {
                    @Override
                    public void onSuccess() {
                        BmobUser user = BmobUser.getCurrentUser(getActivity());
                        BmobFile file = new BmobFile();

                        UserImageTable userImageTable = new UserImageTable(user.getObjectId(), image.getFileUrl(getActivity()));
                        if (imageId != null) {
                            userImageTable.update(getActivity(), imageId, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    BmobFile file = new BmobFile();
                                    file.setUrl(imageURL);//此url是上传文件成功之后通过bmobFile.getUrl()方法获取的。
                                    file.delete(getActivity(), new DeleteListener() {

                                        @Override
                                        public void onSuccess() {
                                            Log.i("@@@@@@@@", "成功");
                                        }

                                        @Override
                                        public void onFailure(int code, String msg) {
                                            Log.i("@@@@@@@@@", "文件删除失败：" + code + ",msg = " + msg);
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Log.i("@@@@@@@@@@", "success");
                                }
                            });
                        } else {
                            userImageTable.save(getActivity(), new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    Log.i("@@@@@@@@@@", "success");
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Log.i("@@@@@@@@@@", s);
                                }
                            });
                        }
                    }

                    @Override
                    public void onProgress(Integer value) {
                        super.onProgress(value);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.i("@@@@@@@@@@", "success");
                    }
                });
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 通过Uri返回File文件
     * 注意：通过相机的是类似content://media/external/images/media/97596
     * 通过相册选择的：file:///storage/sdcard0/DCIM/Camera/IMG_20150423_161955.jpg
     * 通过查询获取实际的地址
     *
     * @param uri
     * @return
     */
    public File getFileByUri(Uri uri) {
        String path = null;
        path = uri.getEncodedPath();
        if (path != null) {
            path = Uri.decode(path);
            ContentResolver cr = getActivity().getContentResolver();
            StringBuffer buff = new StringBuffer();
            buff.append("(").append(Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
            Cursor cur = cr.query(Images.Media.EXTERNAL_CONTENT_URI, new String[]{Images.ImageColumns._ID, Images.ImageColumns.DATA}, buff.toString(), null, null);
            int index = 0;
            int dataIdx = 0;
            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                index = cur.getColumnIndex(Images.ImageColumns._ID);
                index = cur.getInt(index);
                dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cur.getString(dataIdx);
            }
            cur.close();
            if (index == 0) {
            } else {
                Uri u = Uri.parse("content://media/external/images/media/" + index);
                System.out.println("temp uri is :" + u);
            }
        }
        return new File(path);
    }

}
