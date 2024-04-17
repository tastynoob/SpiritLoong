import chisel3._
import chisel3.util.Counter
import circt.stage.ChiselStage

import core.CoreParams
import core.backend.decode.DecodeStage

object Main extends App {
    implicit val p: core.CoreParams = new CoreParams(
        DecodeWdith=2
    )
    // These lines generate the Verilog output
    println(
        ChiselStage.emitSystemVerilogFile(
            new DecodeStage(),
            firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
        )
    )
}
