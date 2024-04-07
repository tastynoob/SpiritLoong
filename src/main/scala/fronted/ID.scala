import LoongArch._
import chisel3._
import chisel3.util.BitPat
import chisel3.util.MuxCase
import chisel3.util.Cat
import chisel3.util.Enum
import chisel3.util.switch
import chisel3.util.Fill
import chisel3.util.is
import circt.stage.ChiselStage

abstract trait DecodeConstants {
  // This X should be used only in 1-bit signal. Otherwise, use BitPat("b???") to align with the width of UInt.
  def X = BitPat("b?")
  def N = BitPat("b0")
  def Y = BitPat("b1")

  def decodeDefault: List[UInt] = // illegal instruction
    //   srcType(j) srcType(k) srcType(d) srcType(a) fuType    fuOpType    suffixType
    //   |          |          |          |          |         |           |         ImmType
    //   |          |          |          |          |         |           |         |
    //   |          |          |          |          |         |           |         |
    //   |          |          |          |          |         |           |         |
    //   |          |          |          |          |         |           |         |
    //   |          |          |          |          |         |           |         |
    //   |          |          |          |          |         |           |         |
    List(SrcType.X, SrcType.X, SrcType.X, SrcType.X, FuType.X, FuOpType.X, SuType.X, SelImm.X)

  val table: Array[(BitPat, List[UInt])]
}

