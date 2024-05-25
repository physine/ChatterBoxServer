package physine.routing

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import physine.utils.UUIDSerializer
import java.util.*

val json = Json {
    encodeDefaults = true
    serializersModule = SerializersModule {
        contextual(UUID::class, UUIDSerializer)
    }
}