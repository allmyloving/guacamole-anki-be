package com.guacamole.cards.domain

enum class RevisionPeriod(val days: Int) {
    ONE_DAY(1), THREE_DAYS(3), WEEK(7), MONTH(30);

    fun next(): RevisionPeriod {
        return values().indexOf(this).let { values().getOrNull(it + 1) ?: this }
    }

    fun previous(): RevisionPeriod {
        return values().indexOf(this).let { values().getOrNull(it - 1) ?: this }
    }
}