object InsDecode extends DecodeConstants {
    val table: Array[(BitPat, List[UInt])] = Array(
        // maybe the ra register don't need to be cared
        // we replace the rk to imm when it need
        // ADD
        LoongIns.ADD_W      ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.add, SuType._W, SelImm.DC),
        LoongIns.ADDI_W     ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.add, SuType._W, SelImm.IMM_SI12),
        LoongIns.ALSL_W     ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.alsl, SuType._W, SelImm.IMM_SA2),
        LoongIns.PCADDI     ->  List(SrcType.DC, SrcType.imm, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.pcaddi, SuType._W, SelImm.IMM_SI20),
        LoongIns.PCADDU12I  ->  List(SrcType.DC, SrcType.imm, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.pcaddu12i, SuType._W, SelImm.IMM_SI20),
        LoongIns.PCALAU12I  ->  List(SrcType.DC, SrcType.imm, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.pcalau12i, SuType._W, SelImm.IMM_SI20),
        // SUB
        LoongIns.SUB_W      ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.add, SuType._W, SelImm.DC),
        // LU
        LoongIns.LU12I_W    ->  List(SrcType.DC, SrcType.imm, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.lu, SuType._W, SelImm.IMM_SI20),
        // COMPARE
        LoongIns.SLT        ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.slt, SuType._W, SelImm.DC),
        LoongIns.SLTU       ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.sltu, SuType._W, SelImm.DC),
        LoongIns.SLTI       ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.slt, SuType._W, SelImm.IMM_SI12),
        LoongIns.SLTUI      ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.sltu, SuType._W, SelImm.IMM_SI12),
        // LOGIC
        LoongIns.AND        ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.and, SuType._W, SelImm.DC),
        LoongIns.OR         ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.or, SuType._W, SelImm.DC),
        LoongIns.NOR        ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.nor, SuType._W, SelImm.DC),
        LoongIns.XOR        ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.xor, SuType._W, SelImm.DC),
        LoongIns.ANDN       ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.andn, SuType._W, SelImm.DC),
        LoongIns.ORN        ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.orn, SuType._W, SelImm.DC),
        LoongIns.ANDI       ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.and, SuType._W, SelImm.IMM_UI12),
        LoongIns.ORI        ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.or, SuType._W, SelImm.IMM_UI12),
        LoongIns.XORI       ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.xor, SuType._W, SelImm.IMM_UI12),
        // MUL
        LoongIns.MUL_W      ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.mul, MDUOpType.mul, SuType._W, SelImm.DC),
        LoongIns.MULH_W     ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.mul, MDUOpType.mulh, SuType._W, SelImm.DC),
        LoongIns.MULH_WU    ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.mul, MDUOpType.mulh, SuType._WU, SelImm.DC),
        LoongIns.DIV_W      ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.div, MDUOpType.div, SuType._W, SelImm.DC),
        LoongIns.DIV_WU     ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.div, MDUOpType.div, SuType._WU, SelImm.DC),
        LoongIns.MOD_W      ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.div, MDUOpType.mod, SuType._W, SelImm.DC),
        LoongIns.MOD_WU     ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.div, MDUOpType.mod, SuType._WU, SelImm.DC),
        LoongIns.SLL_W      ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.sll, SuType._W, SelImm.DC),
        LoongIns.SRL_W      ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.srl, SuType._W, SelImm.DC),
        LoongIns.SRA_W      ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.sra, SuType._W, SelImm.DC),
        LoongIns.ROTR_W     ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.rotr, SuType._W, SelImm.DC),
        LoongIns.SLLI_W     ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.sll, SuType._W, SelImm.IMM_UI5),
        LoongIns.SRLI_W     ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.srl, SuType._W, SelImm.IMM_UI5),
        LoongIns.SRAI_W     ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.sra, SuType._W, SelImm.IMM_UI5),
        LoongIns.ROTRI_W    ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.rotr, SuType._W, SelImm.IMM_UI5),
        LoongIns.EXT_W_B    ->  List(SrcType.reg, SrcType.DC, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.extwb, SuType._W, SelImm.DC),
        LoongIns.EXT_W_H    ->  List(SrcType.reg, SrcType.DC, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.extwh, SuType._W, SelImm.DC),
        LoongIns.CLO_W      ->  List(SrcType.reg, SrcType.DC, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.clo, SuType._W, SelImm.DC),
        LoongIns.CLZ_W      ->  List(SrcType.reg, SrcType.DC, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.clz, SuType._W, SelImm.DC),
        LoongIns.CTO_W      ->  List(SrcType.reg, SrcType.DC, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.cto, SuType._W, SelImm.DC),
        LoongIns.CTZ_W      ->  List(SrcType.reg, SrcType.DC, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.ctz, SuType._W, SelImm.DC),
        LoongIns.BYTEPICK_W ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.imm, FuType.alu, ALUOpType.bytepick, SuType._W, SelImm.IMM_SA2),
        LoongIns.REVB_2H    ->  List(SrcType.reg, SrcType.DC, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.revb2h, SuType._W, SelImm.DC),
        LoongIns.BITREV_4B  ->  List(SrcType.reg, SrcType.DC, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.bitrev4b, SuType._W, SelImm.DC),
        LoongIns.BITREV_W   ->  List(SrcType.reg, SrcType.DC, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.bitrev, SuType._W, SelImm.DC),
        // strange instructions
        LoongIns.BSTRINS_W  ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.imm, FuType.alu, ALUOpType.bstrins, SuType._W, SelImm.DC),
        LoongIns.BSTRPICK_W ->  List(SrcType.DC, SrcType.DC, SrcType.DC, SrcType.DC, FuType.DC, ALUOpType.bstrpick, SuType._W, SelImm.DC),
        // if in alu?
        LoongIns.MASKEQZ    ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.maskeqz, SuType._W, SelImm.DC),
        LoongIns.MASKNEZ    ->  List(SrcType.reg, SrcType.reg, SrcType.reg, SrcType.DC, FuType.alu, ALUOpType.masknez, SuType._W, SelImm.DC),
        // Conditional jump
        LoongIns.BEQ        ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.bju, JumpOpType.beq, SuType.DC, SelImm.IMM_OF16),
        LoongIns.BNE        ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.bju, JumpOpType.bne, SuType.DC, SelImm.IMM_OF16),
        LoongIns.BLT        ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.bju, JumpOpType.blt, SuType.DC, SelImm.IMM_OF16),
        LoongIns.BGE        ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.bju, JumpOpType.bge, SuType.DC, SelImm.IMM_OF16),
        LoongIns.BLTU       ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.bju, JumpOpType.bltu, SuType.DC, SelImm.IMM_OF16),
        LoongIns.BGEU       ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.bju, JumpOpType.bgeu, SuType.DC, SelImm.IMM_OF16),
        LoongIns.BEQZ       ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.bju, JumpOpType.beqz, SuType.DC, SelImm.IMM_OF21),
        LoongIns.BNEZ       ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.bju, JumpOpType.bnez, SuType.DC, SelImm.IMM_OF21),
        // No conditional jump
        LoongIns.B          ->  List(SrcType.pc, SrcType.imm, SrcType.DC, SrcType.DC, FuType.bju, JumpOpType.b, SuType.DC, SelImm.IMM_OF26),
        LoongIns.BL         ->  List(SrcType.pc, SrcType.imm, SrcType.DC, SrcType.DC, FuType.bju, JumpOpType.bl, SuType.DC, SelImm.IMM_OF26),
        LoongIns.JIRL       ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.bju, JumpOpType.jirl, SuType.DC, SelImm.IMM_OF16),
        // load/store
        LoongIns.LD_B       ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.lsu, LSUOpType.ld, SuType._B, SelImm.IMM_SI12),
        LoongIns.LD_H       ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.lsu, LSUOpType.ld, SuType._H, SelImm.IMM_SI12),
        LoongIns.LD_W       ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.lsu, LSUOpType.ld, SuType._W, SelImm.IMM_SI12),
        LoongIns.LD_BU      ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.lsu, LSUOpType.ld, SuType._BU, SelImm.IMM_SI12),
        LoongIns.LD_HU      ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.lsu, LSUOpType.ld, SuType._HU, SelImm.IMM_SI12),
        LoongIns.ST_B       ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.lsu, LSUOpType.st, SuType._B, SelImm.IMM_SI12),
        LoongIns.ST_H       ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.lsu, LSUOpType.st, SuType._H, SelImm.IMM_SI12),
        LoongIns.ST_W       ->  List(SrcType.reg, SrcType.imm, SrcType.reg, SrcType.DC, FuType.lsu, LSUOpType.st, SuType._W, SelImm.IMM_SI12),
        // strange instruction
        LoongIns.PRELD      ->  List(SrcType.DC, SrcType.DC, SrcType.DC, SrcType.DC, FuType.lsu, LSUOpType.preld, SuType.DC, SelImm.DC)
    )

    def getState(ins: UInt, id: Int): UInt = {
        val defaultSrc = 0.U
        val selectedSrc = MuxCase(defaultSrc, table.map { case (index, value) =>
            (ins === index) -> value(id)
        })
        selectedSrc
    }
}

