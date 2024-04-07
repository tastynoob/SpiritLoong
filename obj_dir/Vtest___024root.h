// Verilated -*- C++ -*-
// DESCRIPTION: Verilator output: Design internal header
// See Vtest.h for the primary calling header

#ifndef VERILATED_VTEST___024ROOT_H_
#define VERILATED_VTEST___024ROOT_H_  // guard

#include "verilated_heavy.h"

//==========

class Vtest__Syms;
class Vtest_VerilatedVcd;


//----------

VL_MODULE(Vtest___024root) {
  public:

    // PORTS
    VL_IN8(clock,0,0);
    VL_IN8(reset,0,0);
    VL_IN8(io_mul1,7,0);
    VL_IN8(io_mul2,7,0);
    VL_OUT8(io_out,0,0);
    VL_OUT16(io_res,15,0);

    // LOCAL SIGNALS
    CData/*2:0*/ test__DOT__stateReg;
    QData/*63:0*/ test__DOT__unnamedblk1__DOT___GEN;
    CData/*7:0*/ test__DOT__unnamedblk1__DOT__temp;
    IData/*23:0*/ test__DOT__unnamedblk1__DOT___GEN_0;
    SData/*15:0*/ test__DOT__numReg;
    QData/*46:0*/ test__DOT__unnamedblk1__DOT__unnamedblk2__DOT___numReg_T_10;
    QData/*46:0*/ test__DOT__unnamedblk1__DOT__unnamedblk3__DOT___numReg_T_21;
    QData/*46:0*/ test__DOT__unnamedblk1__DOT__unnamedblk4__DOT___numReg_T_32;
    QData/*46:0*/ test__DOT__unnamedblk1__DOT__unnamedblk5__DOT___numReg_T_41;

    // LOCAL VARIABLES
    CData/*0:0*/ __Vclklast__TOP__clock;
    VlUnpacked<CData/*0:0*/, 2> __Vm_traceActivity;

    // INTERNAL VARIABLES
    Vtest__Syms* vlSymsp;  // Symbol table

    // CONSTRUCTORS
  private:
    VL_UNCOPYABLE(Vtest___024root);  ///< Copying not allowed
  public:
    Vtest___024root(const char* name);
    ~Vtest___024root();

    // INTERNAL METHODS
    void __Vconfigure(Vtest__Syms* symsp, bool first);
} VL_ATTR_ALIGNED(VL_CACHE_LINE_BYTES);

//----------


#endif  // guard
