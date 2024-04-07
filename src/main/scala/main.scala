import chisel3._
import chisel3.util.Cat
import chisel3.util.Enum
import chisel3.util.switch
import chisel3.util.Fill
import chisel3.util.is
import circt.stage.ChiselStage

class test extends Module {

}

object Main extends App {
    // These lines generate the Verilog output
    println(
        ChiselStage.emitSystemVerilogFile(
            new test(32),
            firtoolOpts = Array(
                "-disable-all-randomization",
                "-strip-debug-info",
                "-o=./generated/test.sv"
                // "--help"
            )
        )
    )
}