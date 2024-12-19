@file:Suppress("NOTHING_TO_INLINE")

package utils

import java.io.BufferedReader
import java.io.Reader

inline fun Reader.readChars() = readText().toCharArray()

inline fun BufferedReader.skipLine() { readLine() }
