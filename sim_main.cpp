#include "Vtest.h"
#include "verilated.h"
#include <stdio.h>
#include <verilated_vcd_c.h>
#include <iostream>

#define MAX_TICKS 100
#define CLK clock
#define RIS_EDGE(cylc) ((2 * cylc) - 1)
#define NEG_EDGE(cylc) (2 * cylc)
#define RESET reset
static uint64_t sim_ticks;

int test1()
{
    VerilatedContext *contextp = new VerilatedContext;
    VerilatedVcdC *tracep = new VerilatedVcdC;
    Vtest *top = new Vtest{contextp};
    contextp->traceEverOn(true);

    top->trace(tracep, 0);
    tracep->open("wave.vcd");

    while (!contextp->gotFinish() && sim_ticks < MAX_TICKS)
    {

        if (sim_ticks == 0)
        {
            top->CLK = 0;
            top->RESET = 1;
        }
        else // clk
        {
            top->CLK = !top->CLK;
        }

        // reset
        if (sim_ticks >= RIS_EDGE(3))
        {
            top->RESET = 0;
        }

        if (sim_ticks == RIS_EDGE(3))
        {
            top->io_ins = 1048576;
        }

        if (sim_ticks == RIS_EDGE(4))
        {
            top->io_ins = 41944064;
        }

        top->eval();
        tracep->dump(sim_ticks);
        sim_ticks++;
    }

    top->final();
    tracep->close();
    delete top;

    std::cout << "Over" << std::endl;
    std::cout << sim_ticks << std::endl;
    delete contextp;

    return 0;
}

int main(int argc, char **argv)
{
    test1();
    return 0;
}