@file:JvmName("ExportContentUtil")

package com.github.tkaczenko.lifepanel.util

import org.apache.commons.codec.binary.Base64

fun convertContentToDataUri(mimeType: String?, content: ByteArray?): String? {
    return java.lang.String.format("data:%s;base64,%s", mimeType, Base64.encodeBase64String(content))
}
