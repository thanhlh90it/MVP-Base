package vmodev.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by thanhle on 3/29/17.
 */

public class ImageUtil {

    public static final int photoWidth = 600;
    public static final int photoHeight = 600;

    public static void correctImage(Context context, String url) {
        System.gc();
        int orientation = ImageUtil.getCameraPhotoOrientation(context, url);
        String TAG = "B2B : resize";
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 200000; // 0.2MP
            in = new FileInputStream(url);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();

            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) > IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d(TAG, "scale = " + scale + ", orig-width: " + o.outWidth
                    + ", orig-height: " + o.outHeight);

            Bitmap b = null;
            in = new FileInputStream(url);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                System.gc();
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                Log.d(TAG, "1th scale operation dimenions - width: " + width
                        + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;
                System.gc();
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                if(orientation!=0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(orientation);
                    System.gc();
                    Bitmap rotateBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(),
                            scaledBitmap.getHeight(),matrix,true );
                    b.recycle();
                    b = rotateBitmap;
                }else {
                    b.recycle();
                    b = scaledBitmap;
                }
            } else {
                System.gc();
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            Log.d(TAG, "bitmap size - width: " + b.getWidth() + ", height: "
                    + b.getHeight());
            File file = new File(url);
            file.deleteOnExit();
            System.gc();
            FileOutputStream out = new FileOutputStream(url);
            b.compress(Bitmap.CompressFormat.JPEG,90,out);
            b.recycle();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public static int getCameraPhotoOrientation(Context context, String imagePath){
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Log.i("RotateImage", "Exif orientation: " + orientation);
            Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static String getRightAngleOfPhoto(String photoPath) {
        try {
            ExifInterface ei = new ExifInterface(photoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int degree = 0;

            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    degree = 0;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                case ExifInterface.ORIENTATION_UNDEFINED:
                    degree = 0;
                    break;
                default:
                    degree = 90;
            }
            rotatePhoto(degree, photoPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return photoPath;
    }

    public static String rotatePhoto(int degree, String imagePath) {
        if (degree <= 0) {
            return imagePath;
        }
        try {
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, bitmapOptions);
            int photoW = Math.max(bitmapOptions.outWidth, 800);
            int photoH = Math.max(bitmapOptions.outHeight, 800);
            int targetW = photoWidth;
            int targetH = photoHeight;

//            Figure out which way needs to be reduced less
            int scaleFactor = Math.max(photoW / targetW, photoH / targetH);
            bitmapOptions.inJustDecodeBounds = false;
            bitmapOptions.inSampleSize = scaleFactor;
            bitmapOptions.inPurgeable = true;

//             Decode the JPEG file into a Bitmap
            System.gc();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bitmapOptions);

            Matrix matrix = new Matrix();
            if (bitmap.getWidth() > bitmap.getHeight()) {
                matrix.setRotate(degree);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }

            FileOutputStream fOut = new FileOutputStream(imagePath);
            String imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
            String imageType = imageName.substring(imageName.lastIndexOf(".") + 1);

            FileOutputStream out = new FileOutputStream(imagePath);
            if (imageType.equalsIgnoreCase("png")) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } else if (imageType.equalsIgnoreCase("jpeg") || imageType.equalsIgnoreCase("jpg")) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            }
            fOut.flush();
            fOut.close();
            bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagePath;
    }
}
