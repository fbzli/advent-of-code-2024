package utils

import java.io.Reader

fun Reader.readChars() = readText().toCharArray()
