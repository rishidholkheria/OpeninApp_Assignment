package com.example.openinapp.model

data class LinksData(
    val favourite_links: List<Any>,
    val overall_url_chart: OverallUrlChart,
    val recent_links: List<RecentLink>,
    val top_links: List<TopLink>
)