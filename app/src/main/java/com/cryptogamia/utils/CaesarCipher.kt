package com.cryptogamia.utils

class CaesarCipher {

    companion object {
        var identifiers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        fun encrypt(message: String, key: Int): String {
            var cipher = "";

            for (i in message.indices) {
                if (identifiers.contains(message[i]) || identifiers.lowercase()
                        .contains(message[i])
                ) {
                    val index =
                        Math.floorMod(
                            identifiers.indexOf(message[i].uppercase()) + key,
                            identifiers.length
                        )
                    if (identifiers.lowercase().contains(message[i])) {
                        cipher += identifiers[index].lowercase()
                    } else {
                        cipher += identifiers[index]
                    }
                } else {
                    cipher += message[i]
                }
            }

            return cipher;
        }

        fun decrypt(cipher: String, key: Int): String {
            var message = "";

            for (i in cipher.indices) {
                if (identifiers.contains(cipher[i]) || identifiers.lowercase()
                        .contains(cipher[i])
                ) {
                    val index =
                        Math.floorMod(
                            identifiers.indexOf(cipher[i].uppercase()) - key,
                            identifiers.length
                        )

                    if (identifiers.lowercase().contains(cipher[i])) {
                        message += identifiers[index].lowercase()
                    } else {
                        message += identifiers[index]
                    }
                } else {
                    message += cipher[i]
                }
            }

            return message;
        }

        fun bruteforce(cipher: String): Map<Int, String> {
            val res = HashMap<Int, String>()
            for (key in 1..25) {
                res[key] = decrypt(cipher, key)
            }

            return res
        }
    }
}