// the info of the ins
class DecInfo extends Bundle {
    val ctrl = new DecCtrlInfo()
    val data = new DecDataInfo()
}

class DecCtrlInfo extends Bundle {
    // the vec include "rk, rj, rd, ra"
    val regEn    = Vec(4, Bool())
    val immEn    = Bool()
    val immType  = SelImm()
    val fuType   = FuType()
    val fuOpType = FuOpType()
}

class DecDataInfo extends Bundle {
    val ins   = UInt(Parameters.INS_WIDTH)
    val pc    = UInt(Parameters.DATA_WIDTH)
    val regs  = Vec (4, UInt(Parameters.REGS_WIDTH))
    val imm   = UInt(Parameters.DATA_WIDTH)
}

// advance to parse the info of the instruction
class ID extends Module {
    // the judge of the types
    val io = IO(new Bundle{
        val ins   = Input (UInt(Parameters.INS_WIDTH ))
        val pc    = Input (UInt(Parameters.DATA_WIDTH))
        val idOut = Output(new DecInfo)
    })

    // define
    val srcType: List[UInt] = List(
        InsDecode.getState(io.ins, 0), InsDecode.getState(io.ins, 1),
        InsDecode.getState(io.ins, 2), InsDecode.getState(io.ins, 3),
        InsDecode.getState(io.ins, 4), InsDecode.getState(io.ins, 5),
        InsDecode.getState(io.ins, 6), InsDecode.getState(io.ins, 7)
    )

    // set the bits of the port
    // ctrl    
    for (i <- 0 until 4) {
        io.idOut.ctrl.regEn(i) := SrcType.isReg(srcType(i))
    }

    io.idOut.ctrl.immEn := !SelImm.isDC(srcType(7))

    io.idOut.ctrl.fuType   := srcType(4)
    io.idOut.ctrl.fuOpType := srcType(5)
    io.idOut.ctrl.immType  := srcType(7)
    // data
    io.idOut.data.ins := io.ins

