// Verilated -*- C++ -*-
// DESCRIPTION: Verilator output: Tracing implementation internals
#include "verilated_vcd_c.h"
#include "Vtest__Syms.h"


void Vtest___024root__traceChgSub0(Vtest___024root* vlSelf, VerilatedVcd* tracep);

void Vtest___024root__traceChgTop0(void* voidSelf, VerilatedVcd* tracep) {
    Vtest___024root* const __restrict vlSelf = static_cast<Vtest___024root*>(voidSelf);
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    if (VL_UNLIKELY(!vlSymsp->__Vm_activity)) return;
    // Body
    {
        Vtest___024root__traceChgSub0((&vlSymsp->TOP), tracep);
    }
}

void Vtest___024root__traceChgSub0(Vtest___024root* vlSelf, VerilatedVcd* tracep) {
    if (false && vlSelf) {}  // Prevent unused
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    vluint32_t* const oldp = tracep->oldp(vlSymsp->__Vm_baseCode + 1);
    if (false && oldp) {}  // Prevent unused
    // Body
    {
        if (VL_UNLIKELY(vlSelf->__Vm_traceActivity[1U])) {
            tracep->chgCData(oldp+0,(vlSelf->test__DOT__stateReg),3);
            tracep->chgSData(oldp+1,(vlSelf->test__DOT__numReg),16);
            tracep->chgCData(oldp+2,(vlSelf->test__DOT__unnamedblk1__DOT__temp),8);
        }
        tracep->chgBit(oldp+3,(vlSelf->clock));
        tracep->chgBit(oldp+4,(vlSelf->reset));
        tracep->chgCData(oldp+5,(vlSelf->io_mul1),8);
        tracep->chgCData(oldp+6,(vlSelf->io_mul2),8);
        tracep->chgSData(oldp+7,(vlSelf->io_res),16);
        tracep->chgBit(oldp+8,(vlSelf->io_out));
    }
}

void Vtest___024root__traceCleanup(void* voidSelf, VerilatedVcd* /*unused*/) {
    Vtest___024root* const __restrict vlSelf = static_cast<Vtest___024root*>(voidSelf);
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    // Body
    {
        vlSymsp->__Vm_activity = false;
        vlSymsp->TOP.__Vm_traceActivity[0U] = 0U;
        vlSymsp->TOP.__Vm_traceActivity[1U] = 0U;
    }
}
