package com.example.openinapp.ui.links

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.openinapp.R
import com.example.openinapp.api.LinksAPI
import com.example.openinapp.api.RetrofitHelper
import com.example.openinapp.databinding.FragmentLinksBinding
import com.example.openinapp.model.Link
import com.example.openinapp.model.LinksResponse
import com.example.openinapp.network.NetworkResult
import com.example.openinapp.repository.LinksRepository
import com.example.openinapp.utils.HelperFunctions
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Links : Fragment() {

    private var _binding : FragmentLinksBinding ?= null
    private val binding get() = _binding!!

    private lateinit var linksViewModel : LinksViewModel
    private lateinit var topLinksItemList: ArrayList<Link>
    private lateinit var recentLinksItemList: ArrayList<Link>


    private val months = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLinksBinding.inflate(inflater, container, false)

        val linkService = RetrofitHelper.getInstance().create(LinksAPI::class.java)
        val repo = LinksRepository(linkService)
        linksViewModel = ViewModelProvider(this,LinkViewModelFactory(repo)).get(LinksViewModel::class.java)
        bindObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recentLinks.setOnClickListener{
            binding.recentLinks.setBackgroundResource(R.drawable.blue_btn)
            binding.recentLinks.setTextColor(resources.getColor(R.color.white))
            binding.topLinks.setTextColor(resources.getColor(R.color.text_grey))

            binding.topLinks.background = null
            val adapter = CustomAdapter(requireContext(), recentLinksItemList)
            binding.linksListView.adapter = adapter
        }

        binding.topLinks.setOnClickListener{
            binding.topLinks.setBackgroundResource(R.drawable.blue_btn)
            binding.topLinks.setTextColor(resources.getColor(R.color.white))
            binding.recentLinks.setTextColor(resources.getColor(R.color.text_grey))

            binding.recentLinks.background = null
            val adapter = CustomAdapter(requireContext(), topLinksItemList)
            binding.linksListView.isVisible = true
            binding.linksListView.adapter = adapter
        }

        binding.greeting.text = getGreetingMessage()
    }

    private fun getGreetingMessage(): String {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        return when {
            hourOfDay in 0..11 -> "Good Morning"
            hourOfDay in 12..16 -> "Good Afternoon"
            hourOfDay in 17..20 -> "Good Evening"
            else -> "Good Night"
        }
    }

    private fun bindObservers() {
        linksViewModel.linkLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success<*> -> {
                        binding.todaysClicks.text = it.data!!.today_clicks.toString()
                        binding.location.text = HelperFunctions.handleEmptyData(it.data.top_location)
                        binding.topSource.text = HelperFunctions.handleEmptyData(it.data.top_source)

                        val monthClicksMap = aggregateClicksByMonth(it?.data)
                        val entries = convertToChartData(monthClicksMap)

                        val lineChart: LineChart = binding.lineChart
                        setupLineChart(lineChart, entries)


                    topLinksItemList = ArrayList(it.data.data.top_links)
                    recentLinksItemList = ArrayList(it.data.data.recent_links)

                    binding.topLinks.setBackgroundResource(R.drawable.blue_btn)
                    binding.topLinks.setTextColor(resources.getColor(R.color.white))
                    binding.recentLinks.setTextColor(resources.getColor(R.color.text_grey))

                    binding.recentLinks.background = null
                    val adapter = CustomAdapter(requireContext(), topLinksItemList)
                    binding.linksListView.isVisible = true
                    binding.linksListView.adapter = adapter
                }

                is NetworkResult.Error<*> -> {
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                }

                is NetworkResult.Loading<*> -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }



    private fun aggregateClicksByMonth(dataList: LinksResponse): Map<String, Int> {
        val monthClicksMap = mutableMapOf<String, Int>()

        // Initialize all months with 0 clicks
        for (month in months) {
            monthClicksMap[month] = 0
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())

        for (data in dataList.data.recent_links) {
            val createdAtDate = dateFormat.parse(data.created_at)
            val monthName = monthFormat.format(createdAtDate ?: Date())
            val totalClicks = data.total_clicks ?: 0

            monthClicksMap[monthName] = monthClicksMap[monthName]!! + totalClicks
        }

        return monthClicksMap
    }

    private fun convertToChartData(monthClicksMap: Map<String, Int>): List<Entry> {
        val entries = ArrayList<Entry>()
        for ((index, month) in months.withIndex()) {
            val totalClicks = monthClicksMap[month] ?: 0
            entries.add(Entry(index.toFloat(), totalClicks.toFloat()))
        }
        return entries
    }

    private fun setupLineChart(lineChart: LineChart, entries: List<Entry>) {
        val dataSet = LineDataSet(entries, "Total Clicks")
        dataSet.color = R.color.blue
        dataSet.valueTextColor = R.color.text_grey
        dataSet.lineWidth = 2f
        dataSet.setDrawCircles(false)

//        dataSet.circleRadius = 0f
//        dataSet.setCircleColor(Color.BLUE)
        dataSet.setDrawValues(true)
        dataSet.valueTextSize = 12f

        val lineData = LineData(dataSet)
        lineChart.data = lineData

        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(months)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.xAxis.granularity = 1f
        lineChart.xAxis.setLabelCount(months.size)


        lineChart.xAxis.gridColor = R.color.graph_lines
        lineChart.axisLeft.gridColor = R.color.graph_lines
        lineChart.xAxis.gridLineWidth = 0.5f

        lineChart.axisRight.isEnabled = false
        lineChart.axisLeft.granularity = 1f
        lineChart.invalidate()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}