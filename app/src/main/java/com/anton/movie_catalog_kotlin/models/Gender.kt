package com.anton.movie_catalog_kotlin.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = Gender.Serializer::class)
enum class Gender {
    MALE, FEMALE;

    object Serializer : KSerializer<Gender> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("GenderSerializer", PrimitiveKind.INT)

        override fun deserialize(decoder: Decoder): Gender {
            val value = decoder.decodeInt()
            return when (value) {
                0 -> MALE
                1 -> FEMALE
                else -> throw SerializationException("Invalid Gender value: $value")
            }
        }

        override fun serialize(encoder: Encoder, value: Gender) {
            when (value) {
                MALE -> 0
                FEMALE -> 1
            }.let { encoder.encodeInt(it) }
        }
    }
}

