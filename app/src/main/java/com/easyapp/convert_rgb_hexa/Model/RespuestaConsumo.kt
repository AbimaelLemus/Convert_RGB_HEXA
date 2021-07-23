package com.easyapp.convert_rgb_hexa.Model

class RespuestaConsumo(
    abbreviation: String,
    client_ip: String,
    datetime: String,
    day_of_week: Int,
    day_of_year: Int,
    dst: Boolean,
    dst_from: String,
    dst_offset: Int,
    dst_until: String,
    raw_offset: Int,
    timezone: String,
    unixtime: Long,
    utc_datetime: String,
    week_number: Int
) {
    var abbreviation: String? = null
    var client_ip: String? = null
    var datetime: String? = null
    var day_of_week: Int? = 0
    var day_of_year: Int? = 0
    var dst: Boolean = false
    var dst_from: String? = null
    var dst_offset: Int = 0
    var dst_until: String? = null
    var raw_offset: Int? = 0
    var timezone: String? = null
    var unixtime: Long? = 0
    var utc_datetime: String? = null
    var utc_offset: String? = null
    var week_number: Int? = 0

    init {
        this.abbreviation = abbreviation
        this.client_ip = client_ip
        this.datetime = datetime
        this.day_of_week = day_of_week
        this.day_of_year = day_of_year
        this.dst = dst
        this.dst_from = dst_from
        this.dst_offset = dst_offset
        this.dst_until = dst_until
        this.raw_offset = raw_offset
        this.timezone = timezone
        this.unixtime = unixtime
        this.utc_datetime = datetime
        this.utc_offset = utc_offset
        this.week_number = week_number

    }

}