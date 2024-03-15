# GraphicsDrawable

[![Maven central](https://img.shields.io/maven-central/v/io.github.FlyJingFish/GraphicsDrawable)](https://central.sonatype.com/artifact/io.github.FlyJingFish/GraphicsDrawable/)
[![GitHub stars](https://img.shields.io/github/stars/FlyJingFish/GraphicsDrawable.svg)](https://github.com/FlyJingFish/GraphicsDrawable/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/FlyJingFish/GraphicsDrawable.svg)](https://github.com/FlyJingFish/GraphicsDrawable/network)
[![GitHub issues](https://img.shields.io/github/issues/FlyJingFish/GraphicsDrawable.svg)](https://github.com/FlyJingFish/GraphicsDrawable/issues)
[![GitHub license](https://img.shields.io/github/license/FlyJingFish/GraphicsDrawable.svg)](https://github.com/FlyJingFish/GraphicsDrawable/blob/master/LICENSE)



## GraphicsDrawable支持圆图、圆角图以及自定义图形；不再需要学习各个图片加载框架各种设置圆角，圆形图，甚至任何的特殊图形也无缝支持；不需要再让 UI 切各种圆角、圆形图或其他图形的 placeholder、error 资源图

<img src="https://github.com/FlyJingFish/GraphicsDrawable/blob/master/screenshot/Screenshot_20221011_144810.jpg" width="400px" height="800px" alt="show" />|<img src="https://github.com/FlyJingFish/GraphicsDrawable/blob/master/screenshot/Screenshot_20221031_123252.jpg" width="400px" height="800px" alt="show" />



## 特色功能

1、**GraphicsDrawable** 支持圆形图案、圆角矩形图案、以及自定义图形，完美兼容所有的图片加载库

2、**GraphicsDrawable** 使用三方图片加载框架，不需要再让 UI 切各种圆角、圆形图或其他图形的 placeholder、error 资源图

3、**GraphicsDrawable** 矩形图片支持四个角独立设置角度值，矩形边框也支持四个角独立设置角度值

4、**GraphicsDrawable** 完美兼容所有的图片加载库，支持定义任意图形，只有你想不到没有它做不到

5、**GraphicsDrawable** 不挑显示控件，任何View都可以，只要支持 Drawable 就可以



## 第一步，根目录build.gradle

```gradle
allprojects {
    repositories {
        ...
        maven { url "https://s01.oss.sonatype.org/content/groups/public" }
    }
}
```
## 第二步，需要引用的build.gradle

```gradle
dependencies {
    //必选项
    implementation 'io.github.FlyJingFish:GraphicsDrawable:1.0.0'
    //可选项，如果您使用 Glide 则使用这个
    implementation 'io.github.FlyJingFish:GraphicsDrawableGlideLib:1.0.0'
    //可选项，如果您使用 Coil 则使用这个
    implementation 'io.github.FlyJingFish:GraphicsDrawableCoilLib:1.0.0'
}
```
## 第三步，使用说明

# 一、GraphicsDrawable 使用说明

| 方法名                         |              说明               |
|-----------------------------|:-----------------------------:|
| setDrawable                 |            设置绘制的图片            |
| setCustomDrawable           |          设置自定义的显示形状           |
| setBackgroundMode           |            设置背景模式             |
| setFollowImageViewScaleType | 设置是否跟随ImageView的 ScaleType 显示 |
| setScaleType                |      设置自定义 ScaleType 的类型      |
| setShapeType                |            设置显示的形状            |
| setRadius                   |           设置矩形图的圆角值           |
| setRelativeRadius           |       设置矩形图的圆角值(适配 Rtl)       |
| setUseViewPadding           |      设置是否适应View的padding       |

# 二、Glide 使用

- ImageView 的使用，以下例子默认跟随 ImageView 的 ScaleType 显示


```kotlin
// 四个角一样
val pic1Drawable = GraphicsDrawable(binding.iv1)
pic1Drawable.setShapeType(GraphicsDrawable.ShapeType.RECTANGLE)
pic1Drawable.setRadius(MyImageLoader.dp2px(20f).toFloat())
Glide.with(iv).load(url).placeholder(p).error(err).into(GlideGraphicsImageViewTarget(pic1Drawable))

// 四个角不同
val pic2Drawable = GraphicsDrawable(binding.iv2)
pic2Drawable.setShapeType(GraphicsDrawable.ShapeType.RECTANGLE)
pic2Drawable.setRelativeRadius(MyImageLoader.dp2px(10f).toFloat(),MyImageLoader.dp2px(20f).toFloat(),MyImageLoader.dp2px(30f).toFloat(),MyImageLoader.dp2px(40f).toFloat())
Glide.with(iv).load(url).placeholder(p).error(err).into(GlideGraphicsImageViewTarget(pic2Drawable))

// 圆形图
val pic3Drawable = GraphicsDrawable(binding.iv3)
pic3Drawable.setShapeType(GraphicsDrawable.ShapeType.OVAL)
Glide.with(iv).load(url).placeholder(p).error(err).into(GlideGraphicsImageViewTarget(pic3Drawable))

// 自定义图形
val pic4Drawable = GraphicsDrawable(binding.iv4)
pic4Drawable.setShapeType(GraphicsDrawable.ShapeType.CUSTOM)
pic4Drawable.setCustomDrawable(resources.getDrawable(R.drawable.ic_vector_flower))
Glide.with(iv).load(url).placeholder(p).error(err).into(GlideGraphicsImageViewTarget(pic4Drawable))

```
- View 设置 背景

```kotlin
Glide.with(iv).load(url).placeholder(p).error(err).into(GlideGraphicsViewBackgroundTarget(pic4Drawable))
```

# 三、Coil 使用

- ImageView 的使用，以下例子默认跟随 ImageView 的 ScaleType 显示
- 
```kotlin
// 四个角一样
val pic1Drawable = GraphicsDrawable(binding.iv1)
pic1Drawable.setShapeType(GraphicsDrawable.ShapeType.RECTANGLE)
pic1Drawable.setRadius(MyImageLoader.dp2px(20f).toFloat())
val request = ImageRequest.Builder(iv.context)
    .data(url).placeholder(p).error(err).target(CoilGraphicsImageViewTarget(iv, pic1Drawable)).build()
imageLoader.enqueue(request)

// 四个角不同
val pic2Drawable = GraphicsDrawable(binding.iv2)
pic2Drawable.setShapeType(GraphicsDrawable.ShapeType.RECTANGLE)
pic2Drawable.setRelativeRadius(MyImageLoader.dp2px(10f).toFloat(),MyImageLoader.dp2px(20f).toFloat(),MyImageLoader.dp2px(30f).toFloat(),MyImageLoader.dp2px(40f).toFloat())
val request = ImageRequest.Builder(iv.context)
    .data(url).placeholder(p).error(err).target(CoilGraphicsImageViewTarget(iv, pic2Drawable)).build()
imageLoader.enqueue(request)

// 圆形图
val pic3Drawable = GraphicsDrawable(binding.iv3)
pic3Drawable.setShapeType(GraphicsDrawable.ShapeType.OVAL)
val request = ImageRequest.Builder(iv.context)
    .data(url).placeholder(p).error(err).target(CoilGraphicsImageViewTarget(iv, pic3Drawable)).build()
imageLoader.enqueue(request)

// 自定义图形
val pic4Drawable = GraphicsDrawable(binding.iv4)
pic4Drawable.setShapeType(GraphicsDrawable.ShapeType.CUSTOM)
pic4Drawable.setCustomDrawable(resources.getDrawable(R.drawable.ic_vector_flower))
val request = ImageRequest.Builder(iv.context)
    .data(url).placeholder(p).error(err).target(CoilGraphicsImageViewTarget(iv, pic4Drawable)).build()
imageLoader.enqueue(request)

```

- View 设置 背景

```kotlin
val request = ImageRequest.Builder(iv.context)
    .data(url).placeholder(p).error(err).target(CoilGraphicsViewBackgroundTarget(iv, pic4Drawable)).build()
imageLoader.enqueue(request)

```


# 最后推荐我写的另一个库，轻松实现在应用内点击小图查看大图的动画放大效果

- [OpenImage](https://github.com/FlyJingFish/OpenImage) （已内置当前库）

- [主页查看更多开源库](https://github.com/FlyJingFish)


