package org.beangle.style.maven.util


import java.io.Writer
@SerialVersionUID(1L)
class StringBuilderWriter(val builder: StringBuilder) extends Writer with Serializable {

  /**
   * Construct a new {@link StringBuilder} instance with the specified capacity.
   *
   * @param capacity The initial capacity of the underlying {@link StringBuilder}
   */
  def this(capacity: Int = 16) {
    this(new StringBuilder(capacity))
  }

  /**
   * Append a single character to this Writer.
   */
  override def append(value: Char): Writer = {
    builder.append(value)
    this
  }

  /**
   * Append a character sequence to this Writer.
   */
  override def append(value: CharSequence): Writer = {
    builder.append(value)
    this
  }

  /**
   * Append a portion of a character sequence to the {@link StringBuilder}.
   */
  override def append(value: CharSequence, start: Int, end: Int): Writer = {
    builder.append(value, start, end)
    this
  }

  /**
   * Closing this writer has no effect.
   */
  override def close() {
  }

  /**
   * Flushing this writer has no effect.
   */
  override def flush() {
  }

  /**
   * Write a String to the {@link StringBuilder}.
   *
   * @param value The value to write
   */
  override def write(value: String) {
    if (value != null) builder.append(value)
  }

  /**
   * Write a portion of a character array to the {@link StringBuilder}.
   */
  override def write(value: Array[Char], offset: Int, length: Int) {
    if (value != null) builder.appendAll(value, offset, length)
  }

  /**
   * Returns {@link StringBuilder#toString()}.
   */
  override def toString(): String = builder.toString
}
