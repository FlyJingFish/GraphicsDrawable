package com.flyjingfish.graphicsdrawable

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.flyjingfish.graphicsdrawable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var itemData: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemData =
            "https://pics4.baidu.coms/feed/50da81cb39dbb6fd95aa0c599b8d0d1e962b3708.jpeg?token=bf17224f51a6f4bb389e787f9c487940"
        setData()
        binding.ivFitStart.setOnClickListener { v -> }
        binding.ivFitCenter.setOnClickListener { v -> }
    }

    private fun setData() {
        MyImageLoader.getInstance().loadRoundCorner(
            binding.ivCenter,
            itemData,
            20f,
            R.mipmap.img_load_placeholder,
            R.mipmap.img_load_placeholder
        )
        MyImageLoader.getInstance().loadCircle(
            binding.ivCenterCrop,
            itemData,
            R.mipmap.img_load_placeholder,
            R.mipmap.img_load_placeholder
        )
        MyImageLoader.getInstance().load(
            binding.ivCenterInside,
            itemData,
            R.mipmap.img_load_placeholder,
            R.mipmap.img_load_placeholder
        )
        MyImageLoader.getInstance().loadRoundCorner(
            binding.ivFitStart,
            itemData,
            20f,
            R.mipmap.img_load_placeholder,
            R.mipmap.img_load_placeholder
        )
        MyImageLoader.getInstance().loadRoundCorner(
            binding.ivFitCenter,
            itemData,
            20f,
            R.mipmap.img_load_placeholder,
            R.mipmap.img_load_placeholder
        )
        MyImageLoader.getInstance().loadCircle(
            binding.ivFitEnd,
            itemData,
            R.mipmap.img_load_placeholder,
            R.mipmap.img_load_placeholder
        )
    }

    fun onPicClick(view: View) {
        when (view.id) {
            R.id.tv_pic1 -> {
                binding.tvPic1.setBackgroundColor(resources.getColor(R.color.teal_200))
                binding.tvPic2.setBackgroundColor(Color.TRANSPARENT)
                binding.tvPic3.setBackgroundColor(Color.TRANSPARENT)
                itemData =
                    "https://pics4.baidu.com/feed/50da81cb39dbb6fd95aa0c599b8d0d1e962b3708.jpeg?token=bf17224f51a6f4bb389e787f9c487940"
            }

            R.id.tv_pic2 -> {
                binding.tvPic2.setBackgroundColor(resources.getColor(R.color.teal_200))
                binding.tvPic1.setBackgroundColor(Color.TRANSPARENT)
                binding.tvPic3.setBackgroundColor(Color.TRANSPARENT)
                itemData =
                    "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp0.itc.cn%2Fq_70%2Fimages03%2F20210227%2F6687c969b58d486fa2f23d8488b96ae4.jpeg&refer=http%3A%2F%2Fp0.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661701773&t=19043990158a1d11c2a334146020e2ce"
            }

            R.id.tv_pic3 -> {
                binding.tvPic3.setBackgroundColor(resources.getColor(R.color.teal_200))
                binding.tvPic1.setBackgroundColor(Color.TRANSPARENT)
                binding.tvPic2.setBackgroundColor(Color.TRANSPARENT)
                itemData =
                    "https://img1.baidu.com/it/u=3124924291,3476865151&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=4841"
            }
        }
        setData()
    }
}