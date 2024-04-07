// Verilated -*- C++ -*-
// DESCRIPTION: Verilator output: Tracing implementation internals
#include "verilated_vcd_c.h"
#include "Vtest__Syms.h"


void Vtest___024root__traceInitSub0(Vtest___024root* vlSelf, VerilatedVcd* tracep) VL_ATTR_COLD;

void Vtest___024root__traceInitTop(Vtest___024root* vlSelf, VerilatedVcd* tracep) {
    if (false && vlSelf) {}  // Prevent unused
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    // Body
    {
        Vtest___024root__traceInitSub0(vlSelf, tracep);
    }
}

void Vtest___024root__traceInitSub0(Vtest___024root* vlSelf, VerilatedVcd* tracep) {
    if (false && vlSelf) {}  // Prevent unused
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    const int c = vlSymsp->__Vm_baseCode;
    if (false && tracep && c) {}  // Prevent unused
    // Body
    {
        tracep->declBit(c+4,"clock", false,-1);
        tracep->declBit(c+5,"reset", false,-1);
        tracep->declBus(c+6,"io_mul1", false,-1, 7,0);
        tracep->declBus(c+7,"io_mul2", false,-1, 7,0);
        tracep->declBus(c+8,"io_res", false,-1, 15,0);
        tracep->declBit(c+9,"io_out", false,-1);
        tracep->declBit(c+4,"test clock", false,-1);
        tracep->declBit(c+5,"test reset", false,-1);
        tracep->declBus(c+6,"test io_mul1", false,-1, 7,0);
        tracep->declBus(c+7,"test io_mul2", false,-1, 7,0);
        tracep->declBus(c+8,"test io_res", false,-1, 15,0);
        tracep->declBit(c+9,"test io_out", false,-1);
        tracep->declBus(c+1,"test stateReg", false,-1, 2,0);
        tracep->declBus(c+2,"test numReg", false,-1, 15,0);
        tracep->declBus(c+3,"test unnamedblk1 temp", false,-1, 7,0);
    }
}

void Vtest___024root__traceFullTop0(void* voidSelf, VerilatedVcd* tracep) VL_ATTR_COLD;
void Vtest___024root__traceChgTop0(void* voidSelf, VerilatedVcd* tracep);
void Vtest___024root__traceCleanup(void* voidSelf, VerilatedVcd* /*unused*/);

void Vtest___024root__traceRegister(Vtest___024root* vlSelf, VerilatedVcd* tracep) {
    if (false && vlSelf) {}  // Prevent unused
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    // Body
    {
        tracep->addFullCb(&Vtest___024root__traceFullTop0, vlSelf);
        tracep->addChgCb(&Vtest___024root__traceChgTop0, vlSelf);
        tracep->addCleanupCb(&Vtest___024root__traceCleanup, vlSelf);
    }
}

void Vtest___024root__traceFullSub0(Vtest___024root* vlSelf, VerilatedVcd* tracep) VL_ATTR_COLD;

void Vtest___024root__traceFullTop0(void* voidSelf, VerilatedVcd* tracep) {
    Vtest___024root* const __restrict vlSelf = static_cast<Vtest___024root*>(voidSelf);
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    // Body
    {
        Vtest___024root__traceFullSub0((&vlSymsp->TOP), tracep);
    }
}

void Vtest___024root__traceFullSub0(Vtest___024root* vlSelf, VerilatedVcd* tracep) {
    if (false && vlSelf) {}  // Prevent unused
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    vluint32_t* const oldp = tracep->oldp(vlSymsp->__Vm_baseCode);
    if (false && oldp) {}  // Prevent unused
    // Body
    {
        tracep->fullCData(oldp+1,(vlSelf->test__DOT__stateReg),3);
        tracep->fullSData(oldp+2,(vlSelf->test__DOT__numReg),16);
        tracep->fullCData(oldp+3,(vlSelf->test__DOT__unnamedblk1__DOT__temp),8);
        tracep->fullBit(oldp+4,(vlSelf->clock));
        tracep->fullBit(oldp+5,(vlSelf->reset));
        tracep->fullCData(oldp+6,(vlSelf->io_mul1),8);
        tracep->fullCData(oldp+7,(vlSelf->io_mul2),8);
        tracep->fullSData(oldp+8,(vlSelf->io_res),16);
        tracep->fullBit(oldp+9,(vlSelf->io_out));
    }
}
