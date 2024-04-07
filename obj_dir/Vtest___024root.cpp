// Verilated -*- C++ -*-
// DESCRIPTION: Verilator output: Design implementation internals
// See Vtest.h for the primary calling header

#include "Vtest___024root.h"
#include "Vtest__Syms.h"

//==========

VL_INLINE_OPT void Vtest___024root___sequent__TOP__1(Vtest___024root* vlSelf) {
    if (false && vlSelf) {}  // Prevent unused
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vtest___024root___sequent__TOP__1\n"); );
    // Variables
    CData/*2:0*/ __Vdly__test__DOT__stateReg;
    SData/*15:0*/ __Vdly__test__DOT__numReg;
    // Body
    __Vdly__test__DOT__numReg = vlSelf->test__DOT__numReg;
    __Vdly__test__DOT__stateReg = vlSelf->test__DOT__stateReg;
    if (vlSelf->reset) {
        __Vdly__test__DOT__stateReg = 0U;
        __Vdly__test__DOT__numReg = 0U;
    } else {
        vlSelf->test__DOT__unnamedblk1__DOT___GEN = 
            ((((QData)((IData)((6U & ((IData)(vlSelf->io_mul2) 
                                      << 1U)))) << 0x20U) 
              | ((QData)((IData)(((0x70000U & ((IData)(vlSelf->io_mul2) 
                                               << 0xbU)) 
                                  | ((0x700U & ((IData)(vlSelf->io_mul2) 
                                                << 5U)) 
                                     | (7U & ((IData)(vlSelf->io_mul2) 
                                              >> 1U)))))) 
                 << 8U)) | (QData)((IData)((6U & ((IData)(vlSelf->io_mul2) 
                                                  << 1U)))));
        vlSelf->test__DOT__unnamedblk1__DOT___GEN_0 
            = (0x8d1U | (0xff8000U & (((IData)(vlSelf->test__DOT__stateReg) 
                                       << 0x15U) | 
                                      (((IData)(vlSelf->test__DOT__stateReg) 
                                        << 0x12U) | 
                                       ((IData)(vlSelf->test__DOT__stateReg) 
                                        << 0xfU)))));
        vlSelf->test__DOT__unnamedblk1__DOT__temp = 
            (0xffU & (IData)((vlSelf->test__DOT__unnamedblk1__DOT___GEN 
                              >> (0x3fU & ((IData)(vlSelf->test__DOT__stateReg) 
                                           << 3U)))));
        __Vdly__test__DOT__stateReg = ((0x17U >= (0x1fU 
                                                  & ((IData)(3U) 
                                                     * (IData)(vlSelf->test__DOT__stateReg))))
                                        ? (7U & (vlSelf->test__DOT__unnamedblk1__DOT___GEN_0 
                                                 >> 
                                                 (0x1fU 
                                                  & ((IData)(3U) 
                                                     * (IData)(vlSelf->test__DOT__stateReg)))))
                                        : 0U);
        if ((1U & (~ ((0U == (IData)(vlSelf->test__DOT__unnamedblk1__DOT__temp)) 
                      | (7U == (IData)(vlSelf->test__DOT__unnamedblk1__DOT__temp)))))) {
            if (((1U == (IData)(vlSelf->test__DOT__unnamedblk1__DOT__temp)) 
                 | (2U == (IData)(vlSelf->test__DOT__unnamedblk1__DOT__temp)))) {
                vlSelf->test__DOT__unnamedblk1__DOT__unnamedblk2__DOT___numReg_T_10 
                    = ((0x2eULL >= ((QData)((IData)(
                                                    (7U 
                                                     & ((IData)(vlSelf->test__DOT__stateReg) 
                                                        - (IData)(1U))))) 
                                    << 1U)) ? (0x7fffffffffffULL 
                                               & VL_SHIFTL_QQQ(47,47,47, (QData)((IData)(
                                                                                (0xffffU 
                                                                                & ((IData)(vlSelf->test__DOT__numReg) 
                                                                                + 
                                                                                ((0xff00U 
                                                                                & ((- (IData)(
                                                                                (1U 
                                                                                & ((IData)(vlSelf->io_mul1) 
                                                                                >> 7U)))) 
                                                                                << 8U)) 
                                                                                | (IData)(vlSelf->io_mul1)))))), 
                                                               ((QData)((IData)(
                                                                                (7U 
                                                                                & ((IData)(vlSelf->test__DOT__stateReg) 
                                                                                - (IData)(1U))))) 
                                                                << 1U)))
                        : 0ULL);
                __Vdly__test__DOT__numReg = (0xffffU 
                                             & (IData)(vlSelf->test__DOT__unnamedblk1__DOT__unnamedblk2__DOT___numReg_T_10));
            } else if ((3U == (IData)(vlSelf->test__DOT__unnamedblk1__DOT__temp))) {
                vlSelf->test__DOT__unnamedblk1__DOT__unnamedblk3__DOT___numReg_T_21 
                    = (0x7fffffffffffULL & ((QData)((IData)(
                                                            (0xffffU 
                                                             & ((IData)(vlSelf->test__DOT__numReg) 
                                                                + 
                                                                ((0xff00U 
                                                                  & ((- (IData)(
                                                                                (1U 
                                                                                & ((IData)(vlSelf->io_mul1) 
                                                                                >> 7U)))) 
                                                                     << 8U)) 
                                                                 | (IData)(vlSelf->io_mul1)))))) 
                                            << (0x1fU 
                                                & ((IData)(1U) 
                                                   + 
                                                   (0xeU 
                                                    & (((IData)(vlSelf->test__DOT__stateReg) 
                                                        - (IData)(1U)) 
                                                       << 1U))))));
                __Vdly__test__DOT__numReg = (0xffffU 
                                             & (IData)(vlSelf->test__DOT__unnamedblk1__DOT__unnamedblk3__DOT___numReg_T_21));
            } else if ((4U == (IData)(vlSelf->test__DOT__unnamedblk1__DOT__temp))) {
                vlSelf->test__DOT__unnamedblk1__DOT__unnamedblk4__DOT___numReg_T_32 
                    = (0x7fffffffffffULL & ((QData)((IData)(
                                                            (0xffffU 
                                                             & ((IData)(vlSelf->test__DOT__numReg) 
                                                                - 
                                                                ((0xff00U 
                                                                  & ((- (IData)(
                                                                                (1U 
                                                                                & ((IData)(vlSelf->io_mul1) 
                                                                                >> 7U)))) 
                                                                     << 8U)) 
                                                                 | (IData)(vlSelf->io_mul1)))))) 
                                            << (0x1fU 
                                                & ((IData)(1U) 
                                                   + 
                                                   (0xeU 
                                                    & (((IData)(vlSelf->test__DOT__stateReg) 
                                                        - (IData)(1U)) 
                                                       << 1U))))));
                __Vdly__test__DOT__numReg = (0xffffU 
                                             & (IData)(vlSelf->test__DOT__unnamedblk1__DOT__unnamedblk4__DOT___numReg_T_32));
            } else if (((5U == (IData)(vlSelf->test__DOT__unnamedblk1__DOT__temp)) 
                        | (6U == (IData)(vlSelf->test__DOT__unnamedblk1__DOT__temp)))) {
                vlSelf->test__DOT__unnamedblk1__DOT__unnamedblk5__DOT___numReg_T_41 
                    = ((0x2eULL >= ((QData)((IData)(
                                                    (7U 
                                                     & ((IData)(vlSelf->test__DOT__stateReg) 
                                                        - (IData)(1U))))) 
                                    << 1U)) ? (0x7fffffffffffULL 
                                               & VL_SHIFTL_QQQ(47,47,47, (QData)((IData)(
                                                                                (0xffffU 
                                                                                & ((IData)(vlSelf->test__DOT__numReg) 
                                                                                - 
                                                                                ((0xff00U 
                                                                                & ((- (IData)(
                                                                                (1U 
                                                                                & ((IData)(vlSelf->io_mul1) 
                                                                                >> 7U)))) 
                                                                                << 8U)) 
                                                                                | (IData)(vlSelf->io_mul1)))))), 
                                                               ((QData)((IData)(
                                                                                (7U 
                                                                                & ((IData)(vlSelf->test__DOT__stateReg) 
                                                                                - (IData)(1U))))) 
                                                                << 1U)))
                        : 0ULL);
                __Vdly__test__DOT__numReg = (0xffffU 
                                             & (IData)(vlSelf->test__DOT__unnamedblk1__DOT__unnamedblk5__DOT___numReg_T_41));
            } else if ((1U & (~ (((((0U == (IData)(vlSelf->test__DOT__stateReg)) 
                                    | (1U == (IData)(vlSelf->test__DOT__stateReg))) 
                                   | (2U == (IData)(vlSelf->test__DOT__stateReg))) 
                                  | (3U == (IData)(vlSelf->test__DOT__stateReg))) 
                                 | (4U != (IData)(vlSelf->test__DOT__stateReg)))))) {
                __Vdly__test__DOT__numReg = 0U;
            }
        }
    }
    vlSelf->test__DOT__stateReg = __Vdly__test__DOT__stateReg;
    vlSelf->test__DOT__numReg = __Vdly__test__DOT__numReg;
    if ((4U == (IData)(vlSelf->test__DOT__stateReg))) {
        vlSelf->io_res = vlSelf->test__DOT__numReg;
        vlSelf->io_out = ((IData)(vlSelf->test__DOT__numReg) 
                          & 1U);
    } else {
        vlSelf->io_res = 0U;
        vlSelf->io_out = 0U;
    }
}

