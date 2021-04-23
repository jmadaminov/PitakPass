package com.badcompany.pitakpass

object Test {

    @JvmStatic
    fun main(args: Array<String>) {

        longestPalindrome("bb")

    }

    fun longestPalindrome(s: String): String {


        var longestPal = ""

        if (s.length == 1) {
            longestPal = s[0].toString()
        } else if (s.length == 2) {
            if (s[0] == s[1]) longestPal = s.toString()
        } else {
            var i = 0
            while (true) {

                if (longestPal.isEmpty() || i == 0) {
                    longestPal = s[i].toString()
                } else if (i + 1 < s.length && s[i] == s[i + 1]) {
                    val candidatePal = getFullPalindromeString(i, i + 1, s)
                    if (candidatePal.length > longestPal.length) longestPal = candidatePal
                } else if (i + 1 < s.length && s[i - 1] == s[i + 1]) {
                    val candidatePal = getFullPalindromeString(i, s)
                    if (candidatePal.length > longestPal.length) longestPal = candidatePal
                } else {
                    break
                }
                i++
            }
        }

        return longestPal
    }


    fun getFullPalindromeString(centerIndex: Int, string: String): String {
        var result = ""
        var offset = 1
        while (true) {
            if (centerIndex - offset >= 0 && string[centerIndex - offset] == string[centerIndex + offset]) {
                result = string.substring(centerIndex - offset, centerIndex + offset + 1)
            } else {
                break
            }
            offset++
        }
        return result
    }


    fun getFullPalindromeString(centerLeft: Int, centerRight: Int, string: String): String {
        var result = string.substring(centerLeft, centerRight + 1)
        var offset = 1
        while (true) {
            if (centerLeft - offset >= 0 && centerRight + offset < string.length && string[centerLeft - offset] == string[centerRight + offset]) {
                result = string.substring(centerLeft - offset, centerRight + offset + 1)
            } else {
                break
            }
            offset++
        }
        return result
    }

}