package core.backend.decode

import chisel3._
import chisel3.util._
import chisel3.util.Pipe._
import core.CoreParams
import core.FetchFlow
import core.backend.decoder.DecFlow
import core.backend.decoder.DecodeUnit

class DecodeStage(implicit p: CoreParams) extends Module {
    val io = IO(new Bundle {
        val in    = Vec(p.DecodeWdith, Flipped(ValidIO(new FetchFlow)))
        val out   = Vec(p.DecodeWdith, ValidIO(new DecFlow))
        val stall = new Bundle {
            val in  = Input(Bool())
            val out = Output(Bool())
        }
    })
    io.stall.out := io.stall.in

    for (i <- 0 until p.DecodeWdith) {
        val dec = Module(new DecodeUnit)
        dec.io.code := io.in(i).bits.code
        dec.io.pc   := io.in(i).bits.pc

        io.out(i).bits  := dec.io.decflow
        io.out(i).valid := io.in(i).valid
    }
}
