package com.alakmalak.practicle1

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.alakmalak.practicle1.adapter.NewsAdapter
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_menu.*
import java.util.ArrayList


class MenuActivity : AppCompatActivity(), View.OnClickListener {
    var newsAdapter: NewsAdapter? = null
    var layouts: IntArray? = null
    var layouts2: IntArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        init()
        clickEvents()
        initLineChartDownFill()
    }

    private fun init(){
        try {
            layouts = intArrayOf(
                R.layout.news_lalyout,
                R.layout.news_lalyout,
                R.layout.news_lalyout
            )

            layouts2 = intArrayOf(
                R.layout.invest_lalyout,
                R.layout.invest_lalyout,
                R.layout.invest_lalyout,
                R.layout.invest_lalyout
            )

            newsAdapter = NewsAdapter(this, layouts)
            vpNews.clipToPadding = false
            vpNews.setPadding(0,0,200,0)
//            vpNews.setPageMargin(100)
            vpNews.adapter = newsAdapter
            vpNews.addOnPageChangeListener(newsPageChangeListener)
            indicatorSetup()
            ivArrayDotsPager[0]!!.setImageResource(R.drawable.t_circle)

            vpResearch.clipToPadding = false
            vpResearch.setPadding(0,0,200,0)

            vpResearch.adapter = newsAdapter
            vpResearch.addOnPageChangeListener(researchPageChangeListener)
            indicatorSetup2()
            ivArrayDotsPager2[0]!!.setImageResource(R.drawable.t_circle)

            newsAdapter = NewsAdapter(this, layouts2)
            vpWhyInvest.clipToPadding = false
            vpWhyInvest.setPadding(0,0,200,0)
            vpWhyInvest.adapter = newsAdapter
            vpWhyInvest.addOnPageChangeListener(investPageChangeListener)
            indicatorSetup3()
            ivArrayDotsPager3[0]!!.setImageResource(R.drawable.t_circle)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    var newsPageChangeListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                for (i in 0 until layouts!!.size){
                    ivArrayDotsPager[i]!!.setImageResource(R.drawable.f_circle)
                }
                ivArrayDotsPager[position]!!.setImageResource(R.drawable.t_circle)
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(arg0: Int) {}
        }

    var researchPageChangeListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                for (i in 0 until layouts!!.size){
                    ivArrayDotsPager2[i]!!.setImageResource(R.drawable.f_circle)
                }
                ivArrayDotsPager2[position]!!.setImageResource(R.drawable.t_circle)
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(arg0: Int) {}
        }

    var investPageChangeListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                for (i in 0 until layouts2!!.size){
                    ivArrayDotsPager3[i]!!.setImageResource(R.drawable.f_circle)
                }
                ivArrayDotsPager3[position]!!.setImageResource(R.drawable.t_circle)
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(arg0: Int) {}
        }

    private fun clickEvents(){
        try {
            menu1_dash.setOnClickListener(this)
            menu2_goals.setOnClickListener(this)
            menu3_fractions.setOnClickListener(this)
            menu4_pro.setOnClickListener(this)
            menu5.setOnClickListener(this)

            tvFilter1.setOnClickListener(this)
            tvFilter2.setOnClickListener(this)
            tvFilter3.setOnClickListener(this)
            tvFilter4.setOnClickListener(this)
            tvFilter5.setOnClickListener(this)
            tvFilter6.setOnClickListener(this)
            tvFilter7.setOnClickListener(this)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onClick(p0: View?) {
        try {
            when (p0!!.id) {
                R.id.menu1_dash -> {
//                    menuEffect(ivMenu1,tvMenu1)
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu2_goals -> {
                    menuEffect(ivMenu2,tvMenu2,viewMenu2)
                }
                R.id.menu3_fractions -> {
                    menuEffect(ivMenu3,tvMenu3,viewMenu3)
                }
                R.id.menu4_pro -> {
                    menuEffect(ivMenu4,tvMenu4,viewMenu4)
                }
                R.id.menu5 -> {
                    menuEffect(ivMenu5,tvMenu5,viewMenu5)
                }
                R.id.tvFilter1 -> {
                    tvPerformance.text = "1 Day Performance"
                    filterEffect(tvFilter1)
                }
                R.id.tvFilter2 -> {
                    tvPerformance.text = "1 Week Performance"
                    filterEffect(tvFilter2)
                }
                R.id.tvFilter3 -> {
                    tvPerformance.text = "1 Month Performance"
                    filterEffect(tvFilter3)
                }
                R.id.tvFilter4 -> {
                    tvPerformance.text = "3 Month Performance"
                    filterEffect(tvFilter4)
                }
                R.id.tvFilter5 -> {
                    tvPerformance.text = "1 Year Performance"
                    filterEffect(tvFilter5)
                }
                R.id.tvFilter6 -> {
                    tvPerformance.text = "5 Year Performance"
                    filterEffect(tvFilter6)
                }
                R.id.tvFilter7 -> {
                    tvPerformance.text = "All Performance"
                    filterEffect(tvFilter7)
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun filterEffect(tvFilter: AppCompatTextView){
        try {
            tvFilter1.background = ContextCompat.getDrawable(this, R.drawable.rectangle_btn3)
            tvFilter2.background = ContextCompat.getDrawable(this, R.drawable.rectangle_btn3)
            tvFilter3.background = ContextCompat.getDrawable(this, R.drawable.rectangle_btn3)
            tvFilter4.background = ContextCompat.getDrawable(this, R.drawable.rectangle_btn3)
            tvFilter5.background = ContextCompat.getDrawable(this, R.drawable.rectangle_btn3)
            tvFilter6.background = ContextCompat.getDrawable(this, R.drawable.rectangle_btn3)
            tvFilter7.background = ContextCompat.getDrawable(this, R.drawable.rectangle_btn3)

            tvFilter1.setTextColor(ContextCompat.getColor(applicationContext,R.color.text1))
            tvFilter2.setTextColor(ContextCompat.getColor(applicationContext,R.color.text1))
            tvFilter3.setTextColor(ContextCompat.getColor(applicationContext,R.color.text1))
            tvFilter4.setTextColor(ContextCompat.getColor(applicationContext,R.color.text1))
            tvFilter5.setTextColor(ContextCompat.getColor(applicationContext,R.color.text1))
            tvFilter6.setTextColor(ContextCompat.getColor(applicationContext,R.color.text1))
            tvFilter7.setTextColor(ContextCompat.getColor(applicationContext,R.color.text1))

            tvFilter.setTextColor(ContextCompat.getColor(applicationContext,R.color.white))

            tvFilter.background = ContextCompat.getDrawable(this, R.drawable.rectangle_btn)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun menuEffect(ivMenu: AppCompatImageView,tvMenu: AppCompatTextView,view: View) {
        try {
//            ivMenu1.setColorFilter(ContextCompat.getColor(applicationContext, R.color.text1), android.graphics.PorterDuff.Mode.MULTIPLY)
//            ivMenu2.setColorFilter(ContextCompat.getColor(applicationContext, R.color.text1), android.graphics.PorterDuff.Mode.MULTIPLY)
//            ivMenu3.setColorFilter(ContextCompat.getColor(applicationContext, R.color.text1), android.graphics.PorterDuff.Mode.MULTIPLY)
//            ivMenu4.setColorFilter(ContextCompat.getColor(applicationContext, R.color.text1), android.graphics.PorterDuff.Mode.MULTIPLY)
//            ivMenu5.setColorFilter(ContextCompat.getColor(applicationContext, R.color.text1), android.graphics.PorterDuff.Mode.MULTIPLY)

            tvMenu1.visibility = View.VISIBLE
            tvMenu2.visibility = View.VISIBLE
            tvMenu3.visibility = View.VISIBLE
            tvMenu4.visibility = View.VISIBLE
            tvMenu5.visibility = View.VISIBLE

            viewMenu2.visibility = View.GONE
            viewMenu3.visibility = View.GONE
            viewMenu4.visibility = View.GONE
            viewMenu5.visibility = View.GONE


//            ivMenu1.setColorFilter(ContextCompat.getColor(applicationContext, R.color.header), android.graphics.PorterDuff.Mode.MULTIPLY)
            tvMenu.visibility = View.GONE
            view.visibility = View.VISIBLE
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    lateinit var ivArrayDotsPager: Array<AppCompatImageView?>

    private fun indicatorSetup(){
        try {
            ivArrayDotsPager = arrayOfNulls<AppCompatImageView>(layouts!!.size)

            for (i in 0 until layouts!!.size){
                ivArrayDotsPager[i] = AppCompatImageView(applicationContext)
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(5, 0, 5, 0)
                ivArrayDotsPager[i]!!.layoutParams = params;
                ivArrayDotsPager[i]!!.setImageResource(R.drawable.f_circle)
                //ivArrayDotsPager[i].setAlpha(0.4f);
                parentNewsIndicator.addView(ivArrayDotsPager[i])
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    lateinit var ivArrayDotsPager2: Array<AppCompatImageView?>

    private fun indicatorSetup2(){
        try {
            ivArrayDotsPager2 = arrayOfNulls<AppCompatImageView>(layouts!!.size)

            for (i in 0 until layouts!!.size){
                ivArrayDotsPager2[i] = AppCompatImageView(applicationContext)
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(5, 0, 5, 0)
                ivArrayDotsPager2[i]!!.layoutParams = params;
                ivArrayDotsPager2[i]!!.setImageResource(R.drawable.f_circle)
                //ivArrayDotsPager2[i].setAlpha(0.4f);
                parentResearchIndicator.addView(ivArrayDotsPager2[i])
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    lateinit var ivArrayDotsPager3: Array<AppCompatImageView?>

    private fun indicatorSetup3(){
        try {
            ivArrayDotsPager3 = arrayOfNulls<AppCompatImageView>(layouts2!!.size)

            for (i in 0 until layouts2!!.size){
                ivArrayDotsPager3[i] = AppCompatImageView(applicationContext)
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(5, 0, 5, 0)
                ivArrayDotsPager3[i]!!.layoutParams = params;
                ivArrayDotsPager3[i]!!.setImageResource(R.drawable.f_circle)
                //ivArrayDotsPager2[i].setAlpha(0.4f);
                parentWhyInvestIndicator.addView(ivArrayDotsPager3[i])
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    /*Chart*/
    private fun initLineChartDownFill() {
        chart.setTouchEnabled(false)
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)
        chart.axisLeft.setDrawGridLines(false)
        chart.xAxis.setDrawGridLines(false)
        chart.axisRight.setDrawGridLines(false)
        chart.setDrawGridBackground(false)
        chart.maxHighlightDistance = 200f
        chart.setViewPortOffsets(0f, 0f, 0f, 0f)
        lineChartDownFillWithData()
    }

    private fun lineChartDownFillWithData() {
//        val description = Description()
//        description.setText("Days Data")
//        chart.description = description
        val entryArrayList = ArrayList<Entry>()
        entryArrayList.add(Entry(0f, 0f, ))
        entryArrayList.add(Entry(1f, 5f, ))
        entryArrayList.add(Entry(2f, 6f, ))
        entryArrayList.add(Entry(3f, 4f, ))
        entryArrayList.add(Entry(4f, 5f, ))
        entryArrayList.add(Entry(5f, 6f, ))
        entryArrayList.add(Entry(6f, 30f, ))
        entryArrayList.add(Entry(7f, 40f, ))
        entryArrayList.add(Entry(8f, 45f, ))
        entryArrayList.add(Entry(9f, 60f, ))
        entryArrayList.add(Entry(10f, 45f, ))
        entryArrayList.add(Entry(11f, 20f, ))


        //LineDataSet is the line on the graph
        val lineDataSet = LineDataSet(entryArrayList, "This is y bill")
        lineDataSet.lineWidth = 5f
        lineDataSet.color = Color.parseColor("#13D86D")
//        lineDataSet.setCircleColorHole(Color.GREEN)
        lineDataSet.setCircleColor(android.R.color.white)
        lineDataSet.highLightColor = Color.RED
        lineDataSet.setDrawValues(false)
        lineDataSet.circleRadius = 10f
        lineDataSet.setCircleColor(Color.YELLOW)

        //to make the smooth line as the graph is adrapt change so smooth curve
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        //to enable the cubic density : if 1 then it will be sharp curve
        lineDataSet.cubicIntensity = 0.2f

        //to fill the below of smooth line in graph
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillColor = Color.parseColor("#13D86D")
        //set the transparency
        lineDataSet.fillAlpha = 40

        //set the gradiant then the above draw fill color will be replace
//        val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.gradiant)
//        lineDataSet.fillDrawable = drawable

        //set legend disable or enable to hide {the left down corner name of graph}
        val legend: Legend = chart.getLegend()
        legend.isEnabled = false

        //to remove the cricle from the graph
        lineDataSet.setDrawCircles(false)

        //lineDataSet.setColor(ColorTemplate.COLORFUL_COLORS);
        val iLineDataSetArrayList = ArrayList<ILineDataSet>()
        iLineDataSetArrayList.add(lineDataSet)

        //LineData is the data accord
        val lineData = LineData(iLineDataSetArrayList)
        lineData.setValueTextSize(13f)
        lineData.setValueTextColor(Color.BLACK)
        chart.setData(lineData)
        chart.invalidate()
    }
}