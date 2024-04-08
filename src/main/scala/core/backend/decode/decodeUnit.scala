package core.backend.decoder

import chisel3._
import chisel3.util._

import core.insts._
import core.insts.LA32RInsts._

object DecodeTemplate {
    implicit def uintToBitPat(x: UInt): BitPat = BitPat(x)
    def apply(
        srcType0: UInt = SrcType.X,
        srcType1: UInt = SrcType.X,
        opc: UInt = OpType.X,
        micOp: UInt = UOpType.X,
        selImm: UInt = SelImm.X,
        irfwen: Boolean = false
    ): List[UInt] = List(srcType0, srcType1, opc, micOp, selImm, irfwen.B)
}

class DecFlow extends Bundle {
    val code = UInt(32.W)
    val pc   = UInt(32.W)

    val ldst  = UInt(5.W)
    val lsrcs = Vec(2, UInt(5.W))
    val imm   = UInt(SelImm.ImmMaxLen.W)

    val srcType = Vec(2, SrcType())
    val opc     = OpType()
    val micOp   = UOpType()
    val selImm  = SelImm()
    val irfwen  = Bool()

    def allSignals = srcType ++ Seq(opc, micOp, selImm, irfwen)

    def decode(code: UInt, args: Seq[UInt]) = {
        require(allSignals.size == args.size)
        allSignals.zip(args).foreach({ case (s, a) => s := a })
        this.code := code
        ldst      := code(4, 0)   // rd
        lsrcs(0)  := code(9, 5)   // rj
        lsrcs(1)  := code(14, 10) // rk
        when(opc === OpType.bru) {
            lsrcs(1) := code(4, 0) // replace rk to rd
        }
        imm := MuxCase(
            0.U,
            Seq(
                (selImm === SelImm.IMM_SI12) -> IMM_SI12(code)
            )
        )
        this
    }
}

object InstDecode {
    val table: Array[(BitPat, List[UInt])] = Array(
        ADD_W -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
    )
}

class DecodeUnit extends Module {
    val io = IO(new Bundle {
        val code    = Input(UInt(32.W))
        val pc      = Input(UInt(32.W))
        val decflow = Output(new DecFlow)
    })

    val decodeTable = InstDecode.table
    val decodeInfo  = ListLookup(io.code, DecodeTemplate(), decodeTable).toArray

    val inst = Wire(new DecFlow).decode(io.code, decodeInfo)
    inst.pc    := io.pc
    io.decflow := inst
}
