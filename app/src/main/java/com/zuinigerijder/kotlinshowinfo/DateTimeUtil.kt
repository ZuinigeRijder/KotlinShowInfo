package com.zuinigerijder.kotlinshowinfo

import androidx.compose.runtime.Composable
import java.text.DateFormatSymbols
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

class DateTimeUtil {
    companion object {

        @Composable
        fun getDateTimeInfo(): String {
            val calendar = GregorianCalendar.getInstance()
            calendar.setFirstDayOfWeek(2) // Monday
            calendar.setMinimalDaysInFirstWeek(4) // ISO-8601-compliant week numbering
            val text = getLongDayString(calendar)
            return text
        }

        private fun getLongDayString(calendar : Calendar): String {
            val locale: Locale? = Locale.getDefault()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
            val week = calendar.get(Calendar.WEEK_OF_YEAR)
            val dayNumber = calendar.get(Calendar.DAY_OF_YEAR)
            val hours = String.format(locale, "%02d", calendar.get(Calendar.HOUR_OF_DAY))
            val hoursAmPm = String.format(locale, "%02d", calendar.get(Calendar.HOUR))
            val amPm = if (calendar.get(Calendar.AM_PM) == Calendar.AM) {
                "AM"
            } else {
                "PM"
            }
            val minutes = String.format(locale, "%02d", calendar.get(Calendar.MINUTE))
            val seconds = String.format(locale, "%02d", calendar.get(Calendar.SECOND))
            val timeZone = calendar.timeZone.id
            val utcOffset = getUtcString(calendar, locale)
            val weekDayName = DateFormatSymbols().weekdays[weekDay]
            val monthName = DateFormatSymbols().months[month]
            val text = "$weekDayName $day $monthName $year\n$hours:$minutes:$seconds $utcOffset ($hoursAmPm:$minutes:$seconds $amPm)\n$timeZone\nWeek $week\nDay of year: $dayNumber"
            return text
        }

        private fun getUtcString(calendar: Calendar, locale: Locale?): String {
            var utcOffset = (calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)) / 60000
            val sign = if (utcOffset < 0) {
                utcOffset = -utcOffset
                '-'
            } else {
                '+'
            }
            val hours = String.format(locale, "%02d", utcOffset / 60)
            val minutes = String.format(locale, "%02d", utcOffset % 60)
            return "$sign$hours:$minutes"
        }
    }
}