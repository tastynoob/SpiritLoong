package LoongArch
import chisel3._

object Parameters {
    val DATA_BITS = 32
    val DATA_WIDTH = DATA_BITS.W

    val ADDR_REGS = 5
    val REGS_WIDTH = ADDR_REGS.W

    val INS_WIDTH = 32.W

    val DefaultValue = 0.U
}