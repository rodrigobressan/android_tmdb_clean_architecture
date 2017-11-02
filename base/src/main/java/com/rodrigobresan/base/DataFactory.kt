package com.rodrigobresan.base

import java.util.*
import java.util.concurrent.ThreadLocalRandom


class DataFactory {

    companion object Factory {

        /**
         * Returns a random String object
         *
         */
        fun randomUuid(): String {
            return java.util.UUID.randomUUID().toString()
        }

        /**
         * Returns a random Int object
         */
        fun randomInt(): Int {
            return Random().nextInt()
        }

        /**
         * Returns a random Double object
         */
        fun randomDouble(): Double {
            return Random().nextDouble()
        }

        /**
         * Returns a random Long object
         */
        fun randomLong(): Long {
            return randomInt().toLong()
        }
        /**
         * Returns a random Boolean object
         */
        fun randomBoolean(): Boolean {
            return Math.random() < 0.5
        }
    }

}