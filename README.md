# GraphicsDrawable

[![Maven central](https://img.shields.io/maven-central/v/io.github.FlyJingFish/GraphicsDrawable)](https://central.sonatype.com/artifact/io.github.FlyJingFish/GraphicsDrawable/)
[![GitHub stars](https://img.shields.io/github/stars/FlyJingFish/GraphicsDrawable.svg)](https://github.com/FlyJingFish/GraphicsDrawable/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/FlyJingFish/GraphicsDrawable.svg)](https://github.com/FlyJingFish/GraphicsDrawable/network)
[![GitHub issues](https://img.shields.io/github/issues/FlyJingFish/GraphicsDrawable.svg)](https://github.com/FlyJingFish/GraphicsDrawable/issues)
[![GitHub license](https://img.shields.io/github/license/FlyJingFish/GraphicsDrawable.svg)](https://github.com/FlyJingFish/GraphicsDrawable/blob/master/LICENSE)



## GraphicsDrawable支持圆图、圆角图以及自定义图形；

<img src="https://github.com/FlyJingFish/GraphicsDrawable/blob/master/screenshot/Screenshot_20221011_144810.jpg" width="400px" height="800px" alt="show" />|<img src="https://github.com/FlyJingFish/GraphicsDrawable/blob/master/screenshot/Screenshot_20221031_123252.jpg" width="400px" height="800px" alt="show" />



## 特色功能
1、**GraphicsDrawable** 支持圆形图案、圆角矩形图案、以及自定义图形，完美兼容所有的图片加载库

2、**GraphicsDrawable** 矩形图片支持四个角独立设置角度值，矩形边框也支持四个角独立设置角度值

3、**GraphicsDrawable** 完美兼容所有的图片加载库，支持定义任意图形，只有你想不到没有它做不到



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
    implementation 'io.github.FlyJingFish:GraphicsDrawable:1.0.0'
}
```
## 第三步，使用说明

# 一、GraphicsDrawable 使用说明

### GraphicsDrawable 示例
```xml
<com.flyjingfish.GraphicsDrawablelib.GraphicsDrawable
    android:id="@+id/iv_centerCrop"
    android:layout_width="110dp"
    android:layout_height="110dp"
    android:layout_marginStart="10dp"
    android:padding="10dp"
    app:FlyJFish_shape="rectangle"
    app:FlyJFish_shape_border="rectangle"
    app:FlyJFish_shape_border_width="3dp"
    app:FlyJFish_shape_border_angle="45"
    app:FlyJFish_shape_left_top_radius="8dp"
    app:FlyJFish_shape_right_top_radius="12dp"
    app:FlyJFish_shape_right_bottom_radius="16dp"
    app:FlyJFish_shape_left_bottom_radius="20dp"
    app:FlyJFish_shape_border_left_top_radius="10dp"
    app:FlyJFish_shape_border_right_top_radius="15dp"
    app:FlyJFish_shape_border_right_bottom_radius="20dp"
    app:FlyJFish_shape_border_left_bottom_radius="25dp"
    app:FlyJFish_shape_border_gradient="true"
    app:FlyJFish_shape_border_startColor="@color/purple_200"
    app:FlyJFish_shape_border_endColor="@color/teal_700"
    android:scaleType="centerCrop" />
```


# 最后推荐我写的另一个库，轻松实现在应用内点击小图查看大图的动画放大效果

- [OpenImage](https://github.com/FlyJingFish/OpenImage) （已内置当前库）

- [主页查看更多开源库](https://github.com/FlyJingFish)