void Vtest___024root___eval(Vtest___024root* vlSelf) {
    if (false && vlSelf) {}  // Prevent unused
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vtest___024root___eval\n"); );
    // Body
    if (((IData)(vlSelf->clock) & (~ (IData)(vlSelf->__Vclklast__TOP__clock)))) {
        Vtest___024root___sequent__TOP__1(vlSelf);
        vlSelf->__Vm_traceActivity[1U] = 1U;
    }
    // Final
    vlSelf->__Vclklast__TOP__clock = vlSelf->clock;
}

QData Vtest___024root___change_request_1(Vtest___024root* vlSelf);

VL_INLINE_OPT QData Vtest___024root___change_request(Vtest___024root* vlSelf) {
    if (false && vlSelf) {}  // Prevent unused
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vtest___024root___change_request\n"); );
    // Body
    return (Vtest___024root___change_request_1(vlSelf));
}

VL_INLINE_OPT QData Vtest___024root___change_request_1(Vtest___024root* vlSelf) {
    if (false && vlSelf) {}  // Prevent unused
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vtest___024root___change_request_1\n"); );
    // Body
    // Change detection
    QData __req = false;  // Logically a bool
    return __req;
}

#ifdef VL_DEBUG
void Vtest___024root___eval_debug_assertions(Vtest___024root* vlSelf) {
    if (false && vlSelf) {}  // Prevent unused
    Vtest__Syms* const __restrict vlSymsp VL_ATTR_UNUSED = vlSelf->vlSymsp;
    VL_DEBUG_IF(VL_DBG_MSGF("+    Vtest___024root___eval_debug_assertions\n"); );
    // Body
    if (VL_UNLIKELY((vlSelf->clock & 0xfeU))) {
        Verilated::overWidthError("clock");}
    if (VL_UNLIKELY((vlSelf->reset & 0xfeU))) {
        Verilated::overWidthError("reset");}
}
#endif  // VL_DEBUG
