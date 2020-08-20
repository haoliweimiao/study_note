# Question

+ [无法加载视频缩略图](#无法加载视频缩略图)

## 无法加载视频缩略图

错误日志：MediaMetadataRetrieverJNI: getFrameAtTime: videoFrame is a NULL pointer
单独编写demo检测设备是否支持该视频的缩略图加载

``` java
 ImageView ivContent = findViewById(R.id.iv_content);
            @SuppressLint("SdCardPath") File file = new File("/sdcard/ZKTeco/commonres/advideo/0001.MP4");
            Log.e("!!!!!!!!!", "file exist: " + file.exists() + " " + file.getAbsolutePath());

            // 使用系统方法获取
            Bitmap bMap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
            ivContent.setImageBitmap(bMap);
 
            // Glide方法加载
//            Glide.with(getApplicationContext())
//                    .load(file.getAbsolutePath())
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(ivContent);

            // 使用系统方法获取
//            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//            mmr.setDataSource(file.getAbsolutePath());
//            Bitmap firstFrame = mmr.getFrameAtTime();
//            ivContent.setImageBitmap(firstFrame);

            // 使用第三方库FFmpegMediaMetadataRetriever获取
//            FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
//            mmr.setDataSource(file.getAbsolutePath());
//            mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM);
//            mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST);
//            Bitmap b = mmr.getFrameAtTime(2000000/2, FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC); // frame at 2 seconds
//            ivContent.setImageBitmap(b);
//            mmr.release();
```


如上述方式全部无法获取缩略图，检查视频文件是否被锁死，导致无法正常获取资源

``` java
    // 检查代码是否使用该方法加载视频
    MediaPlayer mediaPlayer = new MediaPlayer();
    mediaPlayer.setDataSource(path);
    mediaPlayer.prepare();
    // 在合适的位置添加该代码，释放资源占用，避免其他地方无法正常使用该视频
    if (mediaPlayer != null) {
         mediaPlayer.release();
    }
```