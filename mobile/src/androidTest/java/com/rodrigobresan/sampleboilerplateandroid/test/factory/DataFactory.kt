package com.rodrigobresan.sampleboilerplateandroid.test.factory

import java.util.concurrent.ThreadLocalRandom


class DataFactory {

    companion object Factory {

        fun randomUuid(): String {
            return java.util.UUID.randomUUID().toString()
        }

        fun randomInt(): Int {
            return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
        }

        fun randomLong(): Long {
            return randomInt().toLong()
        }

        fun randomBoolean(): Boolean {
            return Math.random() < 0.5
        }

        fun makeStringList(count: Int): List<String> {
            val items: MutableList<String> = mutableListOf()
            repeat(count) {
                items.add(randomUuid())
            }
            return items
        }

        fun randomDouble(): Double {
            return ThreadLocalRandom.current().nextDouble(0.0, 1000.0 + 1)
        }

    }

}
