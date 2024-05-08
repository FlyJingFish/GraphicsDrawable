# GraphicsDrawable

[![Maven central](https://img.shields.io/maven-central/v/io.github.FlyJingFish/GraphicsDrawable)](https://central.sonatype.com/artifact/io.github.FlyJingFish/GraphicsDrawable/)
[![GitHub stars](https://img.shields.io/github/stars/FlyJingFish/GraphicsDrawable.svg)](https://github.com/FlyJingFish/GraphicsDrawable/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/FlyJingFish/GraphicsDrawable.svg)](https://github.com/FlyJingFish/GraphicsDrawable/network)
[![GitHub issues](https://img.shields.io/github/issues/FlyJingFish/GraphicsDrawable.svg)](https://github.com/FlyJingFish/GraphicsDrawable/issues)
[![GitHub license](https://img.shields.io/github/license/FlyJingFish/GraphicsDrawable.svg)](https://github.com/FlyJingFish/GraphicsDrawable/blob/master/LICENSE)



## GraphicsDrawable支持圆图、圆角图以及自定义图形；不再需要学习各个图片加载框架各种设置圆角，圆形图，甚至任何的特殊图形也无缝支持；不需要再让 UI 切各种圆角、圆形图或其他图形的 placeholder、error 资源图
## 最重要的是不挑显示控件
<img src="https://github.com/FlyJingFish/GraphicsDrawable/blob/master/screenshot/Screenshot_20240318_102848.jpg" width="400px" height="800px" alt="show" />

#### [点此下载apk,也可扫下边二维码下载](https://github.com/FlyJingFish/GraphicsDrawable/blob/master/apk/release/app-release.apk?raw=true)

<img src="/screenshot/qrcode.png" alt="show" width="200px" />

## 特色功能

1、支持圆形图案、圆角矩形图案、以及自定义图形，完美兼容所有的图片加载库

2、使用三方图片加载框架，不需要再让 UI 切各种圆角、圆形图或其他图形的 placeholder、error 资源图

3、矩形图片支持四个角独立设置角度值

4、完美兼容所有的图片加载库，支持定义任意图形，只有你想不到没有它做不到

5、**不挑显示控件，任何View都可以，只要支持 Drawable 就可以**



## 第一步、引入本库

```gradle
dependencies {
    //必选项
    implementation 'io.github.FlyJingFish:GraphicsDrawable:1.0.3'
    //可选项，如果您使用 Glide 则使用这个
    implementation 'io.github.FlyJingFish:GraphicsDrawableGlideLib:1.0.3'
    //可选项，如果您使用 Coil 则使用这个
    implementation 'io.github.FlyJingFish:GraphicsDrawableCoilLib:1.0.3'
}
```
## 第二步、使用说明

### 一、GraphicsDrawable 使用说明

| 方法名                         |              说明               |
|-----------------------------|:-----------------------------:|
| setDrawable                 |            设置绘制的图片            |
| setCustomDrawable           |          设置自定义的显示形状           |
| setBackgroundMode           |            设置背景模式             |
| setScaleType                |      设置自定义 ScaleType 的类型      |
| setShapeType                |            设置显示的形状            |
| setRadius                   |           设置矩形图的圆角值           |
| setRelativeRadius           |       设置矩形图的圆角值(适配 Rtl)       |
| setUseViewPadding           |      设置是否适应View的padding       |

- 四个角相等的矩形圆角图
```kotlin
val graphicsDrawable = GraphicsDrawable(view)
graphicsDrawable.setShapeType(GraphicsDrawable.ShapeType.RECTANGLE)
graphicsDrawable.setRadius(20.dp)
```
- 四个角不同的矩形圆角图
```kotlin
val graphicsDrawable = GraphicsDrawable(view)
graphicsDrawable.setShapeType(GraphicsDrawable.ShapeType.RECTANGLE)
graphicsDrawable.setRelativeRadius(10.dp,20.dp,30.dp,40.dp)

```
- 圆形图
```kotlin
val graphicsDrawable = GraphicsDrawable(view)
graphicsDrawable.setShapeType(GraphicsDrawable.ShapeType.OVAL)

```
- 自定义图形
```kotlin
val graphicsDrawable = GraphicsDrawable(view)
graphicsDrawable.setShapeType(GraphicsDrawable.ShapeType.CUSTOM)
graphicsDrawable.setCustomDrawable(R.drawable.ic_vector_flower)

```
**💡💡💡图形资源设置提示**

**setCustomDrawable** 就是让UI提前将图形导出的图片资源，可以是shape，可以是vector，可以是png图片，但是**强烈建议使用shape或vector矢量图形效果更佳**

如果使用是png或svg资源可以将其转化为vector，详情可以看我的博客：[博客使用说明](https://blog.csdn.net/u013077428/article/details/127613904)

- 将上述配置好的 GraphicsDrawable 设置给 View
```kotlin
//设置实际想要显示的Drawable
graphicsDrawable.setDrawable(d)
//给 ImageView 设置 GraphicsDrawable 即可显示
view.setImageDrawable(graphicsDrawable)
//给 view 设置背景
view.setBackground(graphicsDrawable)
```

### 二、Glide 使用

- ImageView 的使用，以下例子默认跟随 ImageView 的 ScaleType 显示


```kotlin
val pic1Drawable = GraphicsDrawable(view)
pic1Drawable.setShapeType(GraphicsDrawable.ShapeType.RECTANGLE)
pic1Drawable.setRadius(20.dp)
Glide
    .with(view)
    .load(url)
    .placeholder(R.drawable.placeholder)
    .error(R.drawable.error)
    .into(GlideGraphicsImageViewTarget(pic1Drawable))

```
- View 设置 背景

```kotlin
val pic1Drawable = GraphicsDrawable(view)
pic1Drawable.setShapeType(GraphicsDrawable.ShapeType.RECTANGLE)
pic1Drawable.setRadius(20.dp)

Glide
    .with(view)
    .load(url)
    .placeholder(R.drawable.placeholder)
    .error(R.drawable.error)
    .into(GlideGraphicsViewBackgroundTarget(pic1Drawable))
```

### 三、Coil 使用

- ImageView 的使用，以下例子默认跟随 ImageView 的 ScaleType 显示

```kotlin
val pic1Drawable = GraphicsDrawable(view)
pic1Drawable.setShapeType(GraphicsDrawable.ShapeType.RECTANGLE)
pic1Drawable.setRadius(20.dp)

val imageLoader = Coil.imageLoader(context)
val request = ImageRequest.Builder(context)
    .data(url)
    .placeholder(R.drawable.placeholder)
    .error(R.drawable.error)
    .target(CoilGraphicsImageViewTarget(pic1Drawable))//或 setGraphicsImageViewDrawable(pic1Drawable)
    .build()
    
imageLoader.enqueue(request)

// 或者用 Coil 里 ImageView 的拓展函数

imageView.load(url){
    placeholder(R.drawable.placeholder)
    error(R.drawable.error)
    setGraphicsImageViewDrawable(pic1Drawable)
}

```

- View 设置 背景

```kotlin
val pic1Drawable = GraphicsDrawable(view)
pic1Drawable.setShapeType(GraphicsDrawable.ShapeType.RECTANGLE)
pic1Drawable.setRadius(20.dp)

val imageLoader = Coil.imageLoader(context)
val request = ImageRequest.Builder(context)
    .data(url)
    .placeholder(R.drawable.placeholder)
    .error(R.drawable.error)
    .target(CoilGraphicsViewBackgroundTarget(pic1Drawable))//或 setGraphicsViewBackground(pic1Drawable)
    .build()
imageLoader.enqueue(request)

```

### 四、番外：使用 svg 资源图作为自定义图形

#### 如果想直接使用svg格式图可以这样做（不建议这样做，因为 svg 图可以直接转化为 vector 图，[点此查看转化说明](https://blog.csdn.net/u013077428/article/details/127613904)）

引用三方解析包

```gradle
dependencies {
    implementation 'com.caverock:androidsvg-aar:1.4'
}
```

**新增如下两个类**

- [SvgDecoder](https://github.com/FlyJingFish/GraphicsDrawable/tree/master/app/src/main/java/com/flyjingfish/GraphicsDrawable/svg/SvgDecoder.java)

```java
public class SvgDecoder implements ResourceDecoder<InputStream, SVG> {

    @Override
    public boolean handles(@NonNull InputStream source, @NonNull Options options) {
        // TODO: Can we tell?
        return true;
    }

    public Resource<SVG> decode(
            @NonNull InputStream source, int width, int height, @NonNull Options options)
            throws IOException {
        try {
            SVG svg = SVG.getFromInputStream(source);
            if (width != SIZE_ORIGINAL) {
                svg.setDocumentWidth(width);
            }
            if (height != SIZE_ORIGINAL) {
                svg.setDocumentHeight(height);
            }
            return new SimpleResource<>(svg);
        } catch (SVGParseException ex) {
            throw new IOException("Cannot load SVG from stream", ex);
        }
    }
}
```

- [SvgDrawableTranscoder](https://github.com/FlyJingFish/GraphicsDrawable/tree/master/app/src/main/java/com/flyjingfish/GraphicsDrawable/svg/SvgDrawableTranscoder.java)

```java
public class SvgDrawableTranscoder implements ResourceTranscoder<SVG, PictureDrawable> {
    @Nullable
    @Override
    public Resource<PictureDrawable> transcode(
            @NonNull Resource<SVG> toTranscode, @NonNull Options options) {
        SVG svg = toTranscode.get();
        Picture picture = svg.renderToPicture();
        PictureDrawable drawable = new PictureDrawable(picture);
        return new SimpleResource<>(drawable);
    }
}
```

**新增glide配置**

[MyAppGlideModule](https://github.com/FlyJingFish/GraphicsDrawable/tree/master/app/src/main/java/com/flyjingfish/GraphicsDrawable/svg/MyAppGlideModule.java)

```java

@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(
            @NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry
                .register(SVG.class, PictureDrawable.class, new SvgDrawableTranscoder())
                .append(InputStream.class, SVG.class, new SvgDecoder());
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}

```

**开始使用svg**

- 本地资源

```kotlin
val pic4Drawable = GraphicsDrawable(binding.iv4)
pic4Drawable.setShapeType(GraphicsDrawable.ShapeType.CUSTOM)

val uri = Uri.parse(
    ContentResolver.SCHEME_ANDROID_RESOURCE
            + "://"
            + packageName
            + "/"
            + R.raw.dog_heart
)
Glide.with(this)
    .`as`(PictureDrawable::class.java)
    .transition(DrawableTransitionOptions.withCrossFade())
    .load(uri).into(object : CustomTarget<PictureDrawable?>() {

        override fun onResourceReady(
            resource: PictureDrawable,
            transition: Transition<in PictureDrawable?>?
        ) {
            pic4Drawable.setCustomDrawable(resource)
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    })
```

- 网络资源

```kotlin


val pic4Drawable = GraphicsDrawable(binding.iv4)
pic4Drawable.setShapeType(GraphicsDrawable.ShapeType.CUSTOM)

val uri = Uri.parse("http://www.clker.com/cliparts/u/Z/2/b/a/6/android-toy-h.svg")
Glide.with(this)
    .`as`(PictureDrawable::class.java)
    .transition(DrawableTransitionOptions.withCrossFade())
    .load(uri).into(object : CustomTarget<PictureDrawable?>() {

        override fun onResourceReady(
            resource: PictureDrawable,
            transition: Transition<in PictureDrawable?>?
        ) {
            pic4Drawable.setCustomDrawable(resource)
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    })
```




### 最后推荐我写的另外一些库

- [OpenImage 轻松实现在应用内点击小图查看大图的动画放大效果](https://github.com/FlyJingFish/OpenImage)

- [AndroidAOP 是专属于 Android 端 Aop 框架，只需一个注解就可以请求权限、切换线程、禁止多点、监测生命周期等等，没有使用 AspectJ，也可以定制出属于你的 Aop 代码](https://github.com/FlyJingFish/AndroidAOP)

- [ShapeImageView 支持显示任意图形，只有你想不到没有它做不到](https://github.com/FlyJingFish/ShapeImageView)

- [FormatTextViewLib 支持部分文本设置加粗、斜体、大小、下划线、删除线，下划线支持自定义距离、颜色、线的宽度；支持添加网络或本地图片](https://github.com/FlyJingFish/FormatTextViewLib)

- [主页查看更多开源库](https://github.com/FlyJingFish)


