package com.example.openinapp.model

data class LinksData(
    val favourite_links: List<Any>,
    val overall_url_chart: OverallUrlChart,
    val recent_links: List<Link>,
    val top_links: List<Link>
)