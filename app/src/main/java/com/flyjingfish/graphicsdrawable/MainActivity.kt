package com.flyjingfish.graphicsdrawable

import android.content.ContentResolver
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView.ScaleType
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.flyjingfish.graphicsdrawable.databinding.ActivityMainBinding
import com.flyjingfish.graphicsdrawablelib.GraphicsDrawable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var itemData: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rgLoad.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_glide) {
                MyImageLoader.loadType = MyImageLoader.LoaderType.GLIDE
            } else {
                MyImageLoader.loadType = MyImageLoader.LoaderType.COIL
            }
            setData()
        }
        binding.cbUse.setOnCheckedChangeListener { _, isChecked ->
            MyImageLoader.useDrawable = isChecked
            if (!isChecked){
                MyImageLoader.backgroundMode = false
                binding.cbBackground.isChecked = false
            }
            setData()
        }

        binding.cbBackground.setOnCheckedChangeListener { _, isChecked ->
            MyImageLoader.backgroundMode = isChecked
            if (isChecked){
                binding.iv1.setImageDrawable(null)
                binding.iv2.setImageDrawable(null)
                binding.iv3.setImageDrawable(null)
                binding.iv4.setImageDrawable(null)
            }
            setData()
        }
        itemData =
            "https://pics4.baidu.com/feed/50da81cb39dbb6fd95aa0c599b8d0d1e962b3708.jpeg?token=bf17224f51a6f4bb389e787f9c487940"
        setData()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    private val handler = Handler(Looper.getMainLooper())
    private fun setData() {
        handler.postDelayed({
            if (MyImageLoader.backgroundMode){
                binding.iv1.setImageDrawable(null)
                binding.iv2.setImageDrawable(null)
                binding.iv3.setImageDrawable(null)
                binding.iv4.setImageDrawable(null)
            }

            val pic1Drawable = GraphicsDrawable(binding.iv1)
            pic1Drawable.setShapeType(GraphicsDrawable.ShapeType.RECTANGLE)
            pic1Drawable.setRadius(MyImageLoader.dp2px(20f).toFloat())

            val pic2Drawable = GraphicsDrawable(binding.iv2)
            pic2Drawable.setShapeType(GraphicsDrawable.ShapeType.RECTANGLE)
            pic2Drawable.setRelativeRadius(MyImageLoader.dp2px(10f).toFloat(),MyImageLoader.dp2px(20f).toFloat(),MyImageLoader.dp2px(30f).toFloat(),MyImageLoader.dp2px(40f).toFloat())

            val pic3Drawable = GraphicsDrawable(binding.iv3)
            pic3Drawable.setShapeType(GraphicsDrawable.ShapeType.OVAL)

            val pic4Drawable = GraphicsDrawable(binding.iv4)
            pic4Drawable.setShapeType(GraphicsDrawable.ShapeType.CUSTOM)
//            pic4Drawable.setCustomDrawable(resources.getDrawable(R.drawable.ic_vector_flower))

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

            MyImageLoader.load(itemData,binding.iv1,R.mipmap.img_load_placeholder,R.mipmap.img_load_placeholder,pic1Drawable)
            MyImageLoader.load(itemData,binding.iv2,R.mipmap.img_load_placeholder,R.mipmap.img_load_placeholder,pic2Drawable)
            MyImageLoader.load(itemData,binding.iv3,R.mipmap.img_load_placeholder,R.mipmap.img_load_placeholder,pic3Drawable)
            MyImageLoader.load(itemData,binding.iv4,R.mipmap.img_load_placeholder,R.mipmap.img_load_placeholder,pic4Drawable)
        },400L)



    }

    fun onPicClick(view: View) {
        when (view.id) {
            R.id.tv_pic1 -> {
                binding.tvPic1.setBackgroundColor(resources.getColor(R.color.teal_200))
                binding.tvPic2.setBackgroundColor(Color.TRANSPARENT)
                binding.tvPic3.setBackgroundColor(Color.TRANSPARENT)
                binding.tvPic4.setBackgroundColor(Color.TRANSPARENT)
                itemData =
                    "https://pics4.baidu.com/feed/50da81cb39dbb6fd95aa0c599b8d0d1e962b3708.jpeg?token=bf17224f51a6f4bb389e787f9c487940"
            }

            R.id.tv_pic2 -> {
                binding.tvPic2.setBackgroundColor(resources.getColor(R.color.teal_200))
                binding.tvPic1.setBackgroundColor(Color.TRANSPARENT)
                binding.tvPic3.setBackgroundColor(Color.TRANSPARENT)
                binding.tvPic4.setBackgroundColor(Color.TRANSPARENT)
                itemData =
                    "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp0.itc.cn%2Fq_70%2Fimages03%2F20210227%2F6687c969b58d486fa2f23d8488b96ae4.jpeg&refer=http%3A%2F%2Fp0.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661701773&t=19043990158a1d11c2a334146020e2ce"
            }

            R.id.tv_pic3 -> {
                binding.tvPic3.setBackgroundColor(resources.getColor(R.color.teal_200))
                binding.tvPic1.setBackgroundColor(Color.TRANSPARENT)
                binding.tvPic2.setBackgroundColor(Color.TRANSPARENT)
                binding.tvPic4.setBackgroundColor(Color.TRANSPARENT)
                itemData =
                    "https://img1.baidu.com/it/u=3124924291,3476865151&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=4841"
            }

            R.id.tv_pic4 -> {
                binding.tvPic4.setBackgroundColor(resources.getColor(R.color.teal_200))
                binding.tvPic1.setBackgroundColor(Color.TRANSPARENT)
                binding.tvPic2.setBackgroundColor(Color.TRANSPARENT)
                binding.tvPic3.setBackgroundColor(Color.TRANSPARENT)
                itemData =
                    "https://pics4.baidu.coms/feed/50da81cb39dbb6fd95aa0c599b8d0d1e962b3708.jpeg?token=bf17224f51a6f4bb389e787f9c487940"
            }
        }
        setData()
    }

    fun onScaleTypeClick(view: View) {
        when (view.id) {
            R.id.tv_center -> setScaleType(ScaleType.CENTER)
            R.id.tv_centerCrop -> setScaleType(ScaleType.CENTER_CROP)
            R.id.tv_centerInside -> setScaleType(ScaleType.CENTER_INSIDE)
            R.id.tv_fitCenter -> setScaleType(ScaleType.FIT_CENTER)
            R.id.tv_fitStart -> setScaleType(ScaleType.FIT_START)
            R.id.tv_fitEnd -> setScaleType(ScaleType.FIT_END)
            R.id.tv_fixXY -> setScaleType(ScaleType.FIT_XY)
        }
        (view as RadioButton).isChecked = true
    }

    private fun  setScaleType(scaleType: ScaleType){
        binding.iv1.scaleType = scaleType
        binding.iv2.scaleType = scaleType
        binding.iv3.scaleType = scaleType
        binding.iv4.scaleType = scaleType
        binding.tvCenter.isChecked = false
        binding.tvCenterCrop.isChecked = false
        binding.tvCenterInside.isChecked = false
        binding.tvFitCenter.isChecked = false
        binding.tvFitStart.isChecked = false
        binding.tvFitEnd.isChecked = false
        binding.tvFixXY.isChecked = false

        setData()
    }
}