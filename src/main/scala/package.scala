import chisel3._
import chisel3.util._

object SrcType {
    def reg = "b000".U
    def pc  = "b001".U
    def imm = "b010".U
    def DC  = "b011".U // Don't Care

    def X   = "b100".U

    def isReg(srcType: UInt): Bool = srcType === reg
    def isPc(srcType: UInt): Bool = srcType === pc
    def isImm(srcType: UInt): Bool = srcType === imm
    def isDC(srcType: UInt): Bool = srcType === DC

    def apply() = UInt(3.W)
}

object FuType {
    def alu          = "b0000".U
    def mul          = "b0001".U
    def div          = "b0010".U
    def bju          = "b0011".U
    def lsu          = "b0100".U

    def DC           = "b0101".U
    def X            = "b1111".U

    def apply()      = UInt(4.W)
}

object FuOpType {
    def apply() = UInt(7.W)
    def X = "b1111111".U
}

object ALUOpType {
    def add        = "b000_0000".U
    def sub        = "b000_0001".U
    def alsl       = "b000_0010".U
    def lu         = "b000_0011".U
    def slt        = "b000_0100".U
    def sltu       = "b000_0101".U
    def pcaddi     = "b000_0110".U
    def pcaddu12i  = "b000_0111".U
    def pcalau12i  = "b000_1000".U
    def and        = "b000_1001".U
    def or         = "b000_1010".U
    def nor        = "b000_1011".U
    def xor        = "b000_1100".U
    def andn       = "b000_1101".U
    def orn        = "b000_1110".U
    def sll        = "b000_1111".U
    def srl        = "b001_0000".U
    def sra        = "b001_0001".U
    def rotr       = "b001_0010".U
    def extwb      = "b001_0011".U
    def extwh      = "b001_0100".U
    def clo        = "b001_0101".U
    def clz        = "b001_0110".U
    def cto        = "b001_0111".U
    def ctz        = "b001_1000".U
    def bytepick   = "b001_1001".U
    def revb2h     = "b001_1010".U
    def bitrev4b   = "b001_1011".U
    def bitrev     = "b001_1100".U
    def bstrins    = "b001_1101".U
    def bstrpick   = "b001_1110".U
    def maskeqz    = "b001_1111".U
    def masknez    = "b010_0000".U
}

object MDUOpType {
    def mul        = "b000_0000".U
    def mulh       = "b000_0001".U
    def div        = "b000_0010".U
    def mod        = "b000_0010".U
}

object JumpOpType {
    def beq        = "b000_0000".U
    def bne        = "b000_0001".U
    def blt        = "b000_0010".U
    def bge        = "b000_0011".U
    def bltu       = "b000_0110".U
    def bgeu       = "b000_0111".U
    def beqz       = "b000_0100".U
    def bnez       = "b000_0101".U
    def b          = "b000_0110".U
    def bl         = "b000_0111".U
    def jirl       = "b000_1000".U
}

object LSUOpType {
    def ld         = "b000_0000".U
    def st         = "b000_0001".U
    def preld      = "b000_0010".U
}

object SelImm {
    def IMM_SI12     = "b0000".U
    def IMM_UI12     = "b0001".U
    def IMM_SA2      = "b0010".U
    def IMM_SI20     = "b0011".U
    def IMM_UI5      = "b0100".U
    def IMM_OF16     = "b0101".U
    def IMM_OF21     = "b1001".U
    def IMM_OF26     = "b1010".U
    def DC           = "b1010".U

    def X            = "b1111".U

    def isDC(srcType: UInt) = srcType === DC

    def apply() = UInt(4.W)
}

// Integer
object SuType {
    def _B   = "b0000".U
    def _H   = "b0001".U
    def _W   = "b0010".U
    def _D   = "b0011".U
    def _BU  = "b0100".U
    def _HU  = "b0101".U
    def _WU  = "b0110".U
    def _DU  = "b0111".U

    def DC   = "b1000".U
    def X    = "b1111".U

    def apply() = UInt(4.W)
}


object LsState {
    def load    = "b00".U
    def stroe   = "b01".U
    def DC      = "b10".U
  
    def X       = "b11".U

    def apply() = UInt(2.W)
}