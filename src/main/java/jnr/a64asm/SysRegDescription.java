/*
 * Copyright (C) 2018 ossdev07 ossdev@puresoftware.com
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package jnr.a64asm;

import java.util.EnumMap;
import java.util.Map;
import static jnr.a64asm.SYSREG_CODE.*;

public final class SysRegDescription {
    final SYSREG_CODE code;
    final int reg_code;

    private static final Map<SYSREG_CODE, SysRegDescription> sysRegtable
            = new EnumMap<SYSREG_CODE, SysRegDescription>(SYSREG_CODE.class);

    SysRegDescription(SYSREG_CODE code, int op0, int op1, int crn, int crm, int op2) {
        this.code = code;
        this.reg_code =((((op0) << 19) | ((op1) << 16) | ((crn) << 12) | ((crm) << 8) | ((op2) << 5)) >> 5) ;

    }

    private static final SysRegDescription MAKE_SYSREGCODE(SYSREG_CODE code, int op0, int op1, int crn, int crm, int op2) {
        SysRegDescription id = new SysRegDescription(code, op0, op1, crn, crm, op2);
        sysRegtable.put(code, id);
        return id;
    }

    /* for 3.9.3 Instructions for Accessing Special Purpose Registers */
    private static final SysRegDescription MAKE_SYSREGCODE(SYSREG_CODE code, int op1, int crm, int op2) {
        SysRegDescription id = new SysRegDescription(code,3,(op1),4,(crm),(op2));
        sysRegtable.put(code, id);
        return id;
    }

    public static final SysRegDescription find(SYSREG_CODE code) {
        SysRegDescription id = sysRegtable.get(code);
        if (id == null) {
            throw new IllegalArgumentException("no description for " + code);
        }
        return id;
    }

    private static final SysRegDescription[] SYSREG_tb = {
        // Instruction code (enum)      | instruction name   | group           | operator 1 flags| operator 2 flags| r| opCode1   | opcode2
        MAKE_SYSREGCODE(SPSR_EL1,        0,0,0),
        MAKE_SYSREGCODE(ELR_EL1,         0,0,1),
        MAKE_SYSREGCODE(SP_EL0,          0,1,0),
        MAKE_SYSREGCODE(SPSEL,           0,2,0),
        MAKE_SYSREGCODE(DAIF,            3,2,1),
        MAKE_SYSREGCODE(CURRENTEL,       0,2,2),
        MAKE_SYSREGCODE(NZCV,            3,2,0),
        MAKE_SYSREGCODE(FPCR,            3,4,0),
        MAKE_SYSREGCODE(FPSR,            3,4,1),
        MAKE_SYSREGCODE(DSPSR_EL0,       3,5,0),
        MAKE_SYSREGCODE(DLR_EL0,         3,5,1),
        MAKE_SYSREGCODE(SPSR_EL2,        4,0,0),
        MAKE_SYSREGCODE(ELR_EL2,         4,0,1),
        MAKE_SYSREGCODE(SP_EL1,          4,1,0),
        MAKE_SYSREGCODE(SPSR_IRQ,        4,3,0),
        MAKE_SYSREGCODE(SPSR_ABT,        4,3,1),
        MAKE_SYSREGCODE(SPSR_UND,        4,3,2),
        MAKE_SYSREGCODE(SPSR_FIQ,        4,3,3),
        MAKE_SYSREGCODE(SPSR_EL3,        6,0,0),
        MAKE_SYSREGCODE(ELR_EL3,         6,0,1),
        MAKE_SYSREGCODE(SP_EL2,          6,1,0),
        MAKE_SYSREGCODE(SPSR_SVC,        0,0,0),
        MAKE_SYSREGCODE(SPSR_HYP,        4,0,0),
        MAKE_SYSREGCODE(MIDR_EL1,        3,0,0,0,0),
        MAKE_SYSREGCODE(CTR_EL0,         3,3,0,0,1),
        MAKE_SYSREGCODE(MPIDR_EL1,       3,0,0,0,5),
        MAKE_SYSREGCODE(REVIDR_EL1,      3,0,0,0,6),
        MAKE_SYSREGCODE(AIDR_EL1,        3,1,0,0,7),
        MAKE_SYSREGCODE(DCZID_EL0,       3,3,0,0,7),
        MAKE_SYSREGCODE(ID_DFR0_EL1,     3,0,0,1,2),
        MAKE_SYSREGCODE(ID_PFR0_EL1,     3,0,0,1,0),
        MAKE_SYSREGCODE(ID_PFR1_EL1,     3,0,0,1,1),
        MAKE_SYSREGCODE(ID_AFR0_EL1,     3,0,0,1,3),
        MAKE_SYSREGCODE(ID_MMFR0_EL1,    3,0,0,1,4),
        MAKE_SYSREGCODE(ID_MMFR1_EL1,    3,0,0,1,5),
        MAKE_SYSREGCODE(ID_MMFR2_EL1,    3,0,0,1,6),
        MAKE_SYSREGCODE(ID_MMFR3_EL1,    3,0,0,1,7),
        MAKE_SYSREGCODE(ID_ISAR0_EL1,    3,0,0,2,0),
        MAKE_SYSREGCODE(ID_ISAR1_EL1,    3,0,0,2,1),
        MAKE_SYSREGCODE(ID_ISAR2_EL1,    3,0,0,2,2),
        MAKE_SYSREGCODE(ID_ISAR3_EL1,    3,0,0,2,3),
        MAKE_SYSREGCODE(ID_ISAR4_EL1,    3,0,0,2,4),
        MAKE_SYSREGCODE(ID_ISAR5_EL1,    3,0,0,2,5),
        MAKE_SYSREGCODE(MVFR0_EL1,       3,0,0,3,0),
        MAKE_SYSREGCODE(MVFR1_EL1,       3,0,0,3,1),
        MAKE_SYSREGCODE(MVFR2_EL1,       3,0,0,3,2),
        MAKE_SYSREGCODE(CCSIDR_EL1,      3,1,0,0,0),
        MAKE_SYSREGCODE(ID_AA64PFR0_EL1, 3,0,0,4,0),
        MAKE_SYSREGCODE(ID_AA64PFR1_EL1, 3,0,0,4,1),
        MAKE_SYSREGCODE(ID_AA64DFR0_EL1, 3,0,0,5,0),
        MAKE_SYSREGCODE(ID_AA64DFR1_EL1, 3,0,0,5,1),
        MAKE_SYSREGCODE(ID_AA64ISAR0_EL1,3,0,0,6,0),
        MAKE_SYSREGCODE(ID_AA64ISAR1_EL1,3,0,0,6,1),
        MAKE_SYSREGCODE(ID_AA64MMFR0_EL1,3,0,0,7,0),
        MAKE_SYSREGCODE(ID_AA64MMFR1_EL1,3,0,0,7,1),
        MAKE_SYSREGCODE(ID_AA64AFR0_EL1, 3,0,0,5,4),
        MAKE_SYSREGCODE(ID_AA64AFR1_EL1, 3,0,0,5,5),
        MAKE_SYSREGCODE(CLIDR_EL1,       3,1,0,0,1),
        MAKE_SYSREGCODE(CSSELR_EL1,      3,2,0,0,0),
        MAKE_SYSREGCODE(VPIDR_EL2,       3,4,0,0,0),
        MAKE_SYSREGCODE(VMPIDR_EL2,      3,4,0,0,5),
        MAKE_SYSREGCODE(SCTLR_EL1,       3,0,1,0,0),
        MAKE_SYSREGCODE(SCTLR_EL2,       3,4,1,0,0),
        MAKE_SYSREGCODE(SCTLR_EL3,       3,6,1,0,0),
        MAKE_SYSREGCODE(ACTLR_EL1,       3,0,1,0,1),
        MAKE_SYSREGCODE(ACTLR_EL2,       3,4,1,0,1),
        MAKE_SYSREGCODE(ACTLR_EL3,       3,6,1,0,1),
        MAKE_SYSREGCODE(CPACR_EL1,       3,0,1,0,2),
        MAKE_SYSREGCODE(CPTR_EL2,        3,4,1,1,2),
        MAKE_SYSREGCODE(CPTR_EL3,        3,6,1,1,2),
        MAKE_SYSREGCODE(SCR_EL3,         3,6,1,1,0),
        MAKE_SYSREGCODE(HCR_EL2,         3,4,1,1,0),
        MAKE_SYSREGCODE(MDCR_EL2,        3,4,1,1,1),
        MAKE_SYSREGCODE(MDCR_EL3,        3,6,1,3,1),
        MAKE_SYSREGCODE(HSTR_EL2,        3,4,1,1,3),
        MAKE_SYSREGCODE(HACR_EL2,        3,4,1,1,7),
        MAKE_SYSREGCODE(TTBR0_EL1,       3,0,2,0,0),
        MAKE_SYSREGCODE(TTBR1_EL1,       3,0,2,0,1),
        MAKE_SYSREGCODE(TTBR0_EL2,       3,4,2,0,0),
        MAKE_SYSREGCODE(TTBR0_EL3,       3,6,2,0,0),
        MAKE_SYSREGCODE(VTTBR_EL2,       3,4,2,1,0),
        MAKE_SYSREGCODE(TCR_EL1,         3,0,2,0,2),
        MAKE_SYSREGCODE(TCR_EL2,         3,4,2,0,2),
        MAKE_SYSREGCODE(TCR_EL3,         3,6,2,0,2),
        MAKE_SYSREGCODE(VTCR_EL2,        3,4,2,1,2),
        MAKE_SYSREGCODE(AFSR0_EL1,       3,0,5,1,0),
        MAKE_SYSREGCODE(AFSR1_EL1,       3,0,5,1,1),
        MAKE_SYSREGCODE(AFSR0_EL2,       3,4,5,1,0),
        MAKE_SYSREGCODE(AFSR1_EL2,       3,4,5,1,1),
        MAKE_SYSREGCODE(AFSR0_EL3,       3,6,5,1,0),
        MAKE_SYSREGCODE(AFSR1_EL3,       3,6,5,1,1),
        MAKE_SYSREGCODE(ESR_EL1,         3,0,5,2,0),
        MAKE_SYSREGCODE(ESR_EL2,         3,4,5,2,0),
        MAKE_SYSREGCODE(ESR_EL3,         3,6,5,2,0),
        MAKE_SYSREGCODE(FPEXC32_EL2,     3,4,5,3,0),
        MAKE_SYSREGCODE(FAR_EL1,         3,0,6,0,0),
        MAKE_SYSREGCODE(FAR_EL2,         3,4,6,0,0),
        MAKE_SYSREGCODE(FAR_EL3,         3,6,6,0,0),
        MAKE_SYSREGCODE(HPFAR_EL2,       3,4,6,0,4),
        MAKE_SYSREGCODE(PAR_EL1,         3,0,7,4,0),
        MAKE_SYSREGCODE(MAIR_EL1,        3,0,10,2,0),
        MAKE_SYSREGCODE(MAIR_EL2,        3,4,10,2,0),
        MAKE_SYSREGCODE(MAIR_EL3,        3,6,10,2,0),
        MAKE_SYSREGCODE(AMAIR_EL1,       3,0,10,3,0),
        MAKE_SYSREGCODE(AMAIR_EL2,       3,4,10,3,0),
        MAKE_SYSREGCODE(AMAIR_EL3,       3,6,10,3,0),
        MAKE_SYSREGCODE(VBAR_EL1,        3,0,12,0,0),
        MAKE_SYSREGCODE(VBAR_EL2,        3,4,12,0,0),
        MAKE_SYSREGCODE(VBAR_EL3,        3,6,12,0,0),
        MAKE_SYSREGCODE(RVBAR_EL1,       3,0,12,0,1),
        MAKE_SYSREGCODE(RVBAR_EL2,       3,4,12,0,1),
        MAKE_SYSREGCODE(RVBAR_EL3,       3,6,12,0,1),
        MAKE_SYSREGCODE(RMR_EL1,         3,0,12,0,2),
        MAKE_SYSREGCODE(RMR_EL2,         3,4,12,0,2),
        MAKE_SYSREGCODE(RMR_EL3,         3,6,12,0,2),
        MAKE_SYSREGCODE(ISR_EL1,         3,0,12,1,0),
        MAKE_SYSREGCODE(CONTEXTIDR_EL1,  3,0,13,0,1),
        MAKE_SYSREGCODE(TPIDR_EL0,       3,3,13,0,2),
        MAKE_SYSREGCODE(TPIDRRO_EL0,     3,3,13,0,3),
        MAKE_SYSREGCODE(TPIDR_EL1,       3,0,13,0,4),
        MAKE_SYSREGCODE(TPIDR_EL2,       3,4,13,0,2),
        MAKE_SYSREGCODE(TPIDR_EL3,       3,6,13,0,2),
        MAKE_SYSREGCODE(TEECR32_EL1,     2,2,0,0,0),
        MAKE_SYSREGCODE(CNTFRQ_EL0,      3,3,14,0,0),
        MAKE_SYSREGCODE(CNTPCT_EL0,      3,3,14,0,1),
        MAKE_SYSREGCODE(CNTVCT_EL0,      3,3,14,0,2),
        MAKE_SYSREGCODE(CNTVOFF_EL2,     3,4,14,0,3),
        MAKE_SYSREGCODE(CNTKCTL_EL1,     3,0,14,1,0),
        MAKE_SYSREGCODE(CNTHCTL_EL2,     3,4,14,1,0),
        MAKE_SYSREGCODE(CNTP_TVAL_EL0,   3,3,14,2,0),
        MAKE_SYSREGCODE(CNTP_CTL_EL0,    3,3,14,2,1),
        MAKE_SYSREGCODE(CNTP_CVAL_EL0,   3,3,14,2,2),
        MAKE_SYSREGCODE(CNTV_TVAL_EL0,   3,3,14,3,0),
        MAKE_SYSREGCODE(CNTV_CTL_EL0,    3,3,14,3,1),
        MAKE_SYSREGCODE(CNTV_CVAL_EL0,   3,3,14,3,2),
        MAKE_SYSREGCODE(CNTHP_TVAL_EL2,  3,4,14,2,0),
        MAKE_SYSREGCODE(CNTHP_CTL_EL2,   3,4,14,2,1),
        MAKE_SYSREGCODE(CNTHP_CVAL_EL2,  3,4,14,2,2),
        MAKE_SYSREGCODE(CNTPS_TVAL_EL1,  3,7,14,2,0),
        MAKE_SYSREGCODE(CNTPS_CTL_EL1,   3,7,14,2,1),
        MAKE_SYSREGCODE(CNTPS_CVAL_EL1,  3,7,14,2,2),
        MAKE_SYSREGCODE(DACR32_EL2,      3,4,3,0,0),
        MAKE_SYSREGCODE(IFSR32_EL2,      3,4,5,0,1),
        MAKE_SYSREGCODE(TEEHBR32_EL1,    2,2,1,0,0),
        MAKE_SYSREGCODE(SDER32_EL3,      3,6,1,1,1),
        MAKE_SYSREGCODE(MDSCR_EL1,       2,0,0,2, 2),
        MAKE_SYSREGCODE(MDCCSR_EL0,      2,3,0,1, 0),
        MAKE_SYSREGCODE(MDCCINT_EL1,     2,0,0,2, 0),
        MAKE_SYSREGCODE(DBGDTR_EL0,      2,3,0,4, 0),
        MAKE_SYSREGCODE(DBGDTRRX_EL0,    2,3,0,5, 0),
        MAKE_SYSREGCODE(DBGDTRTX_EL0,    2,3,0,5, 0),
        MAKE_SYSREGCODE(OSDTRRX_EL1,     2,0,0,0, 2),
        MAKE_SYSREGCODE(OSDTRTX_EL1,     2,0,0,3, 2),
        MAKE_SYSREGCODE(OSECCR_EL1,      2,0,0,6, 2),
        MAKE_SYSREGCODE(DBGVCR32_EL2,    2,4,0,7, 0),
        MAKE_SYSREGCODE(DBGBVR0_EL1,     2,0,0,0, 4),
        MAKE_SYSREGCODE(DBGBVR1_EL1,     2,0,0,1, 4),
        MAKE_SYSREGCODE(DBGBVR2_EL1,     2,0,0,2, 4),
        MAKE_SYSREGCODE(DBGBVR3_EL1,     2,0,0,3, 4),
        MAKE_SYSREGCODE(DBGBVR4_EL1,     2,0,0,4, 4),
        MAKE_SYSREGCODE(DBGBVR5_EL1,     2,0,0,5, 4),
        MAKE_SYSREGCODE(DBGBVR6_EL1,     2,0,0,6, 4),
        MAKE_SYSREGCODE(DBGBVR7_EL1,     2,0,0,7, 4),
        MAKE_SYSREGCODE(DBGBVR8_EL1,     2,0,0,8, 4),
        MAKE_SYSREGCODE(DBGBVR9_EL1,     2,0,0,9, 4),
        MAKE_SYSREGCODE(DBGBVR10_EL1,    2,0,0,10,4),
        MAKE_SYSREGCODE(DBGBVR11_EL1,    2,0,0,11,4),
        MAKE_SYSREGCODE(DBGBVR12_EL1,    2,0,0,12,4),
        MAKE_SYSREGCODE(DBGBVR13_EL1,    2,0,0,13,4),
        MAKE_SYSREGCODE(DBGBVR14_EL1,    2,0,0,14,4),
        MAKE_SYSREGCODE(DBGBVR15_EL1,    2,0,0,15,4),
        MAKE_SYSREGCODE(DBGBCR0_EL1,     2,0,0,0, 5),
        MAKE_SYSREGCODE(DBGBCR1_EL1,     2,0,0,1, 5),
        MAKE_SYSREGCODE(DBGBCR2_EL1,     2,0,0,2, 5),
        MAKE_SYSREGCODE(DBGBCR3_EL1,     2,0,0,3, 5),
        MAKE_SYSREGCODE(DBGBCR4_EL1,     2,0,0,4, 5),
        MAKE_SYSREGCODE(DBGBCR5_EL1,     2,0,0,5, 5),
        MAKE_SYSREGCODE(DBGBCR6_EL1,     2,0,0,6, 5),
        MAKE_SYSREGCODE(DBGBCR7_EL1,     2,0,0,7, 5),
        MAKE_SYSREGCODE(DBGBCR8_EL1,     2,0,0,8, 5),
        MAKE_SYSREGCODE(DBGBCR9_EL1,     2,0,0,9, 5),
        MAKE_SYSREGCODE(DBGBCR10_EL1,    2,0,0,10,5),
        MAKE_SYSREGCODE(DBGBCR11_EL1,    2,0,0,11,5),
        MAKE_SYSREGCODE(DBGBCR12_EL1,    2,0,0,12,5),
        MAKE_SYSREGCODE(DBGBCR13_EL1,    2,0,0,13,5),
        MAKE_SYSREGCODE(DBGBCR14_EL1,    2,0,0,14,5),
        MAKE_SYSREGCODE(DBGBCR15_EL1,    2,0,0,15,5),
        MAKE_SYSREGCODE(DBGWVR0_EL1,     2,0,0,0, 6),
        MAKE_SYSREGCODE(DBGWVR1_EL1,     2,0,0,1, 6),
        MAKE_SYSREGCODE(DBGWVR2_EL1,     2,0,0,2, 6),
        MAKE_SYSREGCODE(DBGWVR3_EL1,     2,0,0,3, 6),
        MAKE_SYSREGCODE(DBGWVR4_EL1,     2,0,0,4, 6),
        MAKE_SYSREGCODE(DBGWVR5_EL1,     2,0,0,5, 6),
        MAKE_SYSREGCODE(DBGWVR6_EL1,     2,0,0,6, 6),
        MAKE_SYSREGCODE(DBGWVR7_EL1,     2,0,0,7, 6),
        MAKE_SYSREGCODE(DBGWVR8_EL1,     2,0,0,8, 6),
        MAKE_SYSREGCODE(DBGWVR9_EL1,     2,0,0,9, 6),
        MAKE_SYSREGCODE(DBGWVR10_EL1,    2,0,0,10,6),
        MAKE_SYSREGCODE(DBGWVR11_EL1,    2,0,0,11,6),
        MAKE_SYSREGCODE(DBGWVR12_EL1,    2,0,0,12,6),
        MAKE_SYSREGCODE(DBGWVR13_EL1,    2,0,0,13,6),
        MAKE_SYSREGCODE(DBGWVR14_EL1,    2,0,0,14,6),
        MAKE_SYSREGCODE(DBGWVR15_EL1,    2,0,0,15,6),
        MAKE_SYSREGCODE(DBGWCR0_EL1,     2,0,0,0, 7),
        MAKE_SYSREGCODE(DBGWCR1_EL1,     2,0,0,1, 7),
        MAKE_SYSREGCODE(DBGWCR2_EL1,     2,0,0,2, 7),
        MAKE_SYSREGCODE(DBGWCR3_EL1,     2,0,0,3, 7),
        MAKE_SYSREGCODE(DBGWCR4_EL1,     2,0,0,4, 7),
        MAKE_SYSREGCODE(DBGWCR5_EL1,     2,0,0,5, 7),
        MAKE_SYSREGCODE(DBGWCR6_EL1,     2,0,0,6, 7),
        MAKE_SYSREGCODE(DBGWCR7_EL1,     2,0,0,7, 7),
        MAKE_SYSREGCODE(DBGWCR8_EL1,     2,0,0,8, 7),
        MAKE_SYSREGCODE(DBGWCR9_EL1,     2,0,0,9, 7),
        MAKE_SYSREGCODE(DBGWCR10_EL1,    2,0,0,10,7),
        MAKE_SYSREGCODE(DBGWCR11_EL1,    2,0,0,11,7),
        MAKE_SYSREGCODE(DBGWCR12_EL1,    2,0,0,12,7),
        MAKE_SYSREGCODE(DBGWCR13_EL1,    2,0,0,13,7),
        MAKE_SYSREGCODE(DBGWCR14_EL1,    2,0,0,14,7),
        MAKE_SYSREGCODE(DBGWCR15_EL1,    2,0,0,15,7),
        MAKE_SYSREGCODE(MDRAR_EL1,       2,0,1,0, 0),
        MAKE_SYSREGCODE(OSLAR_EL1,       2,0,1,0, 4),
        MAKE_SYSREGCODE(OSLSR_EL1,       2,0,1,1, 4),
        MAKE_SYSREGCODE(OSDLR_EL1,       2,0,1,3, 4),
        MAKE_SYSREGCODE(DBGPRCR_EL1,     2,0,1,4, 4),
        MAKE_SYSREGCODE(DBGCLAIMSET_EL1, 2,0,7,8, 6),
        MAKE_SYSREGCODE(DBGCLAIMCLR_EL1, 2,0,7,9, 6),
        MAKE_SYSREGCODE(DBGAUTHSTATUS_EL1,2,0,7,14,6),
        MAKE_SYSREGCODE(PMCR_EL0,        3,3,9,12, 0),
        MAKE_SYSREGCODE(PMCNTENSET_EL0,  3,3,9,12, 1),
        MAKE_SYSREGCODE(PMCNTENCLR_EL0,  3,3,9,12, 2),
        MAKE_SYSREGCODE(PMOVSCLR_EL0,    3,3,9,12, 3),
        MAKE_SYSREGCODE(PMSWINC_EL0,     3,3,9,12, 4),
        MAKE_SYSREGCODE(PMSELR_EL0,      3,3,9,12, 5),
        MAKE_SYSREGCODE(PMCEID0_EL0,     3,3,9,12, 6),
        MAKE_SYSREGCODE(PMCEID1_EL0,     3,3,9,12, 7),
        MAKE_SYSREGCODE(PMCCNTR_EL0,     3,3,9,13, 0),
        MAKE_SYSREGCODE(PMXEVTYPER_EL0,  3,3,9,13, 1),
        MAKE_SYSREGCODE(PMXEVCNTR_EL0,   3,3,9,13, 2),
        MAKE_SYSREGCODE(PMUSERENR_EL0,   3,3,9,14, 0),
        MAKE_SYSREGCODE(PMINTENSET_EL1,  3,0,9,14, 1),
        MAKE_SYSREGCODE(PMINTENCLR_EL1,  3,0,9,14, 2),
        MAKE_SYSREGCODE(PMOVSSET_EL0,    3,3,9,14, 3),
        MAKE_SYSREGCODE(PMEVCNTR0_EL0,   3,3,14,8, 0),
        MAKE_SYSREGCODE(PMEVCNTR1_EL0,   3,3,14,8, 1),
        MAKE_SYSREGCODE(PMEVCNTR2_EL0,   3,3,14,8, 2),
        MAKE_SYSREGCODE(PMEVCNTR3_EL0,   3,3,14,8, 3),
        MAKE_SYSREGCODE(PMEVCNTR4_EL0,   3,3,14,8, 4),
        MAKE_SYSREGCODE(PMEVCNTR5_EL0,   3,3,14,8, 5),
        MAKE_SYSREGCODE(PMEVCNTR6_EL0,   3,3,14,8, 6),
        MAKE_SYSREGCODE(PMEVCNTR7_EL0,   3,3,14,8, 7),
        MAKE_SYSREGCODE(PMEVCNTR8_EL0,   3,3,14,9, 0),
        MAKE_SYSREGCODE(PMEVCNTR9_EL0,   3,3,14,9, 1),
        MAKE_SYSREGCODE(PMEVCNTR10_EL0,  3,3,14,9, 2),
        MAKE_SYSREGCODE(PMEVCNTR11_EL0,  3,3,14,9, 3),
        MAKE_SYSREGCODE(PMEVCNTR12_EL0,  3,3,14,9, 4),
        MAKE_SYSREGCODE(PMEVCNTR13_EL0,  3,3,14,9, 5),
        MAKE_SYSREGCODE(PMEVCNTR14_EL0,  3,3,14,9, 6),
        MAKE_SYSREGCODE(PMEVCNTR15_EL0,  3,3,14,9, 7),
        MAKE_SYSREGCODE(PMEVCNTR16_EL0,  3,3,14,10,0),
        MAKE_SYSREGCODE(PMEVCNTR17_EL0,  3,3,14,10,1),
        MAKE_SYSREGCODE(PMEVCNTR18_EL0,  3,3,14,10,2),
        MAKE_SYSREGCODE(PMEVCNTR19_EL0,  3,3,14,10,3),
        MAKE_SYSREGCODE(PMEVCNTR20_EL0,  3,3,14,10,4),
        MAKE_SYSREGCODE(PMEVCNTR21_EL0,  3,3,14,10,5),
        MAKE_SYSREGCODE(PMEVCNTR22_EL0,  3,3,14,10,6),
        MAKE_SYSREGCODE(PMEVCNTR23_EL0,  3,3,14,10,7),
        MAKE_SYSREGCODE(PMEVCNTR24_EL0,  3,3,14,11,0),
        MAKE_SYSREGCODE(PMEVCNTR25_EL0,  3,3,14,11,1),
        MAKE_SYSREGCODE(PMEVCNTR26_EL0,  3,3,14,11,2),
        MAKE_SYSREGCODE(PMEVCNTR27_EL0,  3,3,14,11,3),
        MAKE_SYSREGCODE(PMEVCNTR28_EL0,  3,3,14,11,4),
        MAKE_SYSREGCODE(PMEVCNTR29_EL0,  3,3,14,11,5),
        MAKE_SYSREGCODE(PMEVCNTR30_EL0,  3,3,14,11,6),
        MAKE_SYSREGCODE(PMEVTYPER0_EL0,  3,3,14,12,0),
        MAKE_SYSREGCODE(PMEVTYPER1_EL0,  3,3,14,12,1),
        MAKE_SYSREGCODE(PMEVTYPER2_EL0,  3,3,14,12,2),
        MAKE_SYSREGCODE(PMEVTYPER3_EL0,  3,3,14,12,3),
        MAKE_SYSREGCODE(PMEVTYPER4_EL0,  3,3,14,12,4),
        MAKE_SYSREGCODE(PMEVTYPER5_EL0,  3,3,14,12,5),
        MAKE_SYSREGCODE(PMEVTYPER6_EL0,  3,3,14,12,6),
        MAKE_SYSREGCODE(PMEVTYPER7_EL0,  3,3,14,12,7),
        MAKE_SYSREGCODE(PMEVTYPER8_EL0,  3,3,14,13,0),
        MAKE_SYSREGCODE(PMEVTYPER9_EL0,  3,3,14,13,1),
        MAKE_SYSREGCODE(PMEVTYPER10_EL0, 3,3,14,13,2),
        MAKE_SYSREGCODE(PMEVTYPER11_EL0, 3,3,14,13,3),
        MAKE_SYSREGCODE(PMEVTYPER12_EL0, 3,3,14,13,4),
        MAKE_SYSREGCODE(PMEVTYPER13_EL0, 3,3,14,13,5),
        MAKE_SYSREGCODE(PMEVTYPER14_EL0, 3,3,14,13,6),
        MAKE_SYSREGCODE(PMEVTYPER15_EL0, 3,3,14,13,7),
        MAKE_SYSREGCODE(PMEVTYPER16_EL0, 3,3,14,14,0),
        MAKE_SYSREGCODE(PMEVTYPER17_EL0, 3,3,14,14,1),
        MAKE_SYSREGCODE(PMEVTYPER18_EL0, 3,3,14,14,2),
        MAKE_SYSREGCODE(PMEVTYPER19_EL0, 3,3,14,14,3),
        MAKE_SYSREGCODE(PMEVTYPER20_EL0, 3,3,14,14,4),
        MAKE_SYSREGCODE(PMEVTYPER21_EL0, 3,3,14,14,5),
        MAKE_SYSREGCODE(PMEVTYPER22_EL0, 3,3,14,14,6),
        MAKE_SYSREGCODE(PMEVTYPER23_EL0, 3,3,14,14,7),
        MAKE_SYSREGCODE(PMEVTYPER24_EL0, 3,3,14,15,0),
        MAKE_SYSREGCODE(PMEVTYPER25_EL0, 3,3,14,15,1),
        MAKE_SYSREGCODE(PMEVTYPER26_EL0, 3,3,14,15,2),
        MAKE_SYSREGCODE(PMEVTYPER27_EL0, 3,3,14,15,3),
        MAKE_SYSREGCODE(PMEVTYPER28_EL0, 3,3,14,15,4),
        MAKE_SYSREGCODE(PMEVTYPER29_EL0, 3,3,14,15,5),
        MAKE_SYSREGCODE(PMEVTYPER30_EL0, 3,3,14,15,6),
        MAKE_SYSREGCODE(PMCCFILTR_EL0,   3,3,14,15,7)
    };
}