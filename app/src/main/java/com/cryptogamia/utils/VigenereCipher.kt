package com.cryptogamia.utils

class VigenereCipher {

    companion object {
        var identifiers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"


        private fun generate_key(message: String, key: String): String {
            var result = ""
            var temp = ""
            var x = 0

            for (i in key.indices) {
                if (identifiers.contains(key[i].uppercase())) {
                    temp += key[i].uppercase()
                }
            }
            if (temp.isEmpty()) {
                return "";
            }

            for (i in message.indices) {
                if (identifiers.contains(message[i].uppercase())) {
                    result += identifiers[identifiers.indexOf(temp[x % temp.length].uppercase())]
                    x++
                }

            }

            return result
        }

        fun encrypt(message: String, key: String): String {
            val keycipher = generate_key(message, key)
            if (keycipher.isEmpty()) {
                return ""
            }
            var x: Int = 0
            var i: Int
            var Ki: Int
            var Ei: Int
            var cipher: String = ""

            for (index in message.indices) {
                i = identifiers.indexOf(message[index].uppercase())
                if (i != -1) {
                    Ki = identifiers.indexOf(keycipher[x])
                    Ei = Math.floorMod((i + Ki), identifiers.length)
                    if (identifiers.indexOf(message[index]) != -1) {
                        cipher += identifiers[Ei]
                    } else {
                        cipher += identifiers[Ei].lowercase()
                    }

                    x++
                } else {
                    cipher += message[index]
                }
            }
            return cipher
        }

        fun decrypt(message: String, key: String): String {
            val keycipher = generate_key(message, key)
            if (keycipher.isEmpty()) {
                return ""
            }
            var x: Int = 0
            var i: Int
            var Ki: Int
            var Ei: Int
            var cipher: String = ""

            for (index in message.indices) {
                i = identifiers.indexOf(message[index].uppercase())
                if (i != -1) {
                    Ki = identifiers.indexOf(keycipher[x])
                    Ei = Math.floorMod((i - Ki), identifiers.length)

                    if (identifiers.indexOf(message[index]) != -1) {
                        cipher += identifiers[Ei]
                    } else {
                        cipher += identifiers[Ei].lowercase()
                    }

                    x++
                } else {
                    cipher += message[index]
                }
            }
            return cipher
        }
    }
}