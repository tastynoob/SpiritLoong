// Verilated -*- C++ -*-
// DESCRIPTION: Verilator output: Design implementation internals
// See Vtest.h for the primary calling header

#include "Vtest___024root.h"
#include "Vtest__Syms.h"

//==========


void Vtest___024root___ctor_var_reset(Vtest___024root* vlSelf);

Vtest___024root::Vtest___024root(const char* _vcname__)
    : VerilatedModule(_vcname__)
 {
    // Reset structure values
    Vtest___024root___ctor_var_reset(this);
}

void Vtest___024root::__Vconfigure(Vtest__Syms* _vlSymsp, bool first) {
    if (false && first) {}  // Prevent unused
    this->vlSymsp = _vlSymsp;
}

Vtest___024root::~Vtest___024root() {
}

void Vtest___024root___settle__TOP__2(Vtest___024root* vlSelf) {
    if (false && vlSelf) {}  // Prevent unused
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vtest___024root___settle__TOP__2\n"); );
    // Body
    if ((4U == (IData)(vlSelf->test__DOT__stateReg))) {
        vlSelf->io_res = vlSelf->test__DOT__numReg;
        vlSelf->io_out = ((IData)(vlSelf->test__DOT__numReg) 
                          & 1U);
    } else {
        vlSelf->io_res = 0U;
        vlSelf->io_out = 0U;
    }
}

void Vtest___024root___eval_initial(Vtest___024root* vlSelf) {
    if (false && vlSelf) {}  // Prevent unused
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vtest___024root___eval_initial\n"); );
    // Body
    vlSelf->__Vclklast__TOP__clock = vlSelf->clock;
}

void Vtest___024root___eval_settle(Vtest___024root* vlSelf) {
    if (false && vlSelf) {}  // Prevent unused
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vtest___024root___eval_settle\n"); );
    // Body
    Vtest___024root___settle__TOP__2(vlSelf);
}

void Vtest___024root___final(Vtest___024root* vlSelf) {
    if (false && vlSelf) {}  // Prevent unused
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vtest___024root___final\n"); );
}

void Vtest___024root___ctor_var_reset(Vtest___024root* vlSelf) {
    if (false && vlSelf) {}  // Prevent unused
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vtest___024root___ctor_var_reset\n"); );
    // Body
    vlSelf->clock = VL_RAND_RESET_I(1);
    vlSelf->reset = VL_RAND_RESET_I(1);
    vlSelf->io_mul1 = VL_RAND_RESET_I(8);
    vlSelf->io_mul2 = VL_RAND_RESET_I(8);
    vlSelf->io_res = VL_RAND_RESET_I(16);
    vlSelf->io_out = VL_RAND_RESET_I(1);
    vlSelf->test__DOT__stateReg = VL_RAND_RESET_I(3);
    vlSelf->test__DOT__numReg = VL_RAND_RESET_I(16);
    vlSelf->test__DOT__unnamedblk1__DOT___GEN = VL_RAND_RESET_Q(64);
    vlSelf->test__DOT__unnamedblk1__DOT__temp = VL_RAND_RESET_I(8);
    vlSelf->test__DOT__unnamedblk1__DOT___GEN_0 = VL_RAND_RESET_I(24);
    vlSelf->test__DOT__unnamedblk1__DOT__unnamedblk2__DOT___numReg_T_10 = VL_RAND_RESET_Q(47);
    vlSelf->test__DOT__unnamedblk1__DOT__unnamedblk3__DOT___numReg_T_21 = VL_RAND_RESET_Q(47);
    vlSelf->test__DOT__unnamedblk1__DOT__unnamedblk4__DOT___numReg_T_32 = VL_RAND_RESET_Q(47);
    vlSelf->test__DOT__unnamedblk1__DOT__unnamedblk5__DOT___numReg_T_41 = VL_RAND_RESET_Q(47);
    for (int __Vi0=0; __Vi0<2; ++__Vi0) {
        vlSelf->__Vm_traceActivity[__Vi0] = VL_RAND_RESET_I(1);
    }
}
