package com.base.utils.image;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.base.config.ConstUtil;
import com.base.config.GlobalConfig;
import com.mvp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.*;
import java.net.URL;

/**
 * 从DrawableUtil类中分离出来这个类,为了使代码看起来更加简洁明了
 * 这个类是调用相册相机时,所需要的一些方法
 */
public class SelPhotoUtils {


    public static Bitmap revitionImageSize(String path, int max_w, int max_h) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= max_w)
                    && (options.outHeight >> i <= max_h)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }
    /**
     * 生成一个图片的保存的路径
     *
     * @param context 使用Activity可以调用
     * @return 返回生成好的可以保存图片路径
     */
    public static Uri genImgFileUri(Context context) {
        String IMAGE_FILE_NAME = "faceImage" + System.currentTimeMillis() + ".jpg";
        // 判断存储卡是否可以用，可用进行存储
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            Uri icoUri = Uri.fromFile(new File(ConstUtil.Start_ImgkScardurl, IMAGE_FILE_NAME));
            return icoUri;
        } else {
            Toast.makeText(context, "请检查SD卡是否可用。", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * 生成一个图片的保存的路径
     *
     * @param context 使用Activity可以调用
     * @return 返回生成好的可以保存临时图片路径
     */
    public static Uri gettempImgFileUri(Context context) {
        String IMAGE_FILE_NAME = "faceImage" + System.currentTimeMillis() + "temp" + ".jpg";
        // 判断存储卡是否可以用，可用进行存储
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {

            Uri icoUri = Uri.fromFile(new File(ConstUtil.Start_ImgkScardurl, IMAGE_FILE_NAME));

            File fi = new File(ConstUtil.Start_ImgkScardurl);
            if (!fi.exists()) {
                fi.mkdir();
            }

            return icoUri;
        } else {
            Toast.makeText(context, "请检查SD卡是否可用。", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * 生成一个s视频录制的保存的路径
     *
     * @param context 使用Activity可以调用
     * @return 返回生成好的可以保存图片路径
     */
    public static Uri genVideoFileUri(Context context) {
        String IMAGE_FILE_NAME = "faceVideo" + System.currentTimeMillis() + ".mp4";
        // 判断存储卡是否可以用，可用进行存储
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            Uri icoUri = Uri.fromFile(new File(ConstUtil.Start_ImgkScardurl, IMAGE_FILE_NAME));
            return icoUri;
        } else {
            Toast.makeText(context, "请检查SD卡是否可用。", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    /**
     * 判断制定路径是否存在文件
     *
     * @param imgurl 图片路径  /upload/2014-04-23/adsads324.jpg
     * @return
     */
    public static boolean iwsExistLocal(String imgurl) {
        File file = new File(imgurl);
        if (file.exists())
            return true;
        else
            return false;
    }


    /**
     * 将Bitmap保存到本地
     *
     * @param imgurl 不带file了开头的路径,如:  /strange/sdcard/asdasd.jpg
     * @param bitmap 将bitmap保存到Imgurl
     */
    public static void savePictureToLocal(String imgurl, Bitmap bitmap) {
        FileOutputStream b = null;
        File file = new File(imgurl);
        file.getParentFile().mkdirs();// 创建文件夹


        //对图片质量进行压缩,这样保存的图片就变小了
        int yasuolv = 60;
        try {
            b = new FileOutputStream(imgurl);
            bitmap.compress(Bitmap.CompressFormat.JPEG, yasuolv, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
                //bitmap.recycle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 当你从手机中选择一个图片的时候  有可能需要旋转 先获得图片
     * 读取图片需要旋转的角度
     *
     * @param path
     * @return 角度  如:90
     */
    public static int getPictureDegree(String path) {
        try {
            int degree = 0;

            ExifInterface exifInterface1 = new ExifInterface(path);

            int orientation = exifInterface1.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
            }

            return degree;
        } catch (Exception e) {
            Log.d("获得图像旋转", "失败");
            e.printStackTrace();
        }
        return 0;
    }

    //重新保存图片  将文件大小变小  保存成功返回true
    public static boolean reSaveSamllImg(String input, String output) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = new BufferedInputStream(new URL("file://" + input).openStream());
            ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            options.inTempStorage = new byte[5 * 1024];

            //允许系统在需要的时候回收内存
            options.inPurgeable = true;
            options.inInputShareable = true;
            //降低内存至一半,但是没有了透明度
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            bitmap = BitmapFactory.decodeStream(in, null, options);
            in.close();
            dataStream.close();

            //准备执行保存步骤
            savePictureToLocal(output, bitmap);
            return true;
        } catch (OutOfMemoryError ex) {
            System.gc();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据图片信息的进行旋转(非异步)
     *
     * @param input  输入路径图片
     * @param output 旋转后的图片
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static void rotateBmp(String input, String output) {
        Bitmap bMap = null;
        Bitmap bmp = null;
        try {
            int degree = 0;
            degree = getPictureDegree(input);
            if (degree == 0) {
                Log.d("图像", "不需要旋转");
            }

            bmp = revitionImageSize(input, GlobalConfig.scrwid,GlobalConfig.scrhei);

            Log.d("bmp消耗内存", bMap.getByteCount() / 1024 + "KB");

            float zoom = 0.6f;
//            if(bmp.getWidth()>1000)//宽带如果大于1000
//                zoom=1000/bmp.getWidth();

            // 定义矩阵对象
            Matrix matrix = new Matrix();
            // 缩放原图
            matrix.postScale(zoom, zoom);
            // 向左旋转45度，参数为正则向右旋转
            matrix.postRotate(degree);
            //bmp.getWidth(), 500分别表示重绘后的位图宽高
            bMap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

            if ( bmp!= null&& !bmp.isRecycled())
                bmp.recycle();
            // 在画布上绘制旋转后的位图
            savePictureToLocal(output, bMap);
            Log.d("图像", "成功  zoom:" + zoom + " ");
            Log.d("bMap消耗内存", bMap.getByteCount() / 1024 + "KB");
            //旋转之后的其他处理bigen  (请在此添加代码 )(如果添加代码,建议把这个rotateBmp()方法复制到你的Activity中)

            //旋转之后的其他处理end  即将回收内存
            if (bMap != null&& !bmp.isRecycled())
                bMap.recycle();
        } catch (OutOfMemoryError error) {
            if (bMap != null&& !bMap.isRecycled())
                bMap.recycle();
            if (bmp != null&& !bmp.isRecycled())
                bmp.recycle();
            Log.d("rotateBmp()", "内存溢出");
        } catch (Exception e) {
            Log.d("图像", "失败");
            e.printStackTrace();
        }
    }


    /**
     * 使用ImageLoder根据图片信息的进行旋转(异步)
     *
     * @param input  输入路径图片
     * @param output 旋转后的图片
     */
    public static void asyncImageLoderRotateBmp(final String input, final String output) {
        //新建一个DisplayImageOption  让内存更低
        DisplayImageOptions opendiskcache_options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.empty_photo)
                .showImageOnFail(R.drawable.empty_photo)
                .cacheInMemory(false)//这次载入不启用RAM内存,但是这里设置没用,以为这里是loadImage必须要返回一个Bitmap到内存中的
                .cacheOnDisc(true)  //而是使用硬盘缓存
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)//不适用ARGB_888内存降低一半
                .build();
        //imagerloder会自动根据屏幕分辨率来进行图片大小的压缩,来控制内存
        ImageLoader.getInstance().loadImage("file:/" + input, opendiskcache_options, new SimpleImageLoadingListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                Bitmap bMap = null;
                try {

                    int degree = 0;
                    degree = SelPhotoUtils.getPictureDegree(input);
                    if (degree == 0) {
                        Log.d("图像", "不需要旋转");
                    }

                    float zoom = 0.6f;
                    // 定义矩阵对象
                    Matrix matrix = new Matrix();
                    // 缩放原图
                    matrix.postScale(zoom, zoom);
                    // 向左旋转45度，参数为正则向右旋转
                    if (degree != 0)
                        matrix.postRotate(degree);
                    //bmp.getWidth(), 500分别表示重绘后的位图宽高
                    bMap = Bitmap.createBitmap(loadedImage, 0, 0, loadedImage.getWidth(), loadedImage.getHeight(), matrix, true);
                    Log.d("loadedImage字节数", loadedImage.getByteCount() + "");
                    if (loadedImage != null && !loadedImage.isRecycled())
                        loadedImage.recycle();

                    savePictureToLocal(output, bMap);
                    Log.d("图像字节数", "成功  zoom:" + zoom + " ");
                    Log.d("bMap消耗内存", bMap.getByteCount() / 1024 + "KB");


                    //旋转之后的其他处理bigen  (请在此添加代码 )(如果添加代码,建议把这个rotateBmp()方法复制到你的Activity中)

                    //旋转之后的其他处理end  即将回收内存
                    if (bMap != null && !bMap.isRecycled())
                        bMap.recycle();
                } catch (OutOfMemoryError error) {
                    if (bMap != null && !bMap.isRecycled())
                        bMap.recycle();
                    if (loadedImage != null && !bMap.isRecycled())
                        loadedImage.recycle();

                    error.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }


    /**
     * 根据图片信息的进行异步旋转
     *
     * @param _input  输入路径图片
     * @param _output 旋转后的图片
     */
    public static void asyncRotateBmp(final String _input, final String _output) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... voids) {
                Bitmap bmp = null;
                try {
                    int degree = 0;
                    degree = getPictureDegree(_input);
                    if (degree == 0) {
                        Log.d("图像", "不需要旋转");
                    }

                    bmp = BitmapUtils.GetLocalOrNetBitmap("file://" + _input, true,700,1200);

                    float zoom = 0.6f;
//            if(bmp.getWidth()>1000)//宽带如果大于1000
//                zoom=1000/bmp.getWidth();

                    // 定义矩阵对象
                    Matrix matrix = new Matrix();
                    // 缩放原图
                    matrix.postScale(zoom, zoom);
                    Log.d("图像", "成功  zoom:" + zoom + " ");
                    // 向左旋转45度，参数为正则向右旋转
                    matrix.postRotate(degree);
                    //bmp.getWidth(), 500分别表示重绘后的位图宽高
                    return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
                } catch (OutOfMemoryError error) {
                    error.printStackTrace();
                    if (bmp != null&&!bmp.isRecycled())
                        bmp.recycle();

                    error.printStackTrace();
                    return null;
                }

            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
            @Override
            protected void onPostExecute(Bitmap bitmap_) {
                super.onPostExecute(bitmap_);
                if (bitmap_ == null)
                    return;
                Log.d("bMap消耗内存", bitmap_.getByteCount() / 1024 + "KB");
                savePictureToLocal(_output, bitmap_);
                //旋转之后的其他处理bigen  (请在此添加代码)(如果添加代码,建议把这个rotateBmp()方法复制到你的Activity中)

                //旋转之后的其他处理end  即将回收内存
                if (bitmap_ != null&&!bitmap_.isRecycled())
                    bitmap_.recycle();
            }
        }.execute();

    }


    /**
     * @param context 传递context是用来获得系统的相册的权限
     * @param bmp     待保存的bitmap
     * @return
     */
    public static String saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String path = appDir + fileName;
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        return path;
    }

    //保存Bitmap到图库,但是不返回路径
    public static void saveImageToGalleryNoPath(Context context, Bitmap bitmap) {
        MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "title", "description");//title decription同样需要插入数据库,这两个字段没多大用处
    }
}
