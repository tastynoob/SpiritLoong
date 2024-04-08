package core

import chisel3._
import chisel3.util._

class FetchFlow extends Bundle {
    val code = UInt(32.W)
    val pc   = UInt(32.W)
    // TODO: fetch except
}
