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
        // alu
        ADD_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)   
        ADDI_W      -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.addi_w, SelImm.IMM_SI12, true)
        SUB_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.sub_w, SelImm.X, true)
        // reserve
        ALSL_W      -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.alsl_w, SelImm.IMM_SA2, true)
        LU12I_W     -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.lu12i_w, SelImm.IMM_SI20, true)
        
        SLT         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.slt, SelImm.X, true)
        SLTU        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.sltu, SelImm.X, true)
        SLTI        -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.slti, SelImm.IMM_SI12, true)
        SLTUI       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.sltui, SelImm.IMM_SI12, true)
        ADDI_D      -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        AND         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        OR          -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        NOR         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        XOR         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        ANDN        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        ORN         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        ANDI        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        ORI         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        XORI        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        SLL_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        SRL_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        SRA_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        ROTR_W      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        SLLI_W      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        SRLI_W      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        SRAI_W      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        ROTRI_W     -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        // mdu       
        MUL_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        MULH_W      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        MULH_WU     -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        DIV_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        MOD_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        DIV_WU      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        MOD_WU      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        // bit   
        EXT_W_B     -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        EXT_W_H     -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        CLO_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        CLZ_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        CTO_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        CTZ_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        BYTEPICK_W  -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        REVB_2H     -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        BITREV_4B   -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        BITREV_W    -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        BSTRINS_W   -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        BSTRPICK_W  -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        MASKEQZ     -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        MASKNEZ     -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        // branch   
        PCADDI      -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.bru, ALUOpType.pcaddi, SelImm.IMM_SI20, true)
        PCADDU12I   -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.bru, ALUOpType.pcaddu12i, SelImm.IMM_SI20, true)
        PCALAU12I   -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.bru, ALUOpType.pcalau12i, SelImm.IMM_SI20, true)
        BEQ         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        BNE         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        BLT         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        BGE         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        BLTU        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        BGEU        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        BEQZ        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        BNEZ        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        B           -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        BL          -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        JIRL        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        // mem   
        LD_B        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        LD_H        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        LD_W        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        LD_BU       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        LD_HU       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        ST_B        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        ST_H        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        ST_W        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
        PRELD       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true)
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