    io.idOut.data.pc := io.pc

    io.idOut.data.regs(0) := Mux(SrcType.isReg(srcType(0)), io.ins(9,  5 ), 0.U) // rj
    io.idOut.data.regs(1) := Mux(SrcType.isReg(srcType(1)), io.ins(14, 10), 0.U) // rk
    io.idOut.data.regs(2) := Mux(SrcType.isReg(srcType(2)), io.ins(4,  0 ), 0.U) // rd
    io.idOut.data.regs(3) := Mux(SrcType.isReg(srcType(3)), io.ins(19, 15), 0.U) // ra

    io.idOut.data.imm := MuxCase(
        0.U,
        Seq(
            (srcType(7) === SelImm.IMM_SI12) -> IMM_SI12().insExt(io.ins), // Signed extend
            (srcType(7) === SelImm.IMM_UI12) -> IMM_UI12().insExt(io.ins), // Unsigned extend
            (srcType(7) === SelImm.IMM_SA2 ) -> IMM_SA2 ().insExt(io.ins), // ALSL_W, BYTEPICK_W
            (srcType(7) === SelImm.IMM_SI20) -> IMM_SI20().insExt(io.ins),
            (srcType(7) === SelImm.IMM_UI5 ) -> IMM_UI5 ().insExt(io.ins),
            (srcType(7) === SelImm.IMM_OF16) -> IMM_OF16().insExt(io.ins),
            (srcType(7) === SelImm.IMM_OF21) -> IMM_OF21().insExt(io.ins),
            (srcType(7) === SelImm.IMM_OF26) -> IMM_OF26().insExt(io.ins)
        )
    )
}

abstract class Imm {
    // def signExt(len: Int, ins: UInt): UInt = Cat(Fill(len, 1.U), ins)
    // def zeroExt(len: Int, ins: UInt): UInt = Cat(Fill(len, 0.U), )
    def insExt(ins: UInt): UInt
    def mixformat(ins: UInt): UInt
}

case class IMM_SI12() extends Imm {
    override def insExt(ins: UInt): UInt = {
        Cat(Fill(20, 0.U), mixformat(ins))
    }

    override def mixformat(ins: UInt) = {
        Cat(ins(21, 10))
    }
}

case class IMM_UI12() extends Imm {
    override def insExt(ins: UInt): UInt = {
        Cat(Fill(20, 0.U), mixformat(ins))
    }

    override def mixformat(ins: UInt) = {
        Cat(ins(21, 10))
    }
}

case class IMM_SA2()  extends Imm {
    override def insExt(ins: UInt): UInt = {
        Cat(Fill(30, 0.U), mixformat(ins))
    }

    override def mixformat(ins: UInt) = {
        Cat(ins(16, 15))
    }
}

// not sure the extend
case class IMM_SI20() extends Imm {
    override def insExt(ins: UInt): UInt = {
        Cat(Fill(12, 0.U), mixformat(ins))
    }

    override def mixformat(ins: UInt) = {
        Cat(ins(24, 5))
    }
}

case class IMM_UI5()  extends Imm {
    override def insExt(ins: UInt): UInt = {
        Cat(Fill(27, 0.U), mixformat(ins))
    }

    override def mixformat(ins: UInt) = {
        Cat(ins(14, 10))
    }
}

case class IMM_OF16() extends Imm {
    override def insExt(ins: UInt): UInt = {
        Cat(Fill(16, 0.U), mixformat(ins))
    }

    override def mixformat(ins: UInt) = {
        Cat(ins(25, 10))
    }
}

case class IMM_OF21() extends Imm {
    override def insExt(ins: UInt): UInt = {
        Cat(Fill(11, 0.U), mixformat(ins))
    }

    override def mixformat(ins: UInt) = {
        Cat(ins(4, 0), ins(25, 10))
    }
}

case class IMM_OF26() extends Imm {
    override def insExt(ins: UInt): UInt = {
        Cat(Fill(6, 0.U), mixformat(ins))
    }

    override def mixformat(ins: UInt) = {
        Cat(ins(9, 0), ins(25, 10))
    }
}