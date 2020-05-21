package com.project.son.app.helper.classes

object VoiceToSpeechHelper {
    fun getFloor(result: ArrayList<String>?): Pair<String, Boolean> {
        if (result.isNullOrEmpty())
            return Pair("فرمان معتبر نیست", false)
        else {
            val command = result[0].split(" ")
            val floor =
                when {
                    command.contains("۱۰") -> "10"
                    command.contains("10") -> "10"
                    command.contains("ده") -> "10"
                    command.contains("دهم") -> "10"

                    command.contains("۹") -> "9"
                    command.contains("9") -> "9"
                    command.contains("نه") -> "9"
                    command.contains("نهم") -> "9"

                    command.contains("۸") -> "8"
                    command.contains("8") -> "8"
                    command.contains("هشت") -> "8"
                    command.contains("هشتم") -> "8"

                    command.contains("۷") -> "7"
                    command.contains("7") -> "7"
                    command.contains("هفت") -> "7"
                    command.contains("هفتم") -> "7"

                    command.contains("۶") -> "6"
                    command.contains("6") -> "6"
                    command.contains("شش") -> "6"
                    command.contains("ششم") -> "6"

                    command.contains("۵") -> "5"
                    command.contains("5") -> "5"
                    command.contains("پنج") -> "5"
                    command.contains("پنجم") -> "5"

                    command.contains("۴") -> "4"
                    command.contains("4") -> "4"
                    command.contains("چهار") -> "4"
                    command.contains("چهارم") -> "4"

                    command.contains("۳") -> "3"
                    command.contains("3") -> "3"
                    command.contains("سه") -> "3"
                    command.contains("سوم") -> "3"

                    command.contains("۲") -> "2"
                    command.contains("2") -> "2"
                    command.contains("دو") -> "2"
                    command.contains("دوم") -> "2"

                    command.contains("0") -> "0"
                    command.contains("صفر") -> "0"
                    command.contains("صفرم") -> "0"
                    command.contains("همکف") -> "0"

                    command.contains("-1") -> "-1"
                    command.contains("منفی1") -> "-1"
                    command.indexOf("منفی") > -1 && command.indexOf("1") > -1 && command.indexOf("منفی") + 1 == command.indexOf(
                        "1"
                    ) -> "-1"
                    command.indexOf("منفی") > -1 && command.indexOf("یک") > -1 && command.indexOf("منفی") + 1 == command.indexOf(
                        "یک"
                    ) -> "-1"
                    command.indexOf("منفی") > -1 && command.indexOf("یکم") > -1 && command.indexOf("منفی") + 1 == command.indexOf(
                        "یکم"
                    ) -> "-1"
                    command.contains("زیرزمین") -> "-1"
                    command.indexOf("زیر") > -1 && command.indexOf("زمین") > -1 && command.indexOf("زیر") + 1 == command.indexOf(
                        "زمین"
                    ) -> "-1"

                    command.contains("۱") -> "1"
                    command.contains("1") -> "1"
                    command.contains("یک") -> "1"
                    command.contains("یکم") -> "1"
                    command.contains("اول") -> "1"

                    else -> ""
                }

            return if (floor.isNotBlank())
                Pair(floor, true)
            else
                Pair("فرمان معتبر نیست", false)
        }
    }
}
