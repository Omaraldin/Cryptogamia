package com.cryptogamia.utils

import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

class SubstitutionCipher {

    companion object {

        var identifiers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        fun generateRandomCiphertext(text: String): String? {
            val characters = text.toCharArray()
            val rand = Random()
            for (i in characters.size - 1 downTo 1) {
                val j = rand.nextInt(i + 1)
                val temp = characters[i]
                characters[i] = characters[j]
                characters[j] = temp
            }
            return String(characters)
        }

        fun encrypt(text: String, key: String): String {
            var i: Int = 0
            var ni: Int = 0
            var cipher = "";
            for (index in text.indices) {
                i = identifiers.indexOf(text[index].uppercase())
                if (i != -1) {
                    cipher += if (identifiers.uppercase().contains(text[index])) {
                        key[i].toString().uppercase()
                    } else {
                        key[i].toString()
                    }
                } else {
                    cipher += text[index]
                }

            }

            return cipher
        }


        fun decrypt(text: String, key: String): String {
            var i: Int = 0
            var ni: Int = 0
            var cipher = "";
            for (index in text.indices) {
                i = key.indexOf(text[index].uppercase())
                if (i != -1) {
                    cipher += if (key.uppercase().contains(text[index])) {
                        identifiers[i].toString().uppercase()
                    } else {
                        identifiers[i].toString()
                    }
                } else {
                    cipher += text[index]
                }
            }

            return cipher
        }


        fun getWords(text: String): List<String>? {
            return Stream.of(*text.split(" ").toTypedArray())
                .map { word: String ->
                    word.replace(
                        "[^A-Za-z]".toRegex(),
                        ""
                    ).lowercase(Locale.getDefault())
                }
                .filter { word: String -> word.isNotEmpty() }.collect(Collectors.toList())
        }


        fun countNGramFrequencies(text: String, n: Int): Map<String, Int> {
            var text = text
            val frequencies: MutableMap<String, Int> = HashMap()
            text = text.replace("[^A-Za-z]".toRegex(), "").uppercase(Locale.getDefault())
            for (i in text.indices) {
                val ngram = text.substring(i, i + n)
                val count = frequencies.getOrDefault(ngram, 0) + 1
                frequencies[ngram] = count
            }
            return frequencies
        }

        fun sortFrequencies(frequencies: Map<String, Int>?): MutableList<Map.Entry<String?, Int?>>? {
            return frequencies?.entries
                ?.stream()
                ?.sorted(java.util.Map.Entry.comparingByValue<String?, Int?>().reversed())
                ?.collect(Collectors.toList())
        }
    }
}