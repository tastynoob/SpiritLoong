import chisel3._

class PipelineRegister extends Module {
    val io = IO(new Bundle{
        val wen      = Input (Bool()) // write enable
        val flush_en = Input (Bool()) // flush pipeline
        val din      = Input (UInt(Parameters.INS_WIDTH))
        val dout     = Output(UInt(Parameters.INS_WIDTH))
    })

    val reg = RegInit(Parameters.INS_WIDTH, Parameters.DefaultValue)

    when (io.flush_en) {
        reg := Parameters.DefaultValue
    }.elsewhen(io.wen) {
        reg := io.din
    }

    io.dout := reg
}