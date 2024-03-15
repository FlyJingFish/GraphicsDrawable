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

设置各种圆角，圆形

直接上硬菜

```kotlin

```


# 最后推荐我写的另一个库，轻松实现在应用内点击小图查看大图的动画放大效果

- [OpenImage](https://github.com/FlyJingFish/OpenImage) （已内置当前库）

- [主页查看更多开源库](https://github.com/FlyJingFish)


