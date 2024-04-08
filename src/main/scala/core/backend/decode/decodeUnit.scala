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
        ADD_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w,      SelImm.X,        true ),   
        ADDI_W      -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.add_w,      SelImm.IMM_SI12, true ),
        SUB_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.sub_w,      SelImm.X,        true ),
        ALSL_W      -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.alsl_w,     SelImm.IMM_SA2,  true ),
        LU12I_W     -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.lu12i_w,    SelImm.IMM_SI20, true ),        
        SLT         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.slt,        SelImm.X,        true ),
        SLTU        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.sltu,       SelImm.X,        true ),
        SLTI        -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.slt,        SelImm.IMM_SI12, true ),
        SLTUI       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.sltu,       SelImm.IMM_SI12, true ),
        AND         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.and,        SelImm.X,        true ),
        OR          -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.or,         SelImm.X,        true ),
        NOR         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.nor,        SelImm.X,        true ),
        XOR         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.xor,        SelImm.X,        true ),
        ANDN        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.andn,       SelImm.X,        true ),
        ORN         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.orn,        SelImm.X,        true ),
        ANDI        -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.and,        SelImm.IMM_UI12, true ),
        ORI         -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.or,         SelImm.IMM_UI12, true ),
        XORI        -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.xor,        SelImm.IMM_UI12, true ),
        SLL_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.sll_w,      SelImm.X,        true ),
        SRL_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.srl_w,      SelImm.X,        true ),
        SRA_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.sra_w,      SelImm.X,        true ),
        ROTR_W      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.rotr_w,     SelImm.X,        true ),
        SLLI_W      -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.sll_w,      SelImm.IMM_UI5,  true ),
        SRLI_W      -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.srl_w,      SelImm.IMM_UI5,  true ),
        SRAI_W      -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.sra_w,      SelImm.IMM_UI5,  true ),
        ROTRI_W     -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.rotr_w,     SelImm.IMM_UI5,  true ),
        // mdu           
        MUL_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.mul, MDUOpType.mul_w,      SelImm.X,        true ),
        MULH_W      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.mul, MDUOpType.mulh_w,     SelImm.X,        true ),
        MULH_WU     -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.mul, MDUOpType.mulh_wu,    SelImm.X,        true ),
        DIV_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.div, MDUOpType.div_w,      SelImm.X,        true ),
        MOD_W       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.div, MDUOpType.mod_w,      SelImm.X,        true ),
        DIV_WU      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.div, MDUOpType.div_wu,     SelImm.X,        true ),
        MOD_WU      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.div, MDUOpType.mod_wu,     SelImm.X,        true ),
        // bit       
        EXT_W_B     -> DecodeTemplate(SrcType.reg, SrcType.X,   OpType.alu, ALUOpType.ext_w_b,    SelImm.X,        true ),
        EXT_W_H     -> DecodeTemplate(SrcType.reg, SrcType.X,   OpType.alu, ALUOpType.ext_w_h,    SelImm.X,        true ),
        CLO_W       -> DecodeTemplate(SrcType.reg, SrcType.X,   OpType.alu, ALUOpType.clo_w,      SelImm.X,        true ),
        CLZ_W       -> DecodeTemplate(SrcType.reg, SrcType.X,   OpType.alu, ALUOpType.clz_w,      SelImm.X,        true ),
        CTO_W       -> DecodeTemplate(SrcType.reg, SrcType.X,   OpType.alu, ALUOpType.cto_w,      SelImm.X,        true ),
        CTZ_W       -> DecodeTemplate(SrcType.reg, SrcType.X,   OpType.alu, ALUOpType.ctz_w,      SelImm.X,        true ),
        BYTEPICK_W  -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.bytepick_w, SelImm.IMM_SA2,  true ),
        REVB_2H     -> DecodeTemplate(SrcType.reg, SrcType.X,   OpType.alu, ALUOpType.revb_2h,    SelImm.X,        true ),
        BITREV_4B   -> DecodeTemplate(SrcType.reg, SrcType.X,   OpType.alu, ALUOpType.revb_4b,    SelImm.X,        true ),
        BITREV_W    -> DecodeTemplate(SrcType.reg, SrcType.X,   OpType.alu, ALUOpType.bitrev_w,   SelImm.X,        true ),
         
        // special 
        BSTRINS_W   -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.bstrins_w,  SelImm.IMM_MSLS, true ),
        BSTRPICK_W  -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.bstrpick_w, SelImm.IMM_MSLS, true ),
         
        MASKEQZ     -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.maskeqz,    SelImm.X,        true ),
        MASKNEZ     -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.masknez,    SelImm.X,        true ),
        // branch    
        PCADDI      -> DecodeTemplate(SrcType.pc,  SrcType.imm, OpType.bru, BRUOpType.pcaddi,     SelImm.IMM_SI20, true ),
        PCADDU12I   -> DecodeTemplate(SrcType.pc,  SrcType.imm, OpType.bru, BRUOpType.pcaddu12i,  SelImm.IMM_SI20, true ),
        PCALAU12I   -> DecodeTemplate(SrcType.pc,  SrcType.imm, OpType.bru, BRUOpType.pcalau12i,  SelImm.IMM_SI20, true ),
        BEQ         -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.bru, BRUOpType.beq,        SelImm.IMM_OF16, false),
        BNE         -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.bru, BRUOpType.bne,        SelImm.IMM_OF16, false),
        BLT         -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.bru, BRUOpType.blt,        SelImm.IMM_OF16, false),
        BGE         -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.bru, BRUOpType.bge,        SelImm.IMM_OF16, false),
        BLTU        -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.bru, BRUOpType.bltu,       SelImm.IMM_OF16, false),
        BGEU        -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.bru, BRUOpType.bgeu,       SelImm.IMM_OF16, false),
        BEQZ        -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.bru, BRUOpType.beqz,       SelImm.IMM_OF21, false),
        BNEZ        -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.bru, BRUOpType.bnez,       SelImm.IMM_OF21, false),
        B           -> DecodeTemplate(SrcType.pc,  SrcType.imm, OpType.bru, BRUOpType.b,          SelImm.IMM_OF26, false),
        BL          -> DecodeTemplate(SrcType.pc,  SrcType.imm, OpType.bru, BRUOpType.bl,         SelImm.IMM_OF26, false),
        JIRL        -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.bru, BRUOpType.jirl,       SelImm.IMM_OF16, true ),
        // mem   
        LD_B        -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.ldu, LDUOpType.ld_b,       SelImm.IMM_SI12, true ),
        LD_H        -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.ldu, LDUOpType.ld_h,       SelImm.IMM_SI12, true ),
        LD_W        -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.ldu, LDUOpType.ld_w,       SelImm.IMM_SI12, true ),
        LD_BU       -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.ldu, LDUOpType.ld_bu,      SelImm.IMM_SI12, true ),
        LD_HU       -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.ldu, LDUOpType.ld_hu,      SelImm.IMM_SI12, true ),
        ST_B        -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.stu, LDUOpType.st_b,       SelImm.IMM_SI12, true ),
        ST_H        -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.stu, LDUOpType.st_h,       SelImm.IMM_SI12, true ),
        ST_W        -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.stu, LDUOpType.st_w,       SelImm.IMM_SI12, true ),
        PRELD       -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.ldu, LDUOpType.preld,      SelImm.IMM_SI12, false)
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
