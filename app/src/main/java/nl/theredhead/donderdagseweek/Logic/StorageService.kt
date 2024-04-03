package nl.theredhead.donderdagseweek.Logic

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import java.io.File
import kotlin.io.path.Path

import androidx.core.content.ContentResolverCompat as getFilesDir

interface StorageServiceSerializer<T> {
    fun serialize(obj: T): String;

    fun deserialize(str: String): T;
}
class StorageService<T>(val name: String, val context: Context, val serializer: StorageServiceSerializer<T>) {

    var cache: T? = null;

    fun file(): File {
        return File(context.filesDir.path, "$name-store.bin")
    }

    fun exists(): Boolean {
        return file().exists()
    }
    fun save(obj: T) {
        val serialized = serializer.serialize(obj);
        file().writeText(serialized, Charsets.UTF_8);
        cache = null;
    }
    fun load(): T? {
        return if (cache != null) {
            cache
        } else try {
            val serialized = file().readText()
            val value = serializer.deserialize(serialized);
            cache = value;
            value;
        } catch (err: Throwable) {
            cache = null;
            null;
        }
    }

    fun remove() {
        file().delete()
    }
}