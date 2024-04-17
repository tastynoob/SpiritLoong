package core.insts

import chisel3._
import chisel3.util._

import core.backend._
import core.backend.decoder.DecodeTemplate

package object ArchLa32R {
    object InstDecode {
        // alu
        val ADD_W     = BitPat("b00000000000100000???????????????")
        val ADDI_W    = BitPat("b0000001010??????????????????????")
        val SUB_W     = BitPat("b00000000000100010???????????????")
        val ALSL_W    = BitPat("b000000000000010?????????????????")
        val LU12I_W   = BitPat("b0001010?????????????????????????")
        val SLT       = BitPat("b00000000000100100???????????????")
        val SLTU      = BitPat("b00000000000100101???????????????")
        val SLTI      = BitPat("b0000001000??????????????????????")
        val SLTUI     = BitPat("b0000001001??????????????????????")
        val PCADDI    = BitPat("b0001100?????????????????????????")
        val PCADDU12I = BitPat("b0001110?????????????????????????")
        val PCALAU12I = BitPat("b0001101?????????????????????????")
        val AND       = BitPat("b00000000000101001???????????????")
        val OR        = BitPat("b00000000000101010???????????????")
        val NOR       = BitPat("b00000000000101000???????????????")
        val XOR       = BitPat("b00000000000101011???????????????")
        val ANDN      = BitPat("b00000000000101101???????????????")
        val ORN       = BitPat("b00000000000101100???????????????")
        val ANDI      = BitPat("b0000001101??????????????????????")
        val ORI       = BitPat("b0000001110??????????????????????")
        val XORI      = BitPat("b0000001111??????????????????????")
        val SLL_W     = BitPat("b00000000001000011???????????????")
        val SRL_W     = BitPat("b00000000000101111???????????????")
        val SRA_W     = BitPat("b00000000000110000???????????????")
        val ROTR_W    = BitPat("b00000000000110110???????????????")
        val SLLI_W    = BitPat("b00000000010000001???????????????")
        val SRLI_W    = BitPat("b00000000010001001???????????????")
        val SRAI_W    = BitPat("b00000000010010001???????????????")
        val ROTRI_W   = BitPat("b00000000010011001???????????????")

        // mdu
        val MUL_W   = BitPat("b00000000000111000???????????????")
        val MULH_W  = BitPat("b00000000000111001???????????????")
        val MULH_WU = BitPat("b00000000000111010???????????????")
        val DIV_W   = BitPat("b00000000001000000???????????????")
        val MOD_W   = BitPat("b00000000001000001???????????????")
        val DIV_WU  = BitPat("b00000000001000010???????????????")
        val MOD_WU  = BitPat("b00000000001000011???????????????")

        // bit
        val EXT_W_B    = BitPat("b0000000000000000010111??????????")
        val EXT_W_H    = BitPat("b0000000000000000010110??????????")
        val CLO_W      = BitPat("b0000000000000000000100??????????")
        val CLZ_W      = BitPat("b0000000000000000000101??????????")
        val CTO_W      = BitPat("b0000000000000000000110??????????")
        val CTZ_W      = BitPat("b0000000000000000000111??????????")
        val BYTEPICK_W = BitPat("b000000000000100?????????????????")
        val REVB_2H    = BitPat("b0000000000000000001100??????????")
        val BITREV_4B  = BitPat("b0000000000000000010010??????????")
        val BITREV_W   = BitPat("b0000000000000000010100??????????")
        val BSTRINS_W  = BitPat("b00000000011?????0???????????????")
        val BSTRPICK_W = BitPat("b00000000011?????1???????????????")
        val MASKEQZ    = BitPat("b00000000000100110???????????????")
        val MASKNEZ    = BitPat("b00000000000100111???????????????")
        // branch
        val BEQ  = BitPat("b010110??????????????????????????")
        val BNE  = BitPat("b010111??????????????????????????")
        val BLT  = BitPat("b011000??????????????????????????")
        val BGE  = BitPat("b011001??????????????????????????")
        val BLTU = BitPat("b011010??????????????????????????")
        val BGEU = BitPat("b011011??????????????????????????")
        val BEQZ = BitPat("b010000??????????????????????????")
        val BNEZ = BitPat("b010001??????????????????????????")
        val B    = BitPat("b010100??????????????????????????")
        val BL   = BitPat("b010101??????????????????????????")
        val JIRL = BitPat("b010011??????????????????????????")
        // mem
        val LD_B  = BitPat("b0010100000??????????????????????")
        val LD_H  = BitPat("b0010100001??????????????????????")
        val LD_W  = BitPat("b0010100010??????????????????????")
        val LD_BU = BitPat("b0010101000??????????????????????")
        val LD_HU = BitPat("b0010101001??????????????????????")
        val ST_B  = BitPat("b0010100100??????????????????????")
        val ST_H  = BitPat("b0010100101??????????????????????")
        val ST_W  = BitPat("b0010100110??????????????????????")
        val PRELD = BitPat("b0010101011??????????????????????")

        val table: Array[(BitPat, List[UInt])] = Array(
            // alu
            ADD_W   -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.add_w, SelImm.X, true),
            ADDI_W  -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.add_w, SelImm.IMM_SI12, true),
            SUB_W   -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.sub_w, SelImm.X, true),
            ALSL_W  -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.alsl_w, SelImm.IMM_SA2, true),
            LU12I_W -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.lu12i_w, SelImm.IMM_SI20, true),
            SLT     -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.slt, SelImm.X, true),
            SLTU    -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.sltu, SelImm.X, true),
            SLTI    -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.slt, SelImm.IMM_SI12, true),
            SLTUI   -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.sltu, SelImm.IMM_SI12, true),
            AND     -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.and, SelImm.X, true),
            OR      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.or, SelImm.X, true),
            NOR     -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.nor, SelImm.X, true),
            XOR     -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.xor, SelImm.X, true),
            ANDN    -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.andn, SelImm.X, true),
            ORN     -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.orn, SelImm.X, true),
            ANDI    -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.and, SelImm.IMM_UI12, true),
            ORI     -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.or, SelImm.IMM_UI12, true),
            XORI    -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.xor, SelImm.IMM_UI12, true),
            SLL_W   -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.sll_w, SelImm.X, true),
            SRL_W   -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.srl_w, SelImm.X, true),
            SRA_W   -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.sra_w, SelImm.X, true),
            ROTR_W  -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.rotr_w, SelImm.X, true),
            SLLI_W  -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.sll_w, SelImm.IMM_UI5, true),
            SRLI_W  -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.srl_w, SelImm.IMM_UI5, true),
            SRAI_W  -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.sra_w, SelImm.IMM_UI5, true),
            ROTRI_W -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.rotr_w, SelImm.IMM_UI5, true),
            // mdu
            MUL_W   -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.mul, MDUOpType.mul_w, SelImm.X, true),
            MULH_W  -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.mul, MDUOpType.mulh_w, SelImm.X, true),
            MULH_WU -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.mul, MDUOpType.mulh_wu, SelImm.X, true),
            DIV_W   -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.div, MDUOpType.div_w, SelImm.X, true),
            MOD_W   -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.div, MDUOpType.mod_w, SelImm.X, true),
            DIV_WU  -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.div, MDUOpType.div_wu, SelImm.X, true),
            MOD_WU  -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.div, MDUOpType.mod_wu, SelImm.X, true),
            // bit
            EXT_W_B    -> DecodeTemplate(SrcType.reg, SrcType.X, OpType.alu, ALUOpType.ext_w_b, SelImm.X, true),
            EXT_W_H    -> DecodeTemplate(SrcType.reg, SrcType.X, OpType.alu, ALUOpType.ext_w_h, SelImm.X, true),
            CLO_W      -> DecodeTemplate(SrcType.reg, SrcType.X, OpType.alu, ALUOpType.clo_w, SelImm.X, true),
            CLZ_W      -> DecodeTemplate(SrcType.reg, SrcType.X, OpType.alu, ALUOpType.clz_w, SelImm.X, true),
            CTO_W      -> DecodeTemplate(SrcType.reg, SrcType.X, OpType.alu, ALUOpType.cto_w, SelImm.X, true),
            CTZ_W      -> DecodeTemplate(SrcType.reg, SrcType.X, OpType.alu, ALUOpType.ctz_w, SelImm.X, true),
            BYTEPICK_W -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.bytepick_w, SelImm.IMM_SA2, true),
            REVB_2H    -> DecodeTemplate(SrcType.reg, SrcType.X, OpType.alu, ALUOpType.revb_2h, SelImm.X, true),
            BITREV_4B  -> DecodeTemplate(SrcType.reg, SrcType.X, OpType.alu, ALUOpType.revb_4b, SelImm.X, true),
            BITREV_W   -> DecodeTemplate(SrcType.reg, SrcType.X, OpType.alu, ALUOpType.bitrev_w, SelImm.X, true),

            // special
            BSTRINS_W  -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.bstrins_w, SelImm.IMM_MSLS, true),
            BSTRPICK_W -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.alu, ALUOpType.bstrpick_w, SelImm.IMM_MSLS, true),
            MASKEQZ    -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.maskeqz, SelImm.X, true),
            MASKNEZ    -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.alu, ALUOpType.masknez, SelImm.X, true),
            // branch
            PCADDI    -> DecodeTemplate(SrcType.reg, SrcType.X, OpType.bru, BRUOpType.pcaddi, SelImm.IMM_SI20, true),
            PCADDU12I -> DecodeTemplate(SrcType.reg, SrcType.X, OpType.bru, BRUOpType.pcaddu12i, SelImm.IMM_SI20, true),
            PCALAU12I -> DecodeTemplate(SrcType.reg, SrcType.X, OpType.bru, BRUOpType.pcalau12i, SelImm.IMM_SI20, true),
            BEQ       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.bru, BRUOpType.beq, SelImm.IMM_OF16, false),
            BNE       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.bru, BRUOpType.bne, SelImm.IMM_OF16, false),
            BLT       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.bru, BRUOpType.blt, SelImm.IMM_OF16, false),
            BGE       -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.bru, BRUOpType.bge, SelImm.IMM_OF16, false),
            BLTU      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.bru, BRUOpType.bltu, SelImm.IMM_OF16, false),
            BGEU      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.bru, BRUOpType.bgeu, SelImm.IMM_OF16, false),
            BEQZ      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.bru, BRUOpType.beqz, SelImm.IMM_OF21, false),
            BNEZ      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.bru, BRUOpType.bnez, SelImm.IMM_OF21, false),
            B         -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.bru, BRUOpType.b, SelImm.IMM_OF26, false),
            BL        -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.bru, BRUOpType.bl, SelImm.IMM_OF26, false),
            JIRL      -> DecodeTemplate(SrcType.reg, SrcType.reg, OpType.bru, BRUOpType.jirl, SelImm.IMM_OF16, true),
            // mem
            LD_B -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.ldu, LDUOpType.ld_b, SelImm.IMM_SI12, true)
            // LD_H  -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.ldu, LDUOpType.ld_h, SelImm.IMM_SI12, true),
            // LD_W  -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.ldu, LDUOpType.ld_w, SelImm.IMM_SI12, true),
            // LD_BU -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.ldu, LDUOpType.ld_bu, SelImm.IMM_SI12, true),
            // LD_HU -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.ldu, LDUOpType.ld_hu, SelImm.IMM_SI12, true),
            // ST_B  -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.stu, LDUOpType.st_b, SelImm.IMM_SI12, true),
            // ST_H  -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.stu, LDUOpType.st_h, SelImm.IMM_SI12, true),
            // ST_W  -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.stu, LDUOpType.st_w, SelImm.IMM_SI12, true),
            // PRELD -> DecodeTemplate(SrcType.reg, SrcType.imm, OpType.ldu, LDUOpType.preld, SelImm.IMM_SI12, false)
        )
    }

    object SrcType {
        def wid = 2.W
        def X   = 0.U(wid)
        def reg = 0.U(wid)
        def imm = 1.U(wid)

        def apply() = UInt(wid)

        def isReg(x: UInt) = x === reg
        def isImm(x: UInt) = x === imm
    }

    object OpType {
        def wid = 3.W

        def X       = 0.U(wid)
        def alu     = 1.U(wid)
        def bru     = 2.U(wid)
        def mul     = 3.U(wid)
        def div     = 4.U(wid)
        def ldu     = 5.U(wid)
        def stu     = 6.U(wid)
        def apply() = UInt(wid)
    }

    object UOpType {
        def wid     = 6.W
        def apply() = UInt(6.W)
        def X       = 0.U(wid)
    }

    object SelImm {
        def wid      = 4.W
        def X        = 0.U(wid)
        def IMM_SI12 = 1.U(wid)
        def IMM_UI12 = 2.U(wid)
        def IMM_SA2  = 3.U(wid)
        def IMM_SI20 = 4.U(wid)
        def IMM_UI5  = 5.U(wid)
        def IMM_OF16 = 6.U(wid)
        def IMM_OF21 = 7.U(wid)
        def IMM_OF26 = 8.U(wid)
        def IMM_MSLS = 9.U(wid) // special
        def DC       = 10.U(wid)

        def ImmMaxLen = 26

        def isDC(srcType: UInt) = srcType === DC

        def apply() = UInt(4.W)
    }

    object ALUOpType {
        private def wid = UOpType.wid

        def add_w      = 0.U(wid)
        def sub_w      = 1.U(wid)
        def alsl_w     = 2.U(wid)
        def lu12i_w    = 3.U(wid)
        def slt        = 4.U(wid)
        def sltu       = 5.U(wid)
        def and        = 6.U(wid)
        def or         = 7.U(wid)
        def nor        = 8.U(wid)
        def xor        = 9.U(wid)
        def andn       = 10.U(wid)
        def orn        = 11.U(wid)
        def sll_w      = 12.U(wid)
        def srl_w      = 13.U(wid)
        def sra_w      = 14.U(wid)
        def rotr_w     = 15.U(wid)
        def ext_w_b    = 16.U(wid)
        def ext_w_h    = 17.U(wid)
        def clo_w      = 18.U(wid)
        def clz_w      = 19.U(wid)
        def cto_w      = 20.U(wid)
        def ctz_w      = 21.U(wid)
        def bytepick_w = 22.U(wid)
        def revb_2h    = 23.U(wid)
        def revb_4b    = 24.U(wid)
        def bitrev_w   = 25.U(wid)
        def bstrins_w  = 26.U(wid)
        def bstrpick_w = 27.U(wid)
        def maskeqz    = 28.U(wid)
        def masknez    = 29.U(wid)
    }

    object BRUOpType {
        private def wid = UOpType.wid

        def pcaddi    = 0.U(wid)
        def pcaddu12i = 1.U(wid)
        def pcalau12i = 2.U(wid)
        def beq       = 3.U(wid)
        def bne       = 4.U(wid)
        def blt       = 5.U(wid)
        def bge       = 6.U(wid)
        def bltu      = 7.U(wid)
        def bgeu      = 8.U(wid)
        def beqz      = 9.U(wid)
        def bnez      = 10.U(wid)
        def b         = 11.U(wid)
        def bl        = 12.U(wid)
        def jirl      = 13.U(wid)
    }

    object MDUOpType {
        private def wid = UOpType.wid

        def mul_w   = 0.U(wid)
        def mulh_w  = 1.U(wid)
        def mulh_wu = 2.U(wid)
        def div_w   = 3.U(wid)
        def mod_w   = 4.U(wid)
        def div_wu  = 5.U(wid)
        def mod_wu  = 6.U(wid)
    }

    object LDUOpType {
        private def wid = UOpType.wid

        /** micro opcode width */
        private def opcwid = 3.W
        def opcld          = 0.U(opcwid)
        def opcst          = 1.U(opcwid)
        def opcpf          = 2.U(opcwid)

        /** operand width */
        private def opewid = 2.W
        def op8            = 0.U(opewid)
        def op16           = 1.U(opewid)
        def op32           = 2.U(opewid)

        /** extension width */
        private def extwid = 1.W
        def extU           = 0.U(extwid)
        def extS           = 1.U(extwid)

        // opc + ope + ext
        def ld_b  = ("b" + "000" + "00" + "1").U
        def ld_h  = ("b" + "000" + "01" + "1").U
        def ld_w  = ("b" + "000" + "10" + "1").U
        def ld_bu = ("b" + "000" + "00" + "0").U
        def ld_hu = ("b" + "000" + "01" + "0").U

        def st_b = ("b" + "001" + "00" + "0").U
        def st_h = ("b" + "001" + "01" + "0").U
        def st_w = ("b" + "001" + "10" + "0").U

        def preld = ("b" + "010" + "00" + "0").U

        def getopc(x: UInt) = {
            x(5, 3)
        }
        def getope(x: UInt) = {
            x(2, 1)
        }
        def signExt(x: UInt) = {
            x(0) === extS
        }
    }

    object IMM_SI12 {
        def apply(code: UInt) = {
            code(21, 10)
        }
    }

    object IMM_UI12 {
        def apply(code: UInt) = {
            IMM_SI12(code)
        }
    }

    object IMM_SA2 {
        def apply(code: UInt) = {
            code(16, 15)
        }
    }

    object IMM_SI20 {
        def apply(code: UInt) = {
            code(24, 5)
        }
    }

    object IMM_UI5 {
        def apply(code: UInt) = {
            code(14, 10)
        }
    }

    object IMM_OF16 {
        def apply(code: UInt) = {
            code(25, 10)
        }
    }

    object IMM_OF21 {
        def apply(code: UInt) = {
            Cat(code(4, 0), code(25, 10))
        }
    }

    case class IMM_OF26() {
        def apply(code: UInt) = {
            Cat(code(9, 0), code(25, 10))
        }
    }
}
